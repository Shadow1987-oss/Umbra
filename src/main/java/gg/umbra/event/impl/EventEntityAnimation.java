package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;

public class EventEntityAnimation
extends Event {
    private final boolean state;
    private final Object entityHandle;
    private final int animationId;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventEntityAnimation(Object entityHandle, int animationId, boolean state) {
        this.entityHandle = entityHandle;
        this.animationId = animationId;
        this.state = state;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
