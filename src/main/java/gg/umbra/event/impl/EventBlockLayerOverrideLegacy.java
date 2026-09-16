package gg.umbra.event.impl;

import gg.umbra.event.impl.EventBlockLayerOverride;

public class EventBlockLayerOverrideLegacy
extends EventBlockLayerOverride {
    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventBlockLayerOverrideLegacy(Object blockHandle) {
        super(blockHandle);
    }
}
