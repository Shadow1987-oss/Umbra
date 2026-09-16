package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDeltaTracker;
import gg.umbra.wrapper.Wrapper;

public class DeltaTracker
extends Wrapper {
    public float getGameTimeDeltaPartialTick(boolean runsNormally) {
        return MDeltaTracker.getGameTimeDeltaPartialTick(DeltaTracker.umbraInstance.getMappingsMapperCompat().deltaTracker, this.I, runsNormally);
    }

    public DeltaTracker(Object handle) {
        super(handle);
    }

    public float getGameTimeDeltaTicks() {
        return MDeltaTracker.getGameTimeDeltaTicks(DeltaTracker.umbraInstance.getMappingsMapperCompat().deltaTracker, this.I);
    }
}
