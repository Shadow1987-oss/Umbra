package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.RenderBatchManager;
import gg.umbra.wrapper.impl.MatrixStack;

public abstract class EventRender3DBase
extends Event {
    private final float partialTicks;
    private final MatrixStack matrixStack;
    private static GuiComponent[] obfuscationState;

    EventRender3DBase(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }

    public static GuiComponent[] getRender3DObfuscationState() {
        return obfuscationState;
    }

    public float getTicks() {
        return this.partialTicks;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    @Override
    public boolean fire() {
        boolean fired = super.fire();
        if (GuiRenderPrimitives.d()) {
            RenderBatchManager.getInstance().flushWorldBatches(this.partialTicks);
        }
        return fired;
    }


    public static void setRender3DObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    static {
        if (EventRender3DBase.getRender3DObfuscationState() == null) {
            EventRender3DBase.setRender3DObfuscationState(new GuiComponent[5]);
        }
    }
}

