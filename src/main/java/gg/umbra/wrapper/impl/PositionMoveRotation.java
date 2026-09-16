package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class PositionMoveRotation
extends Wrapper {
    public Vec3 getDeltaMovement() {
        return new Vec3(PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.getDeltaMovement(this.I));
    }

    public void setPitch(float pitch) {
        PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.setPitch(this.I, pitch);
    }

    public void setYaw(float yaw) {
        PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.setYaw(this.I, yaw);
    }

    public Vec3 getPosition() {
        return new Vec3(PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.getPosition(this.I));
    }

    public float getPitch() {
        return PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.getPitch(this.I);
    }

    public float getYaw() {
        return PositionMoveRotation.umbraInstance.getMappingsMapperCompat().qT.getYaw(this.I);
    }

    public PositionMoveRotation(Object handle) {
        super(handle);
    }
}
