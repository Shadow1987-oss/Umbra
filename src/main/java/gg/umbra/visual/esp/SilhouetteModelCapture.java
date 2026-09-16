package gg.umbra.visual.esp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Geometry capture for Minecraft 26.1+ (the "submit graph" renderer era).
 *
 * Since 26.1 {@code LivingEntityRenderer} exposes no {@code render(...)} method at all (models are
 * submitted to a graph executor and drawn later), a re-render based ESP can no longer call
 * {@code doRender}. Instead we replay {@code EntityRenderer.submit(state, pose, collector, camera)}
 * ourselves against a fake {@code SubmitNodeCollector}: vanilla performs every transform (entity
 * position, body yaw, baby scale, y-flip and model offset) while calling back into our collector
 * with the model plus the already-transformed {@code PoseStack}. We then feed the model's cube
 * geometry a {@code VertexConsumer} proxy and capture the quads it would have drawn, so the caller
 * can repaint the actual player body silhouette through Umbra's own buffered pipeline.
 *
 * Everything here is reflection based (real Minecraft names), so it compiles without any Minecraft
 * class on the build classpath. It only ever runs on 26.1+, where the game ships unobfuscated.
 */
public final class SilhouetteModelCapture {
    public interface QuadListener {
        void onQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4);
    }

    private static final int MAX_QUADS_PER_ENTITY = 32768;

    private static Method dispatcherExtractEntityMethod;
    private static final Map<Class<?>, RendererHooks> RENDERER_HOOKS = new HashMap<Class<?>, RendererHooks>();
    private static final Map<Class<?>, ModelHooks> MODEL_HOOKS = new HashMap<Class<?>, ModelHooks>();
    private static Object collectorProxy;

    private SilhouetteModelCapture() {
    }

    private static final class RendererHooks {
        final Method submit;
        final Class<?> poseClass;
        final Class<?> collectorInterface;
        final Constructor<?> cameraConstructor;
        final Method posePush;
        final Method posePop;
        final Method poseTranslate;

        RendererHooks(Method submit, Class<?> poseClass, Class<?> collectorInterface, Constructor<?> cameraConstructor, Method posePush, Method posePop, Method poseTranslate) {
            this.submit = submit;
            this.poseClass = poseClass;
            this.collectorInterface = collectorInterface;
            this.cameraConstructor = cameraConstructor;
            this.posePush = posePush;
            this.posePop = posePop;
            this.poseTranslate = poseTranslate;
        }
    }

    private static final class ModelHooks {
        final Method renderToBuffer;
        final Class<?> consumerClass;

        ModelHooks(Method renderToBuffer, Class<?> consumerClass) {
            this.renderToBuffer = renderToBuffer;
            this.consumerClass = consumerClass;
        }
    }

    private static final class Session {
        final float[] buffer = new float[12];
        int count;
        int quadCount;
        QuadListener listener;
        Object consumerProxy;
        Class<?> consumerClass;
    }

    private static final ThreadLocal<Session> SESSION = new ThreadLocal<Session>() {
        @Override
        protected Session initialValue() {
            return new Session();
        }
    };

    /**
     * Finds a method by (name, arity) whose parameters are compatible with the given runtime types,
     * walking the class hierarchy. The first matching declaration wins.
     */
    private static Method findAssignableMethod(Class<?> owner, String name, Class<?>[] parameterTypes) {
        for (Class<?> current = owner; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (!method.getName().equals(name) || method.getParameterCount() != parameterTypes.length) {
                    continue;
                }
                Class<?>[] declared = method.getParameterTypes();
                boolean compatible = true;
                for (int i = 0; i < declared.length; ++i) {
                    Class<?> declaredType = declared[i];
                    Class<?> runtimeType = parameterTypes[i];
                    if (runtimeType == null) {
                        continue;
                    }
                    declaredType = box(declaredType);
                    runtimeType = box(runtimeType);
                    if (!declaredType.isAssignableFrom(runtimeType)) {
                        compatible = false;
                        break;
                    }
                }
                if (!compatible) {
                    continue;
                }
                if (!method.isAccessible() && !Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }
                return method;
            }
        }
        return null;
    }

    private static Class<?> box(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }
        if (type == Integer.TYPE) {
            return Integer.class;
        }
        if (type == Float.TYPE) {
            return Float.class;
        }
        if (type == Double.TYPE) {
            return Double.class;
        }
        if (type == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type == Long.TYPE) {
            return Long.class;
        }
        if (type == Short.TYPE) {
            return Short.class;
        }
        if (type == Byte.TYPE) {
            return Byte.class;
        }
        if (type == Character.TYPE) {
            return Character.class;
        }
        return type;
    }

    private static RendererHooks resolveRendererHooks(Class<?> rendererClass, Class<?> stateClass) throws Exception {
        RendererHooks cached = RENDERER_HOOKS.get(rendererClass);
        if (cached != null) {
            return cached;
        }
        Method submitMethod = findAssignableMethod(rendererClass, "submit", new Class[]{stateClass, null, null, null});
        if (submitMethod == null) {
            throw new NoSuchMethodException("submit(...) not found on " + rendererClass.getName());
        }
        Class<?>[] types = submitMethod.getParameterTypes();
        Class<?> poseClass = types[1];
        Class<?> collectorInterface = types[2];
        Constructor<?> cameraConstructor = types[3].getConstructor(new Class[0]);
        Method posePush = findInHierarchy(poseClass, "pushPose", new Class[0]);
        Method posePop = findInHierarchy(poseClass, "popPose", new Class[0]);
        Method poseTranslate = findInHierarchy(poseClass, "translate", new Class[]{Float.TYPE, Float.TYPE, Float.TYPE});
        if (posePush == null || posePop == null || poseTranslate == null) {
            throw new NoSuchMethodException("PoseStack ops not found on " + poseClass.getName());
        }
        RendererHooks hooks = new RendererHooks(submitMethod, poseClass, collectorInterface, cameraConstructor, posePush, posePop, poseTranslate);
        RENDERER_HOOKS.put(rendererClass, hooks);
        return hooks;
    }

    private static Method findInHierarchy(Class<?> owner, String name, Class<?>[] parameterTypes) {
        return findAssignableMethod(owner, name, parameterTypes);
    }

    private static ModelHooks resolveModelHooks(Class<?> modelClass, Class<?> poseClass, Class<?> stateClass) throws Exception {
        ModelHooks cached = MODEL_HOOKS.get(modelClass);
        if (cached != null) {
            return cached;
        }
        Method renderToBuffer = null;
        Class<?> consumerClass = null;
        for (Class<?> current = modelClass; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (renderToBuffer == null && method.getName().equals("renderToBuffer") && method.getParameterCount() == 5) {
                    Class<?>[] types = method.getParameterTypes();
                    if (types[0].isAssignableFrom(poseClass) && types[1].isInterface()) {
                        renderToBuffer = method;
                        consumerClass = types[1];
                    }
                }
            }
            if (renderToBuffer != null && consumerClass != null) {
                break;
            }
        }
        if (renderToBuffer == null || consumerClass == null) {
            throw new NoSuchMethodException("renderToBuffer not found on " + modelClass.getName());
        }
        makeAccessible(renderToBuffer);
        ModelHooks hooks = new ModelHooks(renderToBuffer, consumerClass);
        MODEL_HOOKS.put(modelClass, hooks);
        return hooks;
    }

    private static Method findSetupAnim(Class<?> modelClass, Class<?> stateClass) {
        for (Class<?> current = modelClass; current != null; current = current.getSuperclass()) {
            for (Method method : current.getDeclaredMethods()) {
                if (!method.getName().equals("setupAnim") || method.getParameterCount() != 1) {
                    continue;
                }
                if (method.getParameterTypes()[0].isAssignableFrom(stateClass)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    private static void renderPart(Object part, Object pose, Session session) {
        if (part == null || pose == null || session.consumerProxy == null || session.quadCount > MAX_QUADS_PER_ENTITY) {
            return;
        }
        try {
            for (Class<?> current = part.getClass(); current != null; current = current.getSuperclass()) {
                Method render = null;
                try {
                    render = current.getDeclaredMethod("render", pose.getClass(), session.consumerClass, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // walk up
                }
                if (render == null) {
                    continue;
                }
                makeAccessible(render);
                render.invoke(part, pose, session.consumerProxy, Integer.valueOf(15728880), Integer.valueOf(655360), Integer.valueOf(-1));
                return;
            }
        }
        catch (Throwable throwable) {
            // a single exotic model part must never take down the whole ESP pass
        }
    }

    private static void makeAccessible(Method method) {
        if (!method.isAccessible() && !Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
    }

    /**
     * Replays the entity renderer's submit pass and captures the model quads it would draw.
     *
     * @return the number of quads captured, or -1 when this version/entity cannot be captured.
     */
    public static int capture(Object dispatcher, Object renderer, Object entity, double x, double y, double z, float partialTicks, QuadListener listener) {
        Session session = SESSION.get();
        session.listener = listener;
        session.count = 0;
        session.quadCount = 0;
        session.consumerProxy = null;
        session.consumerClass = null;
        if (entity == null || renderer == null || dispatcher == null || listener == null) {
            return -1;
        }
        try {
            if (dispatcherExtractEntityMethod == null) {
                dispatcherExtractEntityMethod = findAssignableMethod(dispatcher.getClass(), "extractEntity", new Class[]{entity.getClass(), Float.TYPE});
                if (dispatcherExtractEntityMethod != null) {
                    makeAccessible(dispatcherExtractEntityMethod);
                }
            }
            if (dispatcherExtractEntityMethod == null) {
                return -1;
            }
            Object state = dispatcherExtractEntityMethod.invoke(dispatcher, entity, Float.valueOf(partialTicks));
            if (state == null) {
                return -1;
            }
            RendererHooks hooks = resolveRendererHooks(renderer.getClass(), state.getClass());
            ensureCollectorProxy(hooks);
            Object pose = hooks.poseClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            hooks.posePush.invoke(pose, new Object[0]);
            try {
                hooks.poseTranslate.invoke(pose, new Object[]{Float.valueOf((float)x), Float.valueOf((float)y), Float.valueOf((float)z)});
                Object camera = hooks.cameraConstructor.newInstance(new Object[0]);
                hooks.submit.invoke(renderer, new Object[]{state, pose, collectorProxy, camera});
            }
            finally {
                hooks.posePop.invoke(pose, new Object[0]);
            }
            return session.quadCount;
        }
        catch (Throwable throwable) {
            return -1;
        }
    }

    private static void ensureCollectorProxy(RendererHooks hooks) {
        if (collectorProxy == null) {
            synchronized (SilhouetteModelCapture.class) {
                if (collectorProxy == null) {
                    collectorProxy = Proxy.newProxyInstance(hooks.collectorInterface.getClassLoader(), new Class[]{hooks.collectorInterface}, new CollectorHandler());
                }
            }
        }
    }

    private static void renderModel(Object model, Object animState, Object pose, Session session) {
        if (model == null || pose == null || session.quadCount > MAX_QUADS_PER_ENTITY) {
            return;
        }
        try {
            Class<?> stateClass = animState != null ? animState.getClass() : Object.class;
            ModelHooks hooks = resolveModelHooks(model.getClass(), pose.getClass(), stateClass);
            if (session.consumerProxy == null) {
                session.consumerProxy = Proxy.newProxyInstance(hooks.consumerClass.getClassLoader(), new Class[]{hooks.consumerClass}, new ConsumerHandler());
                session.consumerClass = hooks.consumerClass;
            }
            if (animState != null) {
                Method setupAnim = findSetupAnim(model.getClass(), stateClass);
                if (setupAnim != null) {
                    setupAnim.invoke(model, animState);
                }
            }
            hooks.renderToBuffer.invoke(model, pose, session.consumerProxy, Integer.valueOf(15728880), Integer.valueOf(655360), Integer.valueOf(-1));
        }
        catch (Throwable throwable) {
            // a single exotic model must never take down the whole ESP pass
        }
    }

    /**
     * Handler for the fake SubmitNodeCollector: vanilla answers submit calls with the model geometry
     * (body, layers, held items) and we redirect those into our VertexConsumer proxy.
     */
    private static final class CollectorHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            String name = method.getName();
            if (args == null) {
                args = new Object[0];
            }
            if (name.equals("submitModel") && args.length >= 3) {
                renderModel(args[0], args[1], args[2], SESSION.get());
                return null;
            }
            if (name.equals("submitModelPart") && args.length >= 2) {
                renderPart(args[0], args[1], SESSION.get());
                return null;
            }
            if (name.equals("toString")) {
                return "SilhouetteModelCapture$Collector";
            }
            if (name.equals("hashCode")) {
                return Integer.valueOf(System.identityHashCode(proxy));
            }
            if (name.equals("equals")) {
                return args.length > 0 && proxy == args[0];
            }
            return null;
        }
    }

    /**
     * Handler for the VertexConsumer proxy: we only keep the final transformed vertex position.
     * Vanilla model parts stream exactly 4 vertices per polygon, so every fourth vertex completes a
     * quad that we hand back to the listener.
     */
    private static final class ConsumerHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            Session session = SESSION.get();
            String name = method.getName();
            if (name.equals("addVertex") && args != null && args.length >= 3 && args[0] instanceof Number) {
                if (session.quadCount <= MAX_QUADS_PER_ENTITY) {
                    int index = session.count * 3;
                    session.buffer[index] = ((Number)args[0]).floatValue();
                    session.buffer[index + 1] = ((Number)args[1]).floatValue();
                    session.buffer[index + 2] = ((Number)args[2]).floatValue();
                    ++session.count;
                    if (session.count == 4) {
                        session.count = 0;
                        ++session.quadCount;
                        session.listener.onQuad(session.buffer[0], session.buffer[1], session.buffer[2], session.buffer[3], session.buffer[4], session.buffer[5], session.buffer[6], session.buffer[7], session.buffer[8], session.buffer[9], session.buffer[10], session.buffer[11]);
                    }
                }
                return proxy;
            }
            if (name.equals("toString")) {
                return "SilhouetteModelCapture$VertexConsumer";
            }
            if (name.equals("hashCode")) {
                return Integer.valueOf(System.identityHashCode(proxy));
            }
            if (name.equals("equals")) {
                return args != null && args.length > 0 && proxy == args[0];
            }
            return proxy;
        }
    }
}
