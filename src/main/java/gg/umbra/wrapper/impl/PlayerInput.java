package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MPlayerInput;
import gg.umbra.wrapper.Wrapper;

public class PlayerInput
extends Wrapper {
    public PlayerInput(Object handle) {
        super(handle);
    }

    public void setForwardImpulse(float forwardImpulse) {
        MPlayerInput.setY(PlayerInput.umbraInstance.getMappingsMapperCompat().playerInputVector, this.I, forwardImpulse);
    }

    public float getForwardImpulse() {
        return MPlayerInput.getY(PlayerInput.umbraInstance.getMappingsMapperCompat().playerInputVector, this.I);
    }

    public float getLeftImpulse() {
        return MPlayerInput.getX(PlayerInput.umbraInstance.getMappingsMapperCompat().playerInputVector, this.I);
    }

    public void setLeftImpulse(float leftImpulse) {
        MPlayerInput.setX(PlayerInput.umbraInstance.getMappingsMapperCompat().playerInputVector, this.I, leftImpulse);
    }
}
