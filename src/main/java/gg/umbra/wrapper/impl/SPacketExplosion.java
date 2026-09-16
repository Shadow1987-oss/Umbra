package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SPacketExplosion
extends Wrapper {
    public void setPosition(float position) {
        SPacketExplosion.umbraInstance.getMappingsMapperCompat().hQ.setPosition(this.I, position);
    }

    public void setPreviousSpeed(float previousSpeed) {
        SPacketExplosion.umbraInstance.getMappingsMapperCompat().hQ.setPreviousSpeed(this.I, previousSpeed);
    }

    public float getSpeed() {
        return SPacketExplosion.umbraInstance.getMappingsMapperCompat().hQ.getSpeed(this.I);
    }

    public float getPreviousSpeed() {
        return SPacketExplosion.umbraInstance.getMappingsMapperCompat().hQ.getPreviousSpeed(this.I);
    }

    public SPacketExplosion(Object handle) {
        super(handle);
    }

    public float getPosition() {
        return SPacketExplosion.umbraInstance.getMappingsMapperCompat().hQ.getPosition(this.I);
    }
}
