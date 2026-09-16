package gg.umbra.event.impl;

import gg.umbra.config.Profile;
import gg.umbra.event.EventListeners;
import gg.umbra.event.IEvent;
import org.jetbrains.annotations.Nullable;

public class ProfileChangeEvent
implements IEvent {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Profile newProfile;
    @Nullable
    private final Profile previousProfile;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public ProfileChangeEvent(@Nullable Profile previousProfile, Profile newProfile) {
        this.previousProfile = previousProfile;
        this.newProfile = newProfile;
    }

    public Profile getNewProfile() {
        return this.newProfile;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public Profile getPreviousProfile() {
        return this.previousProfile;
    }
}
