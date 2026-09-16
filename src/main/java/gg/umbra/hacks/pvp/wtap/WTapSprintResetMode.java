package gg.umbra.hacks.pvp.wtap;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventPacketReceive;
import gg.umbra.event.impl.EventPlayerUseItem;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventRightClickMouse;
import gg.umbra.event.impl.EventSendClickBlockToController;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.hacks.pvp.TargetSelect;
import gg.umbra.rotation.RotationManager;
import gg.umbra.unmap.ModeOption;
import gg.umbra.value.OptionSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.SPacketEntityVelocity;

public class WTapSprintResetMode
extends SubHack<TargetSelect> {
    private final OptionSetting preference;
    private final ModeOption criticalHitsOption;
    private int velocityTicks = 0;
    private boolean pendingTimestampUpdate;
    private static final long VELOCITY_RESET_TICKS = 988881679777005575L;
    private final ModeOption kbReductionOption = new ModeOption("KB reduction");
    private long lastResetTime;
    private boolean cancelUse = true;

    public WTapSprintResetMode(HackModule mod, String string) {
        super(mod, string);
        this.criticalHitsOption = new ModeOption("Critical hits");
        this.preference = OptionSetting.create((Object)this, "Preference", this.kbReductionOption, this.kbReductionOption, this.criticalHitsOption);
        this.preference.setDescription("KB reduction: Favors knockback reduction\nCritical hits: Favors critical hit frequency");
        this.addValue(this.preference);
    }

    @Listen
    public void onPlayerUseItem(EventPlayerUseItem eventPlayerUseItem) {
        if (this.cancelUse) {
            eventPlayerUseItem.setCancelled(true);
        }
    }

    private boolean isMovingTowardTarget(Entity entity) {
        double deltaX = entity.z() - Minecraft.thePlayer().z();
        double deltaZ = entity.h() - Minecraft.thePlayer().h();
        return deltaX < 0.0 == Minecraft.thePlayer().t() < 0.0
                && deltaZ < 0.0 == Minecraft.thePlayer().T() < 0.0;
    }

    @Listen
    public void onRightClickMouse(EventRightClickMouse eventRightClickMouse) {
        if (this.cancelUse) {
            eventRightClickMouse.setCancelled(true);
        }
    }

    @Listen
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        SPacketEntityVelocity velocityPacket;
        if (eventPacketReceive.getPacketInstance() == null) {
            return;
        }
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        Packet packet = eventPacketReceive.getPacket();
        boolean velocityReceived = false;
        if (packet.isInstance(MappedClasses.qe)) {
            velocityReceived = true;
        } else if (packet.isInstance(MappedClasses.YX)
                && (velocityPacket = new SPacketEntityVelocity(packet)).getEntityId() == Minecraft.thePlayer().S()) {
            velocityReceived = true;
        }
        if (velocityReceived) {
            this.velocityTicks = (int)VELOCITY_RESET_TICKS;
        }
    }

    private boolean prefersKnockbackReduction() {
        return this.preference.getValue() == this.kbReductionOption;
    }

    @Listen
    public void onClickMouse(EventClickMouse eventClickMouse) {
        RayTraceResult rayTrace;
        if (!((TargetSelect)this.getParent()).shouldTrigger()) {
            return;
        }
        boolean airborneAfterVelocity = false;
        if (this.velocityTicks > 0) {
            --this.velocityTicks;
            if (eventClickMouse.getThePlayer().b$src$Z$fqlxe4()) {
                this.velocityTicks = 0;
            }
            if (this.prefersKnockbackReduction()) {
                return;
            }
            if (eventClickMouse.getThePlayer().q() > 0.0) {
                airborneAfterVelocity = true;
            } else if (!eventClickMouse.getThePlayer().b$src$Z$fqlxe4()) {
                return;
            }
        }
        if ((rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace()).isNotNull()
                && rayTrace.getEntity().isInstance(MappedClasses.zm)) {
            EntityLivingBase target = new EntityLivingBase(rayTrace.getEntity());
            if (!this.isMovingTowardTarget(target)) {
                return;
            }
            if (!airborneAfterVelocity) {
                AttackPacketTimingTracker timingTracker = AttackPacketTimingTracker.INSTANCE;
                int expectedHurtTime = timingTracker.getExpectedHurtTimeTicks();
                int upperHurtTime = expectedHurtTime + 1;
                if (target.c$src$I$15a9iwo() <= expectedHurtTime) {
                    if (System.currentTimeMillis() - this.lastResetTime >= timingTracker.getAverageHitDelay() * 2L) {
                        this.pendingTimestampUpdate = true;
                        return;
                    }
                }
                if (target.c$src$I$15a9iwo() > expectedHurtTime
                        && target.c$src$I$15a9iwo() <= upperHurtTime) {
                    return;
                }
            }
            eventClickMouse.setCancelled(true);
            this.cancelUse = true;
            ClientSettings.applyAttackEffects(target);
        }
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        if (this.pendingTimestampUpdate) {
            this.lastResetTime = System.currentTimeMillis();
            this.pendingTimestampUpdate = false;
        }
        this.cancelUse = false;
    }

    @Listen
    public void onSendClickBlockToController(EventSendClickBlockToController eventSendClickBlockToController) {
        if (this.cancelUse) {
            eventSendClickBlockToController.setCancelled(true);
        }
    }

}
