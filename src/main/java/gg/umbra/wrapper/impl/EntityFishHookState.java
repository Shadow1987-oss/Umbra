package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityFishHook;
import gg.umbra.wrapper.Wrapper;

public class EntityFishHookState
extends Wrapper {
    public boolean isOpen() {
        return MEntityFishHook.isOpen(EntityFishHookState.umbraInstance.getMappingsMapperCompat().nettyChannel, this.I);
    }

    public CaughtEntity eventLoop() {
        return new CaughtEntity(MEntityFishHook.eventLoop(EntityFishHookState.umbraInstance.getMappingsMapperCompat().nettyChannel, this.I));
    }

    public EntityFishHookState(Object object) {
        super(object);
    }
}
