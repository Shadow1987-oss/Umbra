package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.utils.MouseOverRayTraceUpdater;

public class EventMouseOverUpdate
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventMouseOverUpdate(float f) {
    }

    @Override
    public boolean fire() {
        MouseOverRayTraceUpdater.b(false);
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
