package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventKeyInputBase;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.event.impl.EventPostAttack;
import gg.umbra.event.impl.EventPreAttack;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventSetSprinting;
import gg.umbra.event.impl.SyntheticAttackRequestEvent;
import gg.umbra.input.AttackKeyController;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.hacks.pvp.hitflick.HitFlickAdaptiveRotationController;
import gg.umbra.hacks.pvp.silentaura.SilentAuraAdaptiveRotationEntry;
import gg.umbra.hacks.pvp.silentaura.SilentAuraRotationMode;
import gg.umbra.system.PrimaryActionControlClaim;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.RotationAngles;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.Vec3d;
import gg.umbra.utils.network.PacketDispatchGuard;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.UseEntityPacketBridge;
import gg.umbra.wrapper.impl.Vec3;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class HitBypass
extends HackModule {
    private final ToggleSetting randomizeOffset;
    private final SliderSetting chance;
    private final SliderSetting angle;
    private boolean attackQueued;
    private boolean cancelSprint;
    private boolean attackKeyHeld;
    private int stateTicks;
    private final Queue<EventPacketSend> heldPackets;
    private final SliderSetting flickDelay;
    private final PacketDispatchGuard dispatchGuard;
    private boolean blinking;
    private final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
    private SilentAuraRotationMode rotationMode;
    private final RotationControlClaim rotationClaim;
    private final PrimaryActionControlClaim primaryClaim;
    private int blinkTicks;
    private boolean forwardKeyForced;
    private AdaptiveRotationController rotationController;
    private final TimerUtil flickDelayTimer;
    private float flickAngle;
    private SilentCombat silentAura;
    private final ToggleSetting selectHits;
    private boolean sprintCancelPending;
    private EntityLivingBase currentTarget;
    private boolean backKeyForced;
    private boolean attacked;
    private final ToggleSetting strafeInvert;
    private final SliderSetting randomizeOffsetRange;
    private boolean claimHeld;
    private final ToggleSetting blink = ToggleSetting.create(this, "PacketHold", false, "Chokes outgoing packets during the flick and flushes once the attack is sent");
    private final ItemFilterList allowedItems;
    private final ToggleSetting limitToItems;

    private double[] getTargetAimPoint(EntityLivingBase target) {
        EntityPlayerSP player = Minecraft.thePlayer();
        Vec3d closestPoint = RotationUtil.T(player, target.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0);
        return new double[]{closestPoint.getX(), closestPoint.getY(), closestPoint.getZ()};
    }

    private boolean isValidTarget(EntityLivingBase target) {
        if (target.isNull()) {
            return false;
        }
        if (target.equals(Minecraft.thePlayer())) {
            return false;
        }
        if (target.w$src$F$15l9epb() <= 0.0f || target.M$src$Z$ff28xj()) {
            return false;
        }
        if (!this.targetFilter.isValidTarget(target)) {
            return false;
        }
        double distance = Minecraft.thePlayer().i(target.z(), target.N(), target.h());
        return distance <= 5.0;
    }

    private boolean startFlick(EntityLivingBase target) {
        if (this.rotationMode != SilentAuraRotationMode.IDLE || !this.canStartFlick()) {
            return false;
        }
        if (!this.isValidTarget(target)) {
            return false;
        }
        if (this.selectHits.getEffectiveValue().booleanValue() && !this.isTargetVulnerable(target)) {
            return false;
        }
        if (!this.passesChanceRoll()) {
            return false;
        }
        if (!this.rotationClaim.isOwnedBy(this) && !this.rotationClaim.acquire(this, true)) {
            return false;
        }
        this.currentTarget = target;
        this.attackQueued = true;
        this.flickAngle = this.resolveStrafeAdjustedAngle();
        this.rotationMode = SilentAuraRotationMode.FLICKING_AWAY;
        this.stateTicks = 0;
        this.cancelSprint = true;
        this.flickDelayTimer.reset();
        this.startBlink();
        this.updateMovementKeys();
        this.updateRotationController();
        return true;
    }

    private boolean hasValidTarget() {
        return this.currentTarget != null && this.isValidTarget(this.currentTarget);
    }

    @Listen
    public void onPostAttack(EventPostAttack eventPostAttack) {
    }

    public SilentAuraRotationMode getRotationMode() {
        return this.rotationMode;
    }

    private void resetFlickState() {
        if (this.attackKeyHeld) {
            this.releaseAttackKey();
        }
        if (this.blinking || !this.heldPackets.isEmpty()) {
            this.flushHeldPackets();
        }
        this.attackQueued = false;
        this.attacked = false;
        this.attackKeyHeld = false;
        this.cancelSprint = false;
        this.sprintCancelPending = false;
        this.rotationMode = SilentAuraRotationMode.IDLE;
        this.stateTicks = 0;
        this.flickAngle = 0.0f;
        this.currentTarget = null;
        RotationManager.INSTANCE.setForwardMovementOverride(false);
        this.restoreBackKey();
        this.restoreForwardKey();
        this.rotationClaim.release(this);
        if (this.rotationController != null && RotationManager.INSTANCE.getActiveController() == this.rotationController) {
            this.rotationController.setScaleAxesProportionally(true);
            this.rotationController.setLinearAcceleration(true);
            RotationManager.INSTANCE.releaseController(this.rotationController);
        }
        this.rotationController = null;
    }

    @Listen(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress eventKeyPress) {
        boolean attackKeyEvent = eventKeyPress.isKeybinding(Minecraft.gameSettings().F());
        boolean shouldHandle = attackKeyEvent && (Packet.A() || eventKeyPress.isDown());
        if (shouldHandle) {
            this.handleAttackInput(eventKeyPress);
        }
    }

    @Listen
    public void onPreAttack(EventPreAttack eventPreAttack) {
    }

    private void sendQueuedAttack() {
        if (!this.attackQueued || this.attacked) {
            return;
        }
        boolean attackHeld = AttackKeyController.requestSyntheticAttack(this);
        if (attackHeld) {
            AttackKeyController.releaseAttackKey();
        }
        this.attacked = true;
        this.attackKeyHeld = attackHeld;
        this.attackQueued = false;
    }

    private float getFlickAngle() {
        return this.flickAngle;
    }

    private void releaseBlinkClaim() {
        this.blinking = false;
        this.blinkTicks = 0;
        if (this.claimHeld) {
            this.primaryClaim.clearClaimed();
            this.claimHeld = false;
        }
    }

    private float computeFlickAwayRotationSpeed() {
        return this.scaleAngleToRotationSpeed(this.getTargetRotationError() + 2.0f, 1.0f);
    }

    private RotationAngles calculateTargetRotation() {
        if (this.currentTarget == null || this.rotationController == null) {
            return null;
        }
        double[] aimPoint = this.getTargetAimPoint(this.currentTarget);
        return this.rotationController.calculateRotation(Vec3.create(aimPoint[0], aimPoint[1], aimPoint[2]));
    }

    private boolean interceptAttackRequest() {
        if (this.rotationMode != SilentAuraRotationMode.IDLE) {
            return true;
        }
        if (!this.canStartFlick()) {
            return false;
        }
        RayTraceResult targetRayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (targetRayTrace.isNull() || targetRayTrace.getEntity().isNull() || !targetRayTrace.getEntity().isInstance(MappedClasses.zm)) {
            return false;
        }
        return this.startFlick(new EntityLivingBase(targetRayTrace.getEntity()));
    }

    private void forceForwardKey() {
        KeyBinding forwardKey = Minecraft.gameSettings().r();
        if (!ClientSettings.isPhysicalKeyDown(forwardKey)) {
            forwardKey.setPressed(true);
            this.forwardKeyForced = true;
        } else {
            this.forwardKeyForced = false;
        }
    }

    private void forceBackKey() {
        KeyBinding backKey = Minecraft.gameSettings().Y();
        if (!ClientSettings.isPhysicalKeyDown(backKey)) {
            backKey.setPressed(true);
            this.backKeyForced = true;
        } else {
            this.backKeyForced = false;
        }
    }

    public float getFlickAwayRotationSpeed() {
        return this.computeFlickAwayRotationSpeed();
    }


    private float getYawError(float targetYaw) {
        return Math.abs(MathUtil.wrapAngleTo180(RotationManager.INSTANCE.getManagedYaw() - targetYaw));
    }

    @Listen(priority=EventPriority.HIGH)
    public void onSyntheticAttackRequest(SyntheticAttackRequestEvent syntheticAttackRequestEvent) {
        if (syntheticAttackRequestEvent.getSource() == this) {
            return;
        }
        if (this.interceptAttackRequest()) {
            syntheticAttackRequestEvent.setCancelled(true);
        }
    }

    @Listen(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton eventMouseButton) {
        boolean attackButtonEvent = eventMouseButton.isKeybinding(Minecraft.gameSettings().F());
        boolean shouldHandle = attackButtonEvent && (!Packet.h() || eventMouseButton.isDown());
        if (shouldHandle) {
            this.handleAttackInput(eventMouseButton);
        }
    }

    private boolean isAttackPacket(Packet packet) {
        if (!UseEntityPacketBridge.isUseEntityPacket(packet)) {
            return false;
        }
        return new UseEntityPacketBridge(packet).isAttack();
    }

    private boolean hasAllowedItem() {
        if (!this.limitToItems.getEffectiveValue().booleanValue()) {
            return true;
        }
        ItemStack itemStack = Minecraft.thePlayer().getHeldItemHand();
        return this.allowedItems.isValid(itemStack, false);
    }

    @Override
    public void onDisable() {
        this.resetFlickState();
    }

    private float scaleAngleToRotationSpeed(float angle, float scale) {
        if (scale <= 0.0f) {
            return 0.0f;
        }
        return angle / (1.875f * scale);
    }

    @Override
    public String getDetailedSuffix() {
        String displayAngle = this.angle.getDisplayValue();
        if (this.rotationMode != SilentAuraRotationMode.IDLE) {
            displayAngle = "\u00a7c" + displayAngle;
        }
        return displayAngle + "deg";
    }

    private void restoreBackKey() {
        if (this.backKeyForced) {
            KeyBinding backKey = Minecraft.gameSettings().Y();
            backKey.setPressed(ClientSettings.isPhysicalKeyDown(backKey));
            this.backKeyForced = false;
        }
    }

    @Listen
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        if (this.cancelSprint) {
            eventPrePlayerTick.getThePlayer().R(false);
            this.sprintCancelPending = true;
            this.cancelSprint = false;
        }
    }

    @Listen
    public void onSetSprinting(EventSetSprinting eventSetSprinting) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            this.sprintCancelPending = false;
            return;
        }
        if (this.sprintCancelPending && eventSetSprinting.isNewStateSprinting() && eventSetSprinting.getEntity().isNotNull() && eventSetSprinting.getEntity().S() == player.S()) {
            eventSetSprinting.setCancelled(true);
            this.sprintCancelPending = false;
        }
    }

    private EntityLivingBase getSilentAuraTargetUnderCrosshair() {
        if (this.silentAura == null) {
            this.silentAura = Umbra.INSTANCE.getHackManager().getMod(SilentCombat.class);
        }
        if (this.silentAura == null || !this.silentAura.isEnabled() || !this.silentAura.canClickAttack()) {
            return new EntityLivingBase(null);
        }
        EntityLivingBase target = this.silentAura.getTarget();
        if (target.isNull()) {
            return target;
        }
        RayTraceResult targetRayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (targetRayTrace.isNotNull() && targetRayTrace.getEntity().isNotNull() && target.equals(targetRayTrace.getEntity())) {
            return target;
        }
        return new EntityLivingBase(null);
    }

    private void updateMovementKeys() {
        if (this.rotationMode != SilentAuraRotationMode.ATTACKING) {
            RotationManager.INSTANCE.setForwardMovementOverride(false);
            this.restoreBackKey();
            this.restoreForwardKey();
            return;
        }
        this.forceBackKey();
        this.forceForwardKey();
        RotationManager.INSTANCE.setForwardMovementOverride(true);
    }

    private boolean isTargetVulnerable(EntityLivingBase target) {
        return target.c$src$I$15a9iwo() <= AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
    }

    private float getPitchError(float targetPitch) {
        return Math.abs(MathUtil.wrapAngleTo180(RotationManager.INSTANCE.getManagedPitch() - targetPitch));
    }

    private float computeAttackRotationSpeed() {
        return Math.max(48.0f, this.scaleAngleToRotationSpeed(this.getAbsoluteFlickAngle(), 1.0f));
    }

    private void startBlink() {
        if (!this.blink.getEffectiveValue().booleanValue() || this.primaryClaim.isClaimed()) {
            return;
        }
        this.blinking = true;
        this.claimHeld = true;
        this.blinkTicks = 0;
        this.primaryClaim.markClaimed();
    }

    public float getAttackRotationSpeed() {
        return this.computeAttackRotationSpeed();
    }

    private void updateRotationController() {
        if (!this.hasValidTarget()) {
            this.resetFlickState();
            return;
        }
        if (this.rotationController == null) {
            this.rotationController = new HitFlickAdaptiveRotationController(this);
            this.rotationController.setRelativeMode(false);
            this.rotationController.setRetainAfterCompletion(true);
            this.rotationController.setClampStepToRemaining(true);
            this.rotationController.setTolerance(0.0f);
            this.rotationController.setScaleAxesProportionally(false);
            this.rotationController.setLinearAcceleration(false);
        }
        double[] aimPoint = this.getTargetAimPoint(this.currentTarget);
        this.rotationController.setTarget(aimPoint[0], aimPoint[1], aimPoint[2]);
        this.rotationController.setYawOffset(this.rotationMode == SilentAuraRotationMode.FLICKING_AWAY ? this.getFlickAngle() : 0.0f);
        this.rotationController.setRelativeMode(false);
        if (RotationManager.INSTANCE.getActiveController() == null || RotationManager.INSTANCE.getActiveController() != this.rotationController) {
            RotationManager.INSTANCE.setController(this.rotationController);
        }
    }

    private float getTargetRotationError() {
        RotationAngles rotationAngles = this.calculateTargetRotation();
        if (rotationAngles == null) {
            return 0.0f;
        }
        float yawError = this.getYawError(rotationAngles.getYaw());
        float pitchError = this.getPitchError(rotationAngles.getPitch());
        return Math.max(yawError, pitchError);
    }

    private void flushHeldPackets() {
        if (this.heldPackets.isEmpty()) {
            this.releaseBlinkClaim();
            return;
        }
        this.heldPackets.forEach(this.dispatchGuard::o);
        this.heldPackets.clear();
        this.releaseBlinkClaim();
    }

    private void restoreForwardKey() {
        if (this.forwardKeyForced) {
            KeyBinding forwardKey = Minecraft.gameSettings().r();
            forwardKey.setPressed(ClientSettings.isPhysicalKeyDown(forwardKey));
            this.forwardKeyForced = false;
        }
    }

    @Listen
    public void onPacketSend(EventPacketSend eventPacketSend) {
        if (eventPacketSend.isCanceled() || eventPacketSend.wasModified()) {
            return;
        }
        Packet packet = eventPacketSend.getPacket();
        if (packet.isNull() || this.dispatchGuard.R(packet)) {
            return;
        }
        if (!this.blinking) {
            return;
        }
        this.heldPackets.add(eventPacketSend);
        eventPacketSend.setCancelled(true);
        if (this.isAttackPacket(packet)) {
            this.flushHeldPackets();
        }
    }

    public HitBypass() {
        super("HitFlick", -3580417, Category.COMBAT, "Flicks off and on target to displace knockback angle");
        this.angle = SliderSetting.create(this, "Angle", "#", "deg", 0.0, 90.0, 360.0, 1.0, "0 = none, 90 = right, 180 = pull toward, 270 = left");
        this.chance = SliderSetting.create(this, "Chance", "#", "%", 0.0, 100.0, 100.0, 1.0, "Chance of starting a hit flick for a given attack");
        this.flickDelay = SliderSetting.create(this, "Flick delay", "#", "ms", 0.0, 250.0, 2000.0, 25.0, "Minimum delay between hit flick attempts");
        this.randomizeOffset = ToggleSetting.create(this, "Randomize offset", false, "Randomizes the configured angle by a per-flick range");
        this.randomizeOffsetRange = SliderSetting.create(this, "Randomize offset", "#", "deg", 0.0, 0.0, 180.0, 1.0, "Applies a random offset range around Angle\nExample: 10 means Angle +/- 5 degrees per flick");
        this.strafeInvert = ToggleSetting.create(this, "Strafe invert", false, "Flips the flick side when you strafe toward the current push direction");
        this.selectHits = ToggleSetting.create(this, "Select hits", true, "Only start a hit flick when the target is vulnerable");
        this.limitToItems = ToggleSetting.create(this, "Limit to items", false, "HitBypass functions only while holding selected items");
        this.allowedItems = ItemFilterList.create(this, "hitflick-alloweditems", "Allowed Items", ItemFilterList.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("swords")));
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.primaryClaim = SharedModuleControlClaims.primaryAction;
        this.dispatchGuard = PacketDispatchGuard.b;
        this.heldPackets = new LinkedList<EventPacketSend>();
        this.flickDelayTimer = new TimerUtil();
        this.rotationMode = SilentAuraRotationMode.IDLE;
        this.randomizeOffset.addDependentValues(this.randomizeOffsetRange);
        this.limitToItems.addDependentValues(this.allowedItems);
        this.limitToItems.setCompactListValue(this.allowedItems);
        this.addValue(this.targetFilter, this.angle, this.chance, this.flickDelay, this.randomizeOffset, this.randomizeOffsetRange, this.strafeInvert, this.selectHits, this.blink, this.limitToItems, this.allowedItems);
        this.chance.setMaximumFractionDigits(0);
        this.flickDelayTimer.x(-10000L);
        this.rotationClaim.setPriority(this, 6);
    }

    private void handleAttackInput(EventKeyInputBase inputEvent) {
        if (this.rotationMode != SilentAuraRotationMode.IDLE) {
            inputEvent.setCancelled(true);
            return;
        }
        if (!this.canStartFlick()) {
            return;
        }
        RayTraceResult targetRayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (targetRayTrace.isNull() || targetRayTrace.getEntity().isNull() || !targetRayTrace.getEntity().isInstance(MappedClasses.zm)) {
            return;
        }
        if (this.startFlick(new EntityLivingBase(targetRayTrace.getEntity()))) {
            inputEvent.setCancelled(true);
        }
    }

    private boolean passesChanceRoll() {
        return (Double)this.chance.getValue() >= Math.random() * 100.0;
    }

    private boolean hasReachedFlickRotation() {
        RotationAngles rotationAngles = this.calculateTargetRotation();
        if (rotationAngles == null) {
            return false;
        }
        float offsetYaw = rotationAngles.getYaw() + this.getFlickAngle();
        float yawError = this.getYawError(offsetYaw);
        float pitchError = this.getPitchError(rotationAngles.getPitch());
        return Math.max(yawError, pitchError) <= 6.0f;
    }

    private float getRandomizedFlickAngle() {
        if (!this.randomizeOffset.getEffectiveValue().booleanValue()) {
            return this.getConfiguredFlickAngle();
        }
        float offsetRange = ((Double)this.randomizeOffsetRange.getValue()).floatValue();
        if (offsetRange <= 0.0f) {
            return this.getConfiguredFlickAngle();
        }
        float randomOffset = (ThreadLocalRandom.current().nextFloat() - 0.5f) * offsetRange;
        return this.normalizeAngle(((Double)this.angle.getValue()).floatValue() + randomOffset);
    }

    private float normalizeAngle(float angleDegrees) {
        float normalized = angleDegrees % 360.0f;
        if (normalized < 0.0f) {
            normalized += 360.0f;
        }
        return normalized;
    }

    private void releaseAttackKey() {
        AttackKeyController.releaseAttackKey();
        this.attackKeyHeld = false;
    }

    private float resolveStrafeAdjustedAngle() {
        float flickAngle = this.getRandomizedFlickAngle();
        if (!this.strafeInvert.getEffectiveValue().booleanValue()) {
            return flickAngle;
        }
        boolean leftPressed = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().x$src$Lgg_umbra_wrapper_impl_KeyBinding_$1cf7isg());
        boolean rightPressed = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().g$src$Lgg_umbra_wrapper_impl_KeyBinding_$qqn5n3());
        if (leftPressed == rightPressed) {
            return flickAngle;
        }
        float signedAngle = MathUtil.wrapAngleTo180(flickAngle);
        if (Math.abs(signedAngle) <= 0.001f || Math.abs(Math.abs(signedAngle) - 180.0f) <= 0.001f) {
            return flickAngle;
        }
        if (leftPressed && signedAngle < 0.0f || rightPressed && signedAngle > 0.0f) {
            return this.normalizeAngle(-signedAngle);
        }
        return flickAngle;
    }

    private float getAbsoluteFlickAngle() {
        return Math.abs(MathUtil.wrapAngleTo180(this.getFlickAngle()));
    }

    private boolean canStartFlick() {
        if (!this.hasConfiguredAngle()) {
            return false;
        }
        if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull()) {
            return false;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        if (!this.hasAllowedItem()) {
            return false;
        }
        return this.flickDelayTimer.hasTimeElapsed(((Double)this.flickDelay.getValue()).longValue());
    }

    private float getConfiguredFlickAngle() {
        return this.normalizeAngle(((Double)this.angle.getValue()).floatValue());
    }

    private boolean hasConfiguredAngle() {
        return Math.abs(MathUtil.wrapAngleTo180(this.getConfiguredFlickAngle())) > 0.001f
                || this.randomizeOffset.getEffectiveValue() != false && ((Double)this.randomizeOffsetRange.getValue()).floatValue() > 0.001f;
    }

    @Listen(priority=EventPriority.LOWEREST)
    public void onTick(EventPreTick eventPreTick) {
        if (Minecraft.currentScreen().isNotNull() || eventPreTick.getThePlayer().isNull()) {
            this.resetFlickState();
            return;
        }
        if (this.rotationMode == SilentAuraRotationMode.IDLE) {
            return;
        }
        if (!this.hasValidTarget()) {
            this.resetFlickState();
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this) && !this.rotationClaim.acquire(this, true)) {
            this.resetFlickState();
            return;
        }
        if (this.blinking && ++this.blinkTicks >= 7) {
            this.flushHeldPackets();
        }
        ++this.stateTicks;
        switch (SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[this.rotationMode.ordinal()]) {
            case 1: {
                if (this.stateTicks < 5 && !this.hasReachedFlickRotation()) break;
                this.rotationMode = SilentAuraRotationMode.ATTACKING;
                this.stateTicks = 0;
                break;
            }
            case 2: {
                if (!this.attacked) {
                    this.sendQueuedAttack();
                }
                if (this.stateTicks < 2) break;
                this.resetFlickState();
            }
        }
        this.updateMovementKeys();
        this.updateRotationController();
    }

    @Listen(priority=EventPriority.HIGH)
    public void onClickMouse(EventClickMouse eventClickMouse) {
        if (this.rotationMode != SilentAuraRotationMode.IDLE) {
            return;
        }
        EntityLivingBase target = this.getSilentAuraTargetUnderCrosshair();
        if (target.isNotNull() && this.startFlick(target)) {
            eventClickMouse.setCancelled(true);
        }
    }
}
