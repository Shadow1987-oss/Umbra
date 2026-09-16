package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.Entity;
import org.jetbrains.annotations.Nullable;

public class EventEntityUpdateBase
extends Event {
    @Nullable
    private Entity entity;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object entityHandle;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        if (!MappedClasses.z5.isInstance(this.entityHandle)) {
            return false;
        }
        return super.fire();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    EventEntityUpdateBase(Object entityHandle) {
        this.entityHandle = entityHandle;
    }

    public Entity getEntity() {
        if (this.entity == null) {
            this.entity = new Entity(this.entityHandle);
        }
        return this.entity;
    }

}

