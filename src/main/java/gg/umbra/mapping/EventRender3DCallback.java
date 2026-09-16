package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.event.impl.EventRenderTracers3D;
import gg.umbra.mapping.InsertedEventCallback;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.wrapper.impl.EntityRenderer;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GameSettings;
import gg.umbra.wrapper.impl.GlStateManager;
import gg.umbra.wrapper.impl.Matrix4f;
import gg.umbra.wrapper.impl.MatrixStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RenderManager;
import org.lwjgl.opengl.GL11;

public class EventRender3DCallback
implements InsertedEventCallback {
    private final float N;
    private static boolean r;
    private static float D;
    private final MatrixStack y;

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public boolean fire() {
        RenderManager.updateInterpolatedRenderPosition(this.N);
        boolean bl = EventRender3D.getEventListeners().hasListeners() || EventRenderTracers3D.getEventListeners().hasListeners();
        boolean bl2 = false;
        boolean bl3 = false;
        if (EventRender3D.getEventListeners().hasListeners()) {
            bl2 = new EventRender3D(this.y, this.N).fire();
        }
        GameSettings gameSettings = Minecraft.gameSettings();
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf();
        if (EventRenderTracers3D.getEventListeners().hasListeners()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                try {
                    boolean bl4 = gameSettings.k();
                    gameSettings.O(false);
                    entityRenderer.s(this.N, 0);
                    bl3 = new EventRenderTracers3D(this.y, this.N).fire();
                    gameSettings.O(bl4);
                }
                catch (Exception exception) {
                    Umbra.logThrowable(exception);
                }
            } else {
                SharedModuleControlClaims.renderPass.blockRender();
                GL11.glPushMatrix();
                GlStateManager.F$src$V$acq27m();
                entityRenderer.Y(this.N);
                bl3 = new EventRenderTracers3D(this.y, this.N).fire();
                GL11.glPopMatrix();
                SharedModuleControlClaims.renderPass.clearClaimed();
            }
        }
        if (bl) {
            entityRenderer.B(1.0);
            OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        D = this.N;
        return bl2 || bl3;
    }

    public EventRender3DCallback(Object object) {
        if (ForgeVersion.MC_26_2.d()) {
            this.y = MatrixStack.A();
            this.y.i(new Matrix4f(object));
        } else {
            this.y = new MatrixStack(object);
        }
        this.N = Minecraft.getTimer().renderPartialTicks();
    }

    static {
        D = -1.0f;
    }

    public EventRender3DCallback(float f) {
        this.y = null;
        this.N = f;
    }
}
