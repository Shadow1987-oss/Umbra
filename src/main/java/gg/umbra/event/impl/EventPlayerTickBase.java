package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.EntityPlayer;

public class EventPlayerTickBase
extends Event {
    private final Object playerHandle;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private EntityPlayer player;

    EventPlayerTickBase(Object playerHandle) {
        this.playerHandle = playerHandle;
    }


    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EntityPlayer getPlayer() {
        if (this.player == null) {
            this.player = new EntityPlayer(this.playerHandle);
        }
        return this.player;
    }

    @Override
    public boolean fire() {
        if (!MappedClasses.z5.isInstance(this.playerHandle)) {
            return false;
        }
        return super.fire();
    }
}

