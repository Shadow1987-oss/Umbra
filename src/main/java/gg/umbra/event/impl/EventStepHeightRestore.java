package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.event.impl.EventStep;
import gg.umbra.wrapper.impl.Entity;

public class EventStepHeightRestore
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Entity entity;

    public EventStepHeightRestore(Object entityHandle) {
        this.entity = new Entity(entityHandle);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        this.entity.K(EventStep.getOriginalStepHeight());
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
