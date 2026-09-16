package gg.umbra.event.impl;

import gg.umbra.event.impl.EventMotion;
import gg.umbra.wrapper.impl.Entity;

public class EventPostMotion
extends EventMotion {
    @Override
    public boolean fire() {
        if (player.isNull()) {
            return false;
        }
        boolean fired = super.fire();
        player.T(EventMotion.getSavedRidingEntity());
        return fired;
    }


    public EventPostMotion(Object playerHandle) {
        super(new Entity(playerHandle));
    }
}

