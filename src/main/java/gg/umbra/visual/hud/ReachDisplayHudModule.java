package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPostAttack;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.rotation.RotationManager;
import gg.umbra.ui.click.frame.impl.hud.ReachDisplayHudFrame;
import gg.umbra.wrapper.impl.AxisAlignedBB;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.Vec3;

public class ReachDisplayHudModule
extends HudModule {
    private float savedPitch;
    private long lastAttackTime;
    private float savedYaw;
    private float lastReach;

    public float getLastReach() {
        if (System.currentTimeMillis() - this.lastAttackTime >= 5000L) {
            this.lastReach = 0.0f;
            this.lastAttackTime = 0L;
        }
        return this.lastReach;
    }

    private void restorePlayerRotation() {
        RotationManager rotationManager = RotationManager.INSTANCE;
        if (rotationManager.hasAdaptiveController()) {
            EntityLivingBase entityLivingBase = Minecraft.F();
            entityLivingBase.H(this.savedYaw);
            entityLivingBase.C(this.savedPitch);
        }
    }

    @Listen
    public void onPostAttack(EventPostAttack event) {
        if (event.getTarget().isInstance(MappedClasses.zm) && !event.getTarget().isInstance(MappedClasses.FT) && Minecraft.p$src$Lgg_umbra_wrapper_impl_RayTraceResult_$5rw6n0().isNotNull()) {
            Entity entity = Minecraft.p$src$Lgg_umbra_wrapper_impl_RayTraceResult_$5rw6n0().getEntity();
            if (entity.isNull()) {
                return;
            }
            EntityLivingBase player = Minecraft.F();
            double reachDistance = Minecraft.playerController().N();
            this.applyManagedRotation();
            Vec3 eyePosition = player.O(1.0f);
            Vec3 lookDirection = player.J(1.0f);
            this.restorePlayerRotation();
            Vec3 rayEnd = eyePosition.addVector(
                    lookDirection.getX() * reachDistance,
                    lookDirection.getY() * reachDistance,
                    lookDirection.getZ() * reachDistance);
            float collisionBorder = entity.b();
            AxisAlignedBB targetBounds = entity.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl()
                    .expand(collisionBorder, collisionBorder, collisionBorder);
            RayTraceResult intercept = targetBounds.calculateIntercept(eyePosition, rayEnd);
            if (intercept.isNull()) {
                return;
            }
            double attackDistance = eyePosition.distanceTo(intercept.getHitVec());
            this.lastReach = (float)attackDistance;
            this.lastAttackTime = System.currentTimeMillis();
        }
    }

    @Override
    public String getId() {
        return "reachdisplay";
    }

    public ReachDisplayHudModule() {
        super("Reach Display", HudModuleGroup.HUD, "reach_display", ReachDisplayHudFrame.class);
        this.setSuffix("Shows how far away your last attack was");
    }


    private void applyManagedRotation() {
        RotationManager rotationManager = RotationManager.INSTANCE;
        if (rotationManager.hasAdaptiveController()) {
            EntityLivingBase entityLivingBase = Minecraft.F();
            this.savedYaw = entityLivingBase.J();
            this.savedPitch = entityLivingBase.V();
            entityLivingBase.H(rotationManager.getManagedYaw());
            entityLivingBase.C(rotationManager.getManagedPitch());
        }
    }
}
