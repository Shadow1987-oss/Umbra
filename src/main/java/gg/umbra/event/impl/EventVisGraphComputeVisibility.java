package gg.umbra.event.impl;

import gg.umbra.Umbra;
import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.worldmods.OreHighlight;
import gg.umbra.wrapper.impl.SetVisibility;

public class EventVisGraphComputeVisibility
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        OreHighlight xRay = Umbra.INSTANCE.getHackManager().getXRayModule();
        if (xRay == null || !xRay.boolean_r()) {
            return false;
        }
        this.setCancelled(true);
        return true;
    }


    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventVisGraphComputeVisibility() {
    }

    public EventVisGraphComputeVisibility(Object ignoredVisGraphHandle) {
    }

    public static Object getVisibility() {
        SetVisibility setVisibility = new SetVisibility(Umbra.INSTANCE.getMappingsMapperCompat().setVisibility.constructor.newInstance(new Object[0]));
        setVisibility.setAllVisible(true);
        return setVisibility.getObject();
    }
}

