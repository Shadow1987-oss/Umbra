package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.wrapper.impl.Entity;

public class EventPreRenderEntity
extends Event {
    private Entity entity = null;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object entityHandle;

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }


    @Override
    public boolean fire() {
        return super.fire();
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Entity getEntity() {
        if (this.entity == null) {
            this.entity = new Entity(this.entityHandle);
        }
        return this.entity;
    }

    public EventPreRenderEntity(Object entityHandle) {
        this.entityHandle = entityHandle;
    }
}

