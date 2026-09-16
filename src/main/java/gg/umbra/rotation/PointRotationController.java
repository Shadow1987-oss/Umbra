package gg.umbra.rotation;

import gg.umbra.rotation.FixedRotationController;
import gg.umbra.rotation.RotationAngles;
import gg.umbra.rotation.WorldPointRotationTarget;
import gg.umbra.utils.RotationVectorMath;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GuiScreen;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Vec3;

public class PointRotationController
extends FixedRotationController
implements WorldPointRotationTarget {
    private boolean normalizeYaw = true;
    private Vec3 target;

    public RotationAngles calculateTargetRotation(Vec3 target) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double legacyEyeOffset = ForgeVersion.MC_1_7_10.Y() ? (double)player.X() : 0.0;
        Vec3 targetPosition = Vec3.create(target.getX(), target.getY(), target.getZ());
        Vec3 eyePosition = Vec3.create(player.c(), player.A() + legacyEyeOffset, player.Z());
        return RotationVectorMath.H(eyePosition, targetPosition, this.getCurrentYaw(), this.isYawNormalized());
    }

    public boolean isYawNormalized() {
        return this.normalizeYaw;
    }


    public void updateTargetRotation() {
        this.setTargetRotation(this.calculateTargetRotation(this.target));
    }

    public void setNormalizeYaw(boolean normalizeYaw) {
        this.normalizeYaw = normalizeYaw;
    }

    public PointRotationController(double x, double y, double z) {
        this(Vec3.create(x, y, z));
    }

    @Override
    public Vec3 getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(double x, double y, double z) {
        this.setTarget(Vec3.create(x, y, z));
    }

    public PointRotationController(Vec3 target) {
        super(Minecraft.F().J(), Minecraft.F().V());
        this.target = target;
        this.setTargetRotation(this.calculateTargetRotation(target));
    }

    @Override
    public void setTarget(Vec3 target) {
        this.target = target;
    }

    @Override
    public void update(EntityPlayerSP player, GuiScreen screen) {
        if (player.isNotNull() && screen.isNull()) {
            this.updateTargetRotation();
        }
        super.update(player, screen);
    }
}

