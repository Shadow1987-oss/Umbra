package gg.umbra.event;

import gg.umbra.event.EventListenerInvoker;
import gg.umbra.event.EventListener;
import gg.umbra.event.IEvent;
import gg.umbra.mapping.MappingMethod;
import java.lang.reflect.Method;

public class ReflectiveEventListenerInvoker
implements EventListenerInvoker {
    private final MappingMethod handlerMethod;
    private final EventListener listener;
    private final Class<? extends IEvent> eventType;
    private static String[] obfuscationState;

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    static {
        if (ReflectiveEventListenerInvoker.getObfuscationState() != null) {
            ReflectiveEventListenerInvoker.setObfuscationState(new String[5]);
        }
    }

    @Override
    public <T extends IEvent> void invoke(T event) {
        this.handlerMethod.invokeVoid(this.listener, event);
    }

    public ReflectiveEventListenerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        this.listener = listener;
        this.eventType = eventType;
        MappingMethod mappingMethod = new MappingMethod(null, listener.getClass(), method.getName(), false, false, false, Void.TYPE, eventType);
        this.handlerMethod = mappingMethod.register();
    }
}
