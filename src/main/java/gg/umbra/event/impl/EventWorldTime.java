package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.wrapper.impl.WorldInfo;

public class EventWorldTime
extends Event {
    private long worldTime = 0L;
    private final Object worldInfoHandle;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public void setWorldTime(long worldTime) {
        this.worldTime = worldTime;
        this.setCancelled(true);
    }

    public EventWorldTime(Object worldInfoHandle) {
        this.worldInfoHandle = worldInfoHandle;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public WorldInfo getWorldInfo() {
        return new WorldInfo(this.worldInfoHandle);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public long getWorldTime() {
        return this.worldTime;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
