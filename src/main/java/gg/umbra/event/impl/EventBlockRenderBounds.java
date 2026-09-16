package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.RenderBlocks;

public class EventBlockRenderBounds
extends Event {
    private final RenderBlocks renderBlocks;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Block block;

    public RenderBlocks getRenderBlocks() {
        return this.renderBlocks;
    }

    public EventBlockRenderBounds(Object renderBlocksHandle, Object blockHandle) {
        this.renderBlocks = new RenderBlocks(renderBlocksHandle);
        this.block = new Block(blockHandle);
    }

    public Block getBlock() {
        return this.block;
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onAmbientOcclusion(this);
        return this.isCanceled();
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

