package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;

public class EventChunkRenderRebuild
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        xRay.onChunkRenderRebuild(this);
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

