package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;
import gg.umbra.wrapper.impl.Block;

public class EventBlockShouldRender
extends Event {
    private final Block block;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventBlockShouldRender(Object blockHandle) {
        this.block = new Block(blockHandle);
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onBlockSideRender(this);
        return this.isCanceled();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Block getBlock() {
        return this.block;
    }


    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}

