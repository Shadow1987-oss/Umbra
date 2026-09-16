package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MMovementInput;
import gg.umbra.visual.freecam.FreecamMovementInputBridge;
import gg.umbra.wrapper.Wrapper;

public class MovementInput
extends Wrapper {
    public void B(float f) {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.umbraInstance.getMappings().h7, this.I));
            playerInput.setForwardImpulse(f);
            return;
        }
        MMovementInput.p(MovementInput.umbraInstance.getMappings().h7, this.I, f);
    }

    public MovementInput(Object object) {
        super(object);
    }

    public void setFreecamInput(FreecamMovementInputBridge freecamMovementInputBridge) {
        MMovementInput.x(MovementInput.umbraInstance.getMappings().h7, this.I, freecamMovementInputBridge.getObject());
    }

    public void M(float f) {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.umbraInstance.getMappings().h7, this.I));
            playerInput.setLeftImpulse(f);
            return;
        }
        MMovementInput.j(MovementInput.umbraInstance.getMappings().h7, this.I, f);
    }

    public FreecamMovementInputBridge getFreecamInput() {
        return new FreecamMovementInputBridge(MMovementInput.x(MovementInput.umbraInstance.getMappings().h7, this.I));
    }

    public float T() {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.umbraInstance.getMappings().h7, this.I));
            return playerInput.getLeftImpulse();
        }
        return MMovementInput.b(MovementInput.umbraInstance.getMappings().h7, this.I);
    }

    public void V(boolean bl) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getFreecamInput().setJumping(bl);
            return;
        }
        MMovementInput.H(MovementInput.umbraInstance.getMappings().h7, this.I, bl);
    }

    public float D() {
        if (ForgeVersion.MC_1_21_6.d()) {
            PlayerInput playerInput = new PlayerInput(MMovementInput.W(MovementInput.umbraInstance.getMappings().h7, this.I));
            return playerInput.getForwardImpulse();
        }
        return MMovementInput.f(MovementInput.umbraInstance.getMappings().h7, this.I);
    }


    public boolean G() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return this.getFreecamInput().isJumping();
        }
        return MMovementInput.j(MovementInput.umbraInstance.getMappings().h7, this.I);
    }

    public void setCancelled(boolean bl) {
        if (ForgeVersion.MC_1_21_4.d()) {
            this.getFreecamInput().setSneaking(bl);
            return;
        }
        MMovementInput.V(MovementInput.umbraInstance.getMappings().h7, this.I, bl);
    }

    public boolean D$src$Z$v5d6e8() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return this.getFreecamInput().isSneaking();
        }
        return MMovementInput.R(MovementInput.umbraInstance.getMappings().h7, this.I);
    }
}

