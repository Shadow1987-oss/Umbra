package gg.umbra.visual.esp;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreRenderLiving;
import gg.umbra.event.impl.EventPreRenderPlayerSpec;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.event.impl.EventSetArmorModel;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.visual.ESP;
import gg.umbra.render.OffscreenRenderContext;
import gg.umbra.utils.MutableColor;
import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.BufferedRenderPrimitives;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.utils.render.RenderUtils;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderLivingBase;
import gg.umbra.wrapper.impl.RenderManager;
import gg.umbra.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.lwjgl.opengl.GL11;

/**
 * Core Profile safe "silhouette" ESP: re-renders the actual player model tinted with the ESP
 * color, with an inflated back-face pass to produce a rim that follows the body (Meteor-style).
 *
 * On Minecraft 26.1+ the entity renderer is a submit-graph (there is no render(...) method to
 * call), so a real model silhouette is captured by replaying the renderer submit pass against a
 * fake SubmitNodeCollector (see {@link SilhouetteModelCapture}) and repainting the captured model
 * quads through the buffered pipeline. Older core-profile builds use a buffered bounding shape, and
 * legacy fixed-function builds keep the original two-pass GL re-render.
 */
public class ESPSilhouette
extends SubHack<ESP> {
    private final ESP parentEsp = (ESP)this.getParent();
    private boolean renderingSilhouette;
    private static boolean modernFailureReported;
    private static final Set<String> loggedSkipReasons = new HashSet<String>();

    private static void logSkipOnce(Entity entity, String reason) {
        String name;
        try {
            name = entity.getName();
        } catch (Throwable throwable) {
            name = "?";
        }
        String key = name + "|" + reason;
        if (loggedSkipReasons.add(key)) {
            Umbra.debugLog("ESPSilhouette: skip " + name + " (" + reason + ")");
        }
    }

    @Listen
    public void onPreRenderLiving(EventPreRenderLiving event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (event.getEntity().equals(event.getThePlayer())) {
            return;
        }
        if (this.renderingSilhouette) {
            event.setCancelled(true);
        }
    }

    @Listen
    public void onPreRenderPlayerSpec(EventPreRenderPlayerSpec event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getClientPlayer()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getClientPlayer().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getClientPlayer(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingSilhouette) {
            event.setCancelled(true);
        }
    }

    @Listen
    public void onSetArmorModel(EventSetArmorModel event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        if (Umbra.INSTANCE.getClientSettings().isBot(event.getEntity()) && this.parentEsp.hideBots.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.parentEsp.enemyOnly.getEffectiveValue().booleanValue() && (this.parentEsp.enemyListOnly.getEffectiveValue() != false ? !Umbra.INSTANCE.getEnemyManager().isEnemy(event.getEntity().getName()) : !Umbra.INSTANCE.getClientSettings().isValidTarget(event.getEntity(), false))) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        if (this.renderingSilhouette) {
            event.setResult(0);
            event.setCancelled(true);
        }
    }

    @Listen
    public void onRender3D(EventRender3D event) {
        if (OffscreenRenderContext.isRenderingOffscreen()) {
            return;
        }
        EntityPlayerSP viewer = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (viewer.isNull() || world.isNull()) {
            return;
        }
        if (ForgeVersion.MC_26_1.d()) {
            // 26.1+ (submit-graph renderer): capture the real model geometry and repaint it.
            this.renderSubmitGraphSilhouette(event, viewer, world);
            return;
        }
        if (GuiRenderPrimitives.d()) {
            // 1.17+ core profile (Iris/Sodium): no safe model re-render; draw a filled bounding shape.
            this.renderBufferedFallbackSilhouette(event, viewer, world);
            return;
        }
        this.renderLegacySilhouette(event);
    }

    /**
     * 26.1+ path. The vanilla renderer no longer exposes any render(...) method, so we replay its
     * submit(...) pass against a fake SubmitNodeCollector and capture the model quads vanilla would
     * draw (transforms included). The captured quads are then queued through the buffered pipeline
     * in two passes: an inflated rim (when thickness &gt; 1) that never writes depth, and the body
     * fill at Fill Alpha on top of it.
     */
    private void renderSubmitGraphSilhouette(EventRender3D event, EntityPlayerSP viewer, WorldClient world) {
        RenderUtil.d();
        event.getEntityRenderer().B(1.0);
        RenderUtils.g();
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        float partialTicks = event.getTicks();
        Object dispatcher = Minecraft.D().getObject();
        if (dispatcher == null) {
            RenderUtils.f();
            event.getEntityRenderer().O(1.0);
            RenderUtil.Y();
            OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            return;
        }
        float thickness = ((Double)this.parentEsp.silhouetteThickness.getValue()).floatValue();
        float fillAlpha = Math.max(0.0f, Math.min(1.0f, ((Double)this.parentEsp.silhouetteFillAlpha.getValue()).floatValue()));
        boolean throughWalls = this.parentEsp.silhouetteThroughWalls.getEffectiveValue().booleanValue();
        boolean depthTestEnabled = BufferedGuiRenderPrimitives.capabilityState.depthTestEnabled;
        boolean depthWriteEnabled = BufferedGuiRenderPrimitives.capabilityState.depthWriteEnabled;
        OpenGlBackendHolder.backend.enableCapability(3042);
        try {
            for (Object entityHandle : world.z()) {
                Entity entity = new Entity(entityHandle);
                MutableColor color = this.parentEsp.resolveEntityColor(viewer, entity);
                if (color == null) {
                    continue;
                }
                if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) {
                    continue;
                }
                double previousX = entity.M();
                double previousY = entity.W();
                double previousZ = entity.m$src$D$fwnne5();
                double renderX = previousX + (entity.z() - previousX) * (double)partialTicks - cameraX;
                double renderY = previousY + (entity.N() - previousY) * (double)partialTicks - cameraY;
                double renderZ = previousZ + (entity.h() - previousZ) * (double)partialTicks - cameraZ;
                RenderLivingBase renderLivingBase = new RenderLivingBase(Minecraft.D().getEntityRenderObject(entity).getObject());
                if (!renderLivingBase.isNotNull()) {
                    ESPSilhouette.logSkipOnce(entity, "no-renderer");
                    this.queueFallbackBox(renderX, renderY, renderZ, entity.b(), color, fillAlpha, throughWalls);
                    continue;
                }
                ArrayList<float[]> quads = new ArrayList<float[]>();
                int captured = SilhouetteModelCapture.capture(dispatcher, renderLivingBase.getObject(), entity.getObject(), renderX, renderY, renderZ, partialTicks, new SilhouetteModelCapture.QuadListener() {
                    @Override
                    public void onQuad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
                        quads.add(new float[]{x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4});
                    }
                });
                if (captured < 0) {
                    if (!ESPSilhouette.modernFailureReported) {
                        ESPSilhouette.modernFailureReported = true;
                        Umbra.debugLog("ESPSilhouette: model capture unavailable on this build; falling back to bounding shape.");
                    }
                    ESPSilhouette.logSkipOnce(entity, "capture-fail");
                    this.queueFallbackBox(renderX, renderY, renderZ, entity.b(), color, fillAlpha, throughWalls);
                    continue;
                }
                if (quads.isEmpty()) {
                    ESPSilhouette.logSkipOnce(entity, "empty-quads");
                    this.queueFallbackBox(renderX, renderY, renderZ, entity.b(), color, fillAlpha, throughWalls);
                    continue;
                }
                ESPSilhouette.logSkipOnce(entity, "ok-quads-" + quads.size());
                this.queueSilhouetteQuads(quads, renderX, renderY, renderZ, color, thickness, fillAlpha, throughWalls);
            }
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
        finally {
            if (depthTestEnabled) {
                OpenGlBackendHolder.backend.enableCapability(2929);
            } else {
                OpenGlBackendHolder.backend.disableCapability(2929);
            }
            OpenGlBackendHolder.backend.setDepthMask(depthWriteEnabled);
        }
        RenderUtils.f();
        event.getEntityRenderer().O(1.0);
        RenderUtil.Y();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void queueSilhouetteQuads(ArrayList<float[]> quads, double originX, double originY, double originZ, MutableColor color, float thickness, float fillAlpha, boolean throughWalls) {
        float[] colorComponents = RenderUtils.d(color.l());
        int rimColor = this.rgbaToInt(colorComponents[0], colorComponents[1], colorComponents[2], Math.min(1.0f, colorComponents[3]));
        int fillColor = this.rgbaToInt(colorComponents[0], colorComponents[1], colorComponents[2], fillAlpha);
        boolean rim = thickness > 1.001f;
        boolean depthPerEntity = !throughWalls;
        boolean depthWriteSaved = BufferedGuiRenderPrimitives.capabilityState.depthWriteEnabled;
        if (rim) {
            // Rim pass: inflated silhouette drawn first, without writing depth, so the body fill
            // painted afterwards always lands on top of it (mirrors the legacy two-pass look).
            this.setDepthState(depthPerEntity, false);
            for (float[] quad : quads) {
                BufferedRenderPrimitives.fillQuad(ESPSilhouette.scale(quad[0], originX, thickness), ESPSilhouette.scale(quad[1], originY, thickness), ESPSilhouette.scale(quad[2], originZ, thickness), ESPSilhouette.scale(quad[3], originX, thickness), ESPSilhouette.scale(quad[4], originY, thickness), ESPSilhouette.scale(quad[5], originZ, thickness), ESPSilhouette.scale(quad[6], originX, thickness), ESPSilhouette.scale(quad[7], originY, thickness), ESPSilhouette.scale(quad[8], originZ, thickness), ESPSilhouette.scale(quad[9], originX, thickness), ESPSilhouette.scale(quad[10], originY, thickness), ESPSilhouette.scale(quad[11], originZ, thickness), new Color(rimColor));
            }
        }
        // Body fill pass on top.
        this.setDepthState(depthPerEntity, true);
        for (float[] quad : quads) {
            BufferedRenderPrimitives.fillQuad(quad[0], quad[1], quad[2], quad[3], quad[4], quad[5], quad[6], quad[7], quad[8], quad[9], quad[10], quad[11], new Color(fillColor));
        }
        this.setDepthState(true, depthWriteSaved);
    }

    private void setDepthState(boolean depthTest, boolean depthMask) {
        if (depthTest) {
            OpenGlBackendHolder.backend.enableCapability(2929);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2929);
        }
        OpenGlBackendHolder.backend.setDepthMask(depthMask);
    }

    private static float scale(double value, double origin, float factor) {
        return (float)(origin + (value - origin) * (double)factor);
    }

    private void queueFallbackBox(double renderX, double renderY, double renderZ, double halfWidth, MutableColor color, float fillAlpha, boolean throughWalls) {
        float[] colorComponents = RenderUtils.d(color.l());
        this.setDepthState(!throughWalls, true);
        BufferedRenderPrimitives.fillBox(renderX - halfWidth, renderY, renderZ - halfWidth, renderX + halfWidth, renderY + 1.8, renderZ + halfWidth, new Color(this.rgbaToInt(colorComponents[0], colorComponents[1], colorComponents[2], fillAlpha)));
    }

    private int rgbaToInt(float r, float g, float b, float a) {
        return ((int)(a * 255.0f) & 0xFF) << 24 | ((int)(r * 255.0f) & 0xFF) << 16 | ((int)(g * 255.0f) & 0xFF) << 8 | (int)(b * 255.0f) & 0xFF;
    }

    /**
     * Core profile fallback (1.17 - 1.21.x, or 26.x when the model capture is unavailable): draws a
     * filled, translucent bounding box with the ESP color. Crude compared with the real silhouette,
     * but guaranteed not to crash the modern renderer.
     */
    private void renderBufferedFallbackSilhouette(EventRender3D event, EntityPlayerSP viewer, WorldClient world) {
        RenderUtil.d();
        event.getEntityRenderer().B(1.0);
        RenderUtils.g();
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        float partialTicks = event.getTicks();
        float fillAlpha = ((Double)this.parentEsp.silhouetteFillAlpha.getValue()).floatValue();
        boolean throughWalls = this.parentEsp.silhouetteThroughWalls.getEffectiveValue().booleanValue();
        boolean depthTestEnabled = BufferedGuiRenderPrimitives.capabilityState.depthTestEnabled;
        boolean depthWriteEnabled = BufferedGuiRenderPrimitives.capabilityState.depthWriteEnabled;
        OpenGlBackendHolder.backend.enableCapability(3042);
        try {
            if (throughWalls) {
                OpenGlBackendHolder.backend.disableCapability(2929);
            } else {
                OpenGlBackendHolder.backend.enableCapability(2929);
            }
            OpenGlBackendHolder.backend.setDepthMask(true);
            for (Object entityHandle : world.z()) {
                Entity entity = new Entity(entityHandle);
                MutableColor color = this.parentEsp.resolveEntityColor(viewer, entity);
                if (color == null) {
                    continue;
                }
                if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) {
                    continue;
                }
                double previousX = entity.M();
                double previousY = entity.W();
                double previousZ = entity.m$src$D$fwnne5();
                double renderX = previousX + (entity.z() - previousX) * (double)partialTicks - cameraX;
                double renderY = previousY + (entity.N() - previousY) * (double)partialTicks - cameraY;
                double renderZ = previousZ + (entity.h() - previousZ) * (double)partialTicks - cameraZ;
                float[] colorComponents = RenderUtils.d(color.l());
                BufferedRenderPrimitives.drawBoxOutline(renderX - entity.b(), renderY, renderZ - entity.b(), renderX + entity.b(), renderY + 1.8, renderZ + entity.b(), new Color(this.rgbaToInt(colorComponents[0], colorComponents[1], colorComponents[2], 1.0f)));
                BufferedRenderPrimitives.fillBox(renderX - entity.b(), renderY, renderZ - entity.b(), renderX + entity.b(), renderY + 1.8, renderZ + entity.b(), new Color(this.rgbaToInt(colorComponents[0], colorComponents[1], colorComponents[2], fillAlpha)));
            }
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
        }
        finally {
            if (depthTestEnabled) {
                OpenGlBackendHolder.backend.enableCapability(2929);
            } else {
                OpenGlBackendHolder.backend.disableCapability(2929);
            }
            OpenGlBackendHolder.backend.setDepthMask(depthWriteEnabled);
        }
        RenderUtils.f();
        event.getEntityRenderer().O(1.0);
        RenderUtil.Y();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderLegacySilhouette(EventRender3D event) {
        EntityPlayerSP viewer = event.getThePlayer();
        WorldClient world = event.getWorld();
        event.getEntityRenderer().B(1.0);
        RenderUtil.d();
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        GL11.glBlendFunc(770, 771);
        boolean depthEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(2929);
        boolean cullEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(2884);
        boolean textureEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3553);
        boolean lightingEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(2896);
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        float thickness = ((Double)this.parentEsp.silhouetteThickness.getValue()).floatValue();
        float fillAlpha = ((Double)this.parentEsp.silhouetteFillAlpha.getValue()).floatValue();
        boolean throughWalls = this.parentEsp.silhouetteThroughWalls.getEffectiveValue().booleanValue();
        for (Object entityHandle : world.z()) {
                Entity entity = new Entity(entityHandle);
                MutableColor color = this.parentEsp.resolveEntityColor(viewer, entity);
                if (color == null) {
                    ESPSilhouette.logSkipOnce(entity, "no-color");
                    continue;
                }
            if (entity.equals(viewer) || !entity.isInstance(MappedClasses.Yl)) {
                continue;
            }
            double previousX = entity.M();
            double previousY = entity.W();
            double previousZ = entity.m$src$D$fwnne5();
            double renderX = previousX + (entity.z() - previousX) * (double)event.getTicks() - cameraX;
            double renderY = previousY + (entity.N() - previousY) * (double)event.getTicks() - cameraY;
            double renderZ = previousZ + (entity.h() - previousZ) * (double)event.getTicks() - cameraZ;
            RenderLivingBase renderLivingBase = new RenderLivingBase(Minecraft.D().getEntityRenderObject(entity).getObject());
            if (!renderLivingBase.isNotNull()) {
                continue;
            }
            float[] colorComponents = RenderUtils.d(color.l());
            OpenGlBackendHolder.backend.pushMatrix();
            OpenGlBackendHolder.backend.translate(renderX, renderY, renderZ);
            OpenGlBackendHolder.backend.scale(thickness, thickness, thickness);
            OpenGlBackendHolder.backend.translate(-renderX, -renderY, -renderZ);
            if (throughWalls) {
                OpenGlBackendHolder.backend.disableCapability(2929);
            } else {
                OpenGlBackendHolder.backend.enableCapability(2929);
            }
            OpenGlBackendHolder.backend.setDepthMask(true);
            OpenGlBackendHolder.backend.enableCapability(2884);
            this.renderingSilhouette = true;
            GL11.glCullFace(1028);
            OpenGlBackendHolder.backend.setColor(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
            renderLivingBase.doRender(entity, 0.0, 0.0, 0.0, 0.0f, event.getTicks());
            GL11.glCullFace(1029);
            OpenGlBackendHolder.backend.setColor(colorComponents[0], colorComponents[1], colorComponents[2], fillAlpha);
            renderLivingBase.doRender(entity, 0.0, 0.0, 0.0, 0.0f, event.getTicks());
            this.renderingSilhouette = false;
            if (!cullEnabled) {
                OpenGlBackendHolder.backend.disableCapability(2884);
            }
            OpenGlBackendHolder.backend.popMatrix();
        }
        OpenGlBackendHolder.backend.setDepthMask(true);
        if (depthEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2929);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2929);
        }
        if (cullEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2884);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2884);
        }
        if (textureEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3553);
        } else {
            OpenGlBackendHolder.backend.disableCapability(3553);
        }
        if (lightingEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2896);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2896);
        }
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        RenderUtil.Y();
        event.getEntityRenderer().O(1.0);
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public ESPSilhouette(HackModule parent, String name) {
        super(parent, name);
    }
}
