package gg.umbra.event.impl;

import gg.umbra.ui.click.GuiScreenNativeCallbackBridge;
import gg.umbra.utils.RenderThreadTaskQueue;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.RenderBatchManager;

public class EventPostRenderTick
extends EventRenderTickBase {
    private static final String RENDER_PHASE_NAME;

    public EventPostRenderTick() {
        super(-1.0f);
    }

    public EventPostRenderTick(float f) {
        super(f);
    }


    @Override
    public boolean fire() {
        GuiScreenNativeCallbackBridge.drawScreen(null, 0, 0, 0.0f);
        if (GuiRenderPrimitives.d()) {
            RenderThreadTaskQueue.runPendingTasks();
            RenderBatchManager.getInstance().flushGuiBatches(this.getTicks());
        }
        boolean fired = super.fire();
        GuiRenderPrimitives.l(RENDER_PHASE_NAME);
        return fired;
    }

    static {
        try {
            RENDER_PHASE_NAME = "Post render";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

