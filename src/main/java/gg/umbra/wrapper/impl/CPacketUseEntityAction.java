package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class CPacketUseEntityAction
extends Wrapper {
    private CPacketUseEntityAction(Object handle) {
        super(handle);
    }

    public CPacketUseEntityAction(Object handle, CPacketUseEntityActionConstructorMarker constructorMarker) {
        this(handle);
    }

    public CPacketUseEntity getType() {
        return new CPacketUseEntity(CPacketUseEntityAction.umbraInstance.getMappingsMapperCompat().qz.getType(this.I), null);
    }
}
