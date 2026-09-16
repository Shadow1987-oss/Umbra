package gg.umbra.tools.inventory;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.tools.inventory.InventoryActionGuard;
import gg.umbra.utils.RotationUtil;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityOtherPlayerMP;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.World;

public class ClosePlayerHealthInventoryActionGuard
extends InventoryActionGuard {
    private Entity nearestPlayer;
    private double nearestDistance = 99.0;

    @Override
    public void update(EntityLivingBase entity) {
        World world = entity.getWorld();
        if (world.isNull() || this.trackedWorld != null && this.trackedWorld.isNotNull() && !world.equals(this.trackedWorld)) {
            this.reset();
            return;
        }
        for (Object entityObject : Minecraft.theWorld().X()) {
            if (!MappedClasses.lG.isInstance(entityObject)) continue;
            EntityOtherPlayerMP otherPlayer = new EntityOtherPlayerMP(entityObject);
            EntityPlayerSP localPlayer = Minecraft.thePlayer();
            double distance = RotationUtil.y(localPlayer.z(), localPlayer.N(), localPlayer.h(), otherPlayer.z(), otherPlayer.N(), otherPlayer.h());
            if (!(distance <= 2.0) || !(distance < this.nearestDistance)) continue;
            this.nearestDistance = distance;
            this.nearestPlayer = otherPlayer;
        }
        if (this.nearestDistance != 99.0) {
            this.activateBlock();
        } else {
            this.reset();
        }
        if (this.blocked) {
            if (RotationUtil.D(entity, 10) == 0) {
                this.reset();
            } else if (RotationUtil.d(entity)) {
                ++this.stationaryTicks;
                if (this.stationaryTicks >= 40) {
                    this.reset();
                } else {
                    this.stationaryTicks = 0;
                }
            }
            if (this.remainingCooldownTicks > 0) {
                --this.remainingCooldownTicks;
            } else {
                this.reset();
            }
        }
        RayTraceResult rayTraceResult = Minecraft.p$src$Lgg_umbra_wrapper_impl_RayTraceResult_$5rw6n0();
        double health = entity.w$src$F$15l9epb();
        boolean lowHealthOrHurt = health < this.previousHealth || entity.V$src$I$fk0dv5() == 20;
        boolean lookingAtEntity = rayTraceResult.isNotNull() && rayTraceResult.getEntity().isNotNull() && entity.Y$src$Z$154rldp();
        if (lookingAtEntity || lowHealthOrHurt) {
            this.activateBlock();
        }
        this.previousHealth = health;
        this.trackedWorld = world;
    }

    @Override
    public void reset() {
        super.reset();
        this.nearestDistance = 99.0;
        this.nearestPlayer = null;
    }

    public ClosePlayerHealthInventoryActionGuard(int cooldownTicks) {
        super(cooldownTicks);
    }

}

