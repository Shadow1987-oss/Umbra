package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MCaughtEntityActionBridge;
import gg.umbra.wrapper.Wrapper;

public class CaughtEntity
extends Wrapper {
    public CaughtEntity(Object object) {
        super(object);
    }

    public boolean inEventLoop() {
        return MCaughtEntityActionBridge.inEventLoop(CaughtEntity.umbraInstance.getMappingsMapperCompat().eventLoop, this.I);
    }

    public void execute(Runnable runnable) {
        MCaughtEntityActionBridge.execute(CaughtEntity.umbraInstance.getMappingsMapperCompat().eventLoop, this.I, runnable);
    }
}
