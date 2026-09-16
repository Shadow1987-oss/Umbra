package gg.umbra.event.impl;

import gg.umbra.event.Event;
import gg.umbra.event.EventListeners;
import gg.umbra.module.HackModule;

public class SyntheticAttackRequestEvent
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final HackModule source;

    public HackModule getSource() {
        return this.source;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public SyntheticAttackRequestEvent(HackModule mod) {
        this.source = mod;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
