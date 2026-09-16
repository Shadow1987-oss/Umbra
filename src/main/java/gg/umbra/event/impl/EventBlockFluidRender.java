package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;

public class EventBlockFluidRender
extends Event {
    private final int z;
    private final int x;
    private final Object blockHandle;
    private final int y;
    private final Object renderBlocksHandle;
    private boolean result;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public boolean isResult() {
        return this.result;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventBlockFluidRender(Object renderBlocksHandle, Object blockHandle, int x, int y, int z) {
        this.renderBlocksHandle = renderBlocksHandle;
        this.blockHandle = blockHandle;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockFluidRender(this);
        if (this.isCanceled()) {
            this.result = Umbra.INSTANCE.getMappingsMapperCompat().renderBlocks
                    .renderStandardBlockWithColorMultiplierMethod.invokeBoolean(
                            this.renderBlocksHandle, this.blockHandle, this.x, this.y, this.z,
                            Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
        }
        return this.isCanceled();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}

