package gg.umbra.visual.freecam;

import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;

public class FreecamMovementInputBridge
extends Wrapper {
    public void setSprinting(boolean sprinting) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), this.isJumping(),
                    this.isSneaking(), sprinting);
            return;
        }
        FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.K(this.I, sprinting);
    }

    public boolean isSneaking() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.r(this.I);
    }

    public boolean isMovingForward() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.H(this.I);
    }

    public boolean isMovingBackward() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.W(this.I);
    }

    public boolean isMovingLeft() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.K(this.I);
    }

    public FreecamMovementInputBridge(Object object) {
        super(object);
    }


    private void applyMovementInput(boolean forward, boolean backward, boolean left,
            boolean right, boolean jump, boolean sneak, boolean sprint) {
        FreecamMovementInputBridge input = new FreecamMovementInputBridge(
                FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.z(
                        forward, backward, left, right, jump, sneak, sprint));
        this.I = input.getObject();
        Minecraft.a_xH_J().a_jw_2_I().setFreecamInput(input);
    }

    public boolean isSprinting() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.Y(this.I);
    }

    public void setSneaking(boolean sneaking) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), this.isJumping(),
                    sneaking, this.isSprinting());
            return;
        }
        FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.V(this.I, sneaking);
    }

    public boolean isMovingRight() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.A(this.I);
    }

    public void setJumping(boolean jumping) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.applyMovementInput(this.isMovingForward(), this.isMovingBackward(),
                    this.isMovingLeft(), this.isMovingRight(), jumping,
                    this.isSneaking(), this.isSprinting());
            return;
        }
        FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.A(this.I, jumping);
    }

    public boolean isJumping() {
        return FreecamMovementInputBridge.umbraInstance.getMappingsMapperCompat().Cc.p(this.I);
    }
}

