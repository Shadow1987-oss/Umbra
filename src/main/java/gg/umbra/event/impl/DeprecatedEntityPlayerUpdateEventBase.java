package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.Entity;

@Deprecated
public class DeprecatedEntityPlayerUpdateEventBase
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Entity entity;

    public DeprecatedEntityPlayerUpdateEventBase(Object entityHandle) {
        this.entity = new Entity(entityHandle);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }


    @Override
    public boolean fire() {
        if (!this.entity.isInstance(MappedClasses.z5)) {
            return false;
        }
        return super.fire();
    }
}

