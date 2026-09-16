package gg.umbra.worldmods;

import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.MovementInputLock;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.movement.MovementInputHelper;
import gg.umbra.movement.PlayerMovementTaskManager;
import gg.umbra.movement.TargetPositionMovementTask;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.FixedRotationController;
import gg.umbra.rotation.RotationAngles;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Vec3;
import java.util.Random;

public class AntiAFK
extends HackModule {
    private FixedRotationController rotationController;
    private final TimerUtil rotationTimer;
    private final SliderSetting frequency;
    private RotationAngles referenceRotation;
    private final SliderSetting maxPitchChange;
    private final Random random;
    private long nextRotationDelay = -1L;
    private final RotationControlClaim rotationClaim;
    private Vec3 referencePosition;
    private boolean disablePending;
    private final RandomRangeSetting startDelay;
    private long nextMovementDelay = -1L;
    private final ToggleSetting keepClose;
    private final ToggleSetting silentAim;
    private final MovementInputLock movementInputLock;
    private final TimerUtil movementDurationTimer;
    private final ToggleSetting rotationEnabled;
    private final SliderSetting maxYawChange;
    private TargetPositionMovementTask movementTask;
    private final TimerUtil movementTimer;
    private final TimerUtil inactivityTimer = new TimerUtil();

    public AntiAFK() {
        super("Anti-AFK", 0, Category.WORLD);
        this.movementTimer = new TimerUtil();
        this.rotationTimer = new TimerUtil();
        this.movementDurationTimer = new TimerUtil();
        this.random = new Random();
        this.startDelay = RandomRangeSetting.createWithDescription(this, "Start delay", "##", "sec", 10.0, 30.0, 40.0, 200.0, 1.0, "How long to wait after moving to start");
        this.frequency = SliderSetting.createWithDescription(this, "Frequency", "#.#", "", 0.1, 0.2, 20.0, "How often you should move");
        this.rotationEnabled = ToggleSetting.create(this, "Rotation", true, "Moves your camera around");
        this.silentAim = ToggleSetting.create(this, "Silent aim", false, "Uses Silent Aim system");
        this.keepClose = ToggleSetting.create(this, "Keep close", false, "Keeps your position and rotation close to the original");
        this.maxYawChange = SliderSetting.create(this, "Max yaw change", "#", "\u00b0", 1.0, 10.0, 180.0, 1.0, "Max you will turn left and right");
        this.maxPitchChange = SliderSetting.create(this, "Max pitch change", "#", "\u00b0", 1.0, 5.0, 90.0, 1.0, "Max you will tilt up and down");
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.movementInputLock = SharedModuleControlClaims.movementInput;
        this.rotationEnabled.addDependentValues(this.silentAim, this.maxYawChange, this.maxPitchChange);
        this.addValue(this.startDelay, this.frequency, this.keepClose, this.rotationEnabled, this.silentAim, this.maxYawChange, this.maxPitchChange);
    }

    @Override
    public String getId() {
        return "antiafk";
    }

    private void captureReferencePose() {
        if (this.referencePosition == null) {
            this.referencePosition = Vec3.create(Minecraft.thePlayer().z(), Minecraft.thePlayer().N(), Minecraft.thePlayer().h());
        }
        if (this.referenceRotation == null) {
            this.referenceRotation = new RotationAngles(RotationManager.INSTANCE.getManagedYaw(), RotationManager.INSTANCE.getManagedPitch());
        }
    }

    private void resetActionState() {
        this.inactivityTimer.reset();
        this.nextMovementDelay = (long) this.startDelay.getRandomRangeSetting() * 1000L;
        this.nextRotationDelay = (long) this.startDelay.getRandomRangeSetting() * 1000L;
        this.referencePosition = null;
        this.referenceRotation = null;
        this.movementTimer.reset();
        this.rotationTimer.reset();
        this.movementDurationTimer.reset();
    }

    @Listen(priority = EventPriority.LOW)
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP entityPlayerSP = eventPreTick.getThePlayer();
        if (Minecraft.thePlayer().isNull() || Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
            return;
        }
        if (this.disablePending || this.shouldPauseForInput() || this.rotationClaim.isBlockedFor(this)) {
            this.resetActionState();
            this.clearActionState();
            this.removeMovementTarget();
            return;
        }
        if (!this.inactivityTimer.hasTimeElapsed((long) this.startDelay.getRandomRangeSetting() * 1000L)) {
            return;
        }
        int intervalRange = (int) (1000.0f / ((Double) this.frequency.getValue()).floatValue());
        if (this.rotationController != null && !this.rotationController.shouldRetainAfterCompletion() && this.rotationController.isComplete()) {
            this.clearActionState();
        }
        boolean ownsRotationClaim = this.rotationClaim.isOwnedBy(this) || this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue());
        boolean shouldRotate = this.rotationEnabled.getEffectiveValue() && ownsRotationClaim && Minecraft.currentScreen().isNull();
        if (shouldRotate && this.rotationTimer.hasTimeElapsed(this.nextRotationDelay)) {
            this.captureReferencePose();
            this.nextRotationDelay = this.random.nextInt(1000 + intervalRange);
            this.updateRandomRotation();
            this.rotationTimer.reset();
        }
        if (this.movementTask == null) {
            if (!this.movementInputLock.isLocked() && this.movementTimer.hasTimeElapsed(this.nextMovementDelay)) {
                this.captureReferencePose();
                this.nextMovementDelay = this.random.nextInt(1000 + intervalRange);
                this.movementInputLock.lock();
                this.queueMovementTarget(entityPlayerSP);
                this.movementTimer.reset();
                this.movementDurationTimer.reset();
            }
        } else if (this.movementDurationTimer.hasTimeElapsed(100 + this.random.nextInt(100))) {
            this.removeMovementTarget();
        }
    }

    @Override
    public void onEnable() {
        this.clearActionState();
        this.resetActionState();
    }

    private void removeMovementTarget() {
        if (this.movementTask != null) {
            PlayerMovementTaskManager.INSTANCE.cancel(this.movementTask);
            this.movementInputLock.unlock();
            this.movementTask = null;
        }
    }

    private void queueMovementTarget(EntityPlayerSP entityPlayerSP) {
        double targetX = entityPlayerSP.z() + this.random.nextDouble() * 2.0 - 1.0;
        double targetZ = entityPlayerSP.h() + this.random.nextDouble() * 2.0 - 1.0;
        if (this.keepClose.getEffectiveValue().booleanValue() && this.referencePosition != null) {
            double deltaX = this.referencePosition.getX() - entityPlayerSP.z();
            double deltaZ = this.referencePosition.getZ() - entityPlayerSP.h();
            double distanceFromReference = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            if (distanceFromReference >= 0.75) {
                targetX = this.referencePosition.getX();
                targetZ = this.referencePosition.getZ();
            }
        }
        if (this.movementTask == null) {
            this.movementTask = new TargetPositionMovementTask(targetX, targetZ);
        }
        this.movementTask.setRestoreInputOnCompletion(true);
        PlayerMovementTaskManager.INSTANCE.submit(this.movementTask);
    }

    private boolean shouldPauseForInput() {
        boolean focused = InputEventDispatcher.getInstance().getFocusState().isFocused();
        return focused && MovementInputHelper.isPhysicalMovementInputActive();
    }

    @Override
    public void onDisable() {
        this.clearActionState();
        this.removeMovementTarget();
        this.movementInputLock.unlock();
        this.rotationClaim.release(this);
    }

    @Override
    public void setEnabled(boolean enabled, boolean bypassVisibilityCheck) {
        if (!enabled && this.rotationController instanceof AdaptiveRotationController) {
            this.disablePending = !this.disablePending;
        } else {
            this.disablePending = false;
            super.setEnabled(enabled, bypassVisibilityCheck);
        }
    }

    private void clearActionState() {
        if (this.rotationController != null) {
            RotationManager.INSTANCE.releaseController(this.rotationController);
            this.rotationController.setScaleAxesProportionally(true);
            this.rotationController.setRandomizeMovement(false);
            this.rotationController.setCubicAcceleration(false);
            this.rotationController.setAngleBasedAcceleration(true);
            this.rotationController.setSpeed(6.0f);
        }
        if (RotationManager.INSTANCE.getActiveController() == null || RotationManager.INSTANCE.getActiveController() != this.rotationController || this.rotationController != null && !this.rotationController.shouldRetainAfterCompletion() && this.rotationController.isComplete()) {
            this.rotationController = null;
            this.rotationClaim.release(this);
            if (this.disablePending) {
                this.disablePending = false;
                super.setEnabled(false);
            }
        }
    }

    private void updateRandomRotation() {
        float targetPitch;
        float targetYaw;
        float distanceFromReference = 0.0f;
        if (this.keepClose.getEffectiveValue().booleanValue() && this.referenceRotation != null) {
            float yawOffset = MathUtil.wrapAngleTo180(RotationManager.INSTANCE.getManagedYaw() - this.referenceRotation.getYaw());
            float pitchOffset = MathUtil.wrapAngleTo180(RotationManager.INSTANCE.getManagedPitch() - this.referenceRotation.getPitch());
            distanceFromReference = MathUtil.sqrt(yawOffset * yawOffset + pitchOffset * pitchOffset);
        }
        double maxReferenceDistance = Math.sqrt((Double) this.maxYawChange.getValue() * (Double) this.maxYawChange.getValue() + (Double) this.maxPitchChange.getValue() * (Double) this.maxPitchChange.getValue());
        double yawChange = this.random.nextDouble() * (Double) this.maxYawChange.getValue();
        double pitchChange = this.random.nextDouble() * (Double) this.maxPitchChange.getValue();
        if (this.random.nextDouble() > 0.6) {
            yawChange = -yawChange;
        }
        if (this.random.nextDouble() > 0.5) {
            pitchChange = -pitchChange;
        }
        if ((double) distanceFromReference >= maxReferenceDistance) {
            targetYaw = this.referenceRotation.getYaw();
            targetPitch = this.referenceRotation.getPitch();
        } else {
            targetYaw = (float) ((double) RotationManager.INSTANCE.getManagedYaw() + yawChange) % 360.0f;
            targetPitch = (float) ((double) RotationManager.INSTANCE.getManagedPitch() + pitchChange) % 90.0f;
        }
        if (this.rotationController == null) {
            this.rotationController = this.silentAim.getEffectiveValue() ? new AdaptiveRotationController(targetYaw, targetPitch) : new FixedRotationController(targetYaw, targetPitch);
        }
        this.rotationController.setTargetRotation(targetYaw, targetPitch);
        this.rotationController.setSpeed((int) (this.random.nextDouble() * 10.0));
        RotationManager.INSTANCE.setController(this.rotationController);
    }
}
