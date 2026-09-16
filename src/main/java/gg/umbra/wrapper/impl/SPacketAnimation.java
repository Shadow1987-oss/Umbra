package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SPacketAnimation
extends Wrapper {
    public int getEntityId() {
        return SPacketAnimation.umbraInstance.getMappingsMapperCompat().DF.getEntityId(this.I);
    }

    public int getAnimationType() {
        return SPacketAnimation.umbraInstance.getMappingsMapperCompat().DF.getAnimationType(this.I);
    }

    public SPacketAnimation(Object handle) {
        super(handle);
    }
}
