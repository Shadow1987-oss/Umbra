package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SPacketEntityStatus
extends Wrapper {
    public byte getLogicOpcode() {
        return SPacketEntityStatus.umbraInstance.getMappingsMapperCompat().RO.getLogicOpcode(this.I);
    }

    public int getEntityId() {
        return SPacketEntityStatus.umbraInstance.getMappingsMapperCompat().RO.getEntityId(this.I);
    }

    public SPacketEntityStatus(Object handle) {
        super(handle);
    }
}
