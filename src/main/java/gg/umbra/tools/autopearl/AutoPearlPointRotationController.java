package gg.umbra.tools.autopearl;

import gg.umbra.tools.AutoPearl;
import gg.umbra.rotation.PointRotationController;
import gg.umbra.wrapper.impl.Vec3;

public class AutoPearlPointRotationController
extends PointRotationController {
    final Float pitch;
    final AutoPearl module;

    public AutoPearlPointRotationController(AutoPearl autoPearl, Vec3 vec3, Float pitch) {
        super(vec3);
        this.module = autoPearl;
        this.pitch = pitch;
    }

    @Override
    public void setTargetRotation(float yaw, float pitch) {
        super.setTargetRotation(yaw, this.pitch.floatValue());
    }
}
