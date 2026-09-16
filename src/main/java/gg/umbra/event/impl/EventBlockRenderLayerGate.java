package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;

public class EventBlockRenderLayerGate
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        return xRay != null && xRay.boolean_r();
    }
}
