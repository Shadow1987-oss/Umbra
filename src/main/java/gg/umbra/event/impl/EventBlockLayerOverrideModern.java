package gg.umbra.event.impl;

import gg.umbra.event.impl.EventBlockLayerOverride;

public class EventBlockLayerOverrideModern
extends EventBlockLayerOverride {
    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventBlockLayerOverrideModern(Object blockHandle) {
        super(blockHandle);
    }
}
