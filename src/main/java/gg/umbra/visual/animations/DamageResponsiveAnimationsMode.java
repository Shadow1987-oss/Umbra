package gg.umbra.visual.animations;

import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventLivingUpdate;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventTickBase;
import gg.umbra.event.impl.SyntheticAttackRequestEvent;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.HackModule;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.visual.Animations;
import gg.umbra.rotation.RotationManager;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;

public class DamageResponsiveAnimationsMode
extends AnimationsMode {
    private long releaseTime;
    private boolean damageTriggered;
    private boolean useKeyPressed;

    private void triggerUseForTarget(EntityPlayerSP player) {
        if (((Animations)this.getParent()).requiresMouseDown() && !gg.umbra.config.ClientSettings.isUseItemButtonDown()) {
            return;
        }
        RayTraceResult rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTraceResult.isNotNull() && rayTraceResult.getEntity().isInstance(MappedClasses.zm)) {
            EntityLivingBase target = new EntityLivingBase(rayTraceResult.getEntity());
            int hurtTime = target.c$src$I$15a9iwo();
            int expectedHurtTime = AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
            if (!this.useKeyPressed && !player.o$src$Z$1iprrmi() && hurtTime <= expectedHurtTime + 1 && hurtTime <= expectedHurtTime && ((Animations)this.getParent()).findDefaultTarget() != null) {
                this.setUseKeyPressed(true);
                this.releaseTime = System.currentTimeMillis() + 50L * (long)(expectedHurtTime + 1);
            }
        }
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (Minecraft.thePlayer().isNull() || !((Animations)this.getParent()).isHoldingSword()) {
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        EntityLivingBase target = ((Animations)this.getParent()).findDefaultTarget();
        boolean playerHurtTimeExpired = event.getThePlayer().c$src$I$15a9iwo() > AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
        boolean releaseTimeReached = this.releaseTime > 0L && System.currentTimeMillis() >= this.releaseTime;
        if (target == null || playerHurtTimeExpired || releaseTimeReached) {
            this.setUseKeyPressed(false);
            return;
        }
        if (((Animations)this.getParent()).requiresMouseDown() && !gg.umbra.config.ClientSettings.isUseItemButtonDown()) {
            return;
        }
        int playerHurtTime = event.getThePlayer().c$src$I$15a9iwo();
        int expectedHurtTime = AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 2;
        boolean targetCanBeHit = target.c$src$I$15a9iwo() <= expectedHurtTime + 2;
        if (!(this.useKeyPressed && this.damageTriggered && targetCanBeHit || this.useKeyPressed || playerHurtTime > expectedHurtTime + 1 || playerHurtTime <= 0)) {
            this.setUseKeyPressed(true);
            this.damageTriggered = true;
            this.releaseTime = System.currentTimeMillis() + 50L * (long)expectedHurtTime;
        }
    }

    @Override
    public boolean shouldBlock() {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return this.useKeyPressed;
    }


    public DamageResponsiveAnimationsMode(HackModule parent, String name) {
        super(parent, name);
    }

    @Listen(priority=EventPriority.LOW)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (event.isCanceled() || !((Animations)this.getParent()).isHoldingSword()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        this.triggerUseForTarget(player);
    }

    public void setUseKeyPressed(boolean pressed) {
        if (this.useKeyPressed != pressed) {
            this.useKeyPressed = pressed;
            this.releaseTime = 0L;
            this.damageTriggered = false;
            Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362().setPressed(pressed);
        }
    }

    @Listen
    public void onLivingUpdate(EventLivingUpdate event) {
        if (!((Animations)this.getParent()).isHoldingSword()) {
            return;
        }
        this.releaseTime = System.currentTimeMillis();
        if (event.getEntity().getObject().equals(Minecraft.thePlayer().getObject())) {
            EventTickBase.PRE_TICK_EXECUTOR.execute(this::resetUseKey);
        }
    }

    @Listen
    public void onMouseButton(EventMouseButton event) {
        int buttonBinding = -100 + event.getButton();
        if (!event.getButtonState() || !((Animations)this.getParent()).isHoldingSword()) {
            return;
        }
        if (buttonBinding == Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362().getKeyCode() && ((Animations)this.getParent()).requiresMouseDown() && gg.umbra.config.ClientSettings.isAttackButtonDown()) {
            event.setCancelled(true);
            return;
        }
        if (buttonBinding == Minecraft.gameSettings().F().getKeyCode()) {
            this.triggerUseForTarget(event.getThePlayer());
        }
    }

    private void resetUseKey() {
        this.setUseKeyPressed(false);
    }
}
