package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.module.HackModule;

public class EventModStateChange
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final boolean enabled;
    private final HackModule module;

    public EventModStateChange(HackModule module, boolean enabled) {
        this.module = module;
        this.enabled = enabled;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public HackModule getModule() {
        return this.module;
    }
}
