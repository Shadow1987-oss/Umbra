package gg.umbra.tools.mlg;

import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.hacks.exploits.blockin.BlockPlacementUtility;
import gg.umbra.hacks.exploits.blockin.HotbarSlotResolution;
import gg.umbra.hacks.exploits.blockin.HotbarSlotResolutionWithValue;
import gg.umbra.tools.MLG;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.FixedRotationController;
import gg.umbra.rotation.PointRotationController;
import gg.umbra.rotation.RotationManager;
import gg.umbra.rotation.WorldPointRotationTarget;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.datas.BlockCoordinate;
import gg.umbra.wrapper.impl.BlockPos;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MLGPlacementController {
    private final MLG MLG;

    @Nullable
    private FixedRotationController prepareRotation(@Nullable BlockCoordinate targetCoordinate, @Nullable FixedRotationController currentController, ItemMappingEntry itemMappingEntry) {
        FixedRotationController preparedController = currentController;
        if (targetCoordinate == null) {
            return preparedController;
        }
        if (preparedController != null && !(preparedController instanceof AdaptiveRotationController) && !(preparedController instanceof PointRotationController)) {
            BlockPlacementUtility.releaseRotationController(preparedController, false, true);
            preparedController = this.createRotationController(targetCoordinate, itemMappingEntry);
        }
        if (preparedController instanceof PointRotationController || preparedController instanceof AdaptiveRotationController) {
            BlockPlacementUtility.updateRotationTarget((WorldPointRotationTarget)((Object)preparedController), targetCoordinate, itemMappingEntry);
        }
        if (this.MLG.acquireRotationControl() && preparedController != null && !preparedController.equals(RotationManager.INSTANCE.getActiveController())) {
            RotationManager.INSTANCE.setController(preparedController);
        }
        return preparedController;
    }

    @NotNull
    public HotbarSlotResolution aimAtTarget(@NotNull ItemMappingEntry itemMappingEntry, @Nullable BlockCoordinate targetCoordinate, @Nullable FixedRotationController currentController, boolean pickupWater) {
        FixedRotationController preparedController = this.prepareRotation(targetCoordinate, currentController, itemMappingEntry);
        if (preparedController != null) {
            if (!preparedController.equals(currentController)) {
                if (pickupWater) {
                    this.MLG.pickupRotation = preparedController;
                } else {
                    this.MLG.placementRotation = preparedController;
                }
            }
            if (BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
                return HotbarSlotResolution.success("AimJob completed by looking at valid block");
            }
            if (preparedController.isComplete()) {
                return HotbarSlotResolution.success("AimJob was already completed");
            }
            if (!preparedController.equals(RotationManager.INSTANCE.getActiveController())) {
                if (this.MLG.acquireRotationControl()) {
                    RotationManager.INSTANCE.setController(preparedController);
                    return HotbarSlotResolution.pending("AimJob set as current job");
                }
                return HotbarSlotResolution.pending("AimJob is not current job");
            }
            return HotbarSlotResolution.pending("Waiting for AimJob to complete");
        }
        if (BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
            return HotbarSlotResolution.success("AimJob completed by looking at valid block");
        }
        return HotbarSlotResolution.failure("AimJob is null").force();
    }

    public MLGPlacementController(MLG MLG) {
        this.MLG = MLG;
    }

    private <T extends FixedRotationController> T configureController(T controller) {
        controller.setClampStepToRemaining(true);
        controller.setTolerance(0.1f);
        controller.setAngleBasedAcceleration(true);
        controller.setScaleAxesProportionally(true);
        controller.setRetainAfterCompletion(true);
        controller.setCubicAcceleration(true);
        controller.setLinearAcceleration(true);
        controller.setSpeed(((Double)this.MLG.aimSpeed.getValue()).floatValue());
        controller.setRandomizeMovement(true);
        if (controller instanceof AdaptiveRotationController) {
            ((AdaptiveRotationController)controller).setRelativeMode(false);
        }
        return controller;
    }


    public HotbarSlotResolutionWithValue<BlockPos> placeItem(@NotNull ItemMappingEntry itemMappingEntry, @Nullable BlockCoordinate targetCoordinate, @Nullable TimerUtil timeoutTimer) {
        HotbarSlotResolutionWithValue<BlockPos> result = new HotbarSlotResolutionWithValue<>();
        if (timeoutTimer != null && timeoutTimer.hasTimeElapsed(1000L)) {
            return (HotbarSlotResolutionWithValue)result.markFailure("Timed out while trying to place item");
        }
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull()) {
            return (HotbarSlotResolutionWithValue)result.markPending("Player is unavailable");
        }
        HotbarSlotResolution closeResult = this.MLG.closeInventory();
        if (closeResult.isFailure()) {
            return result.markFailure("Failed to close GUI due to: " + closeResult.getMessage()).setForced(closeResult.canContinue());
        }
        if (closeResult.isPending()) {
            return (HotbarSlotResolutionWithValue)result.markPending("Waiting to close GUI");
        }
        RayTraceResult rayTraceResult = BlockPlacementUtility.getPlacementRayTrace(itemMappingEntry);
        if (rayTraceResult.isNull()) {
            return (HotbarSlotResolutionWithValue)result.markPending("Waiting for mouse over to not be null");
        }
        if (!rayTraceResult.isBlockHit()) {
            return (HotbarSlotResolutionWithValue)result.markPending("Waiting for mouse over to be a block");
        }
        if (!BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
            return (HotbarSlotResolutionWithValue)result.markPending("Waiting to look at valid block");
        }
        BlockPos blockPos = BlockPlacementUtility.getPlacementBlockPos(itemMappingEntry);
        KeyBinding keyBinding = Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
        KeyBindingHelper.setPressedAndTick(keyBinding, true);
        KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
        return result.markSuccess("Placed MLG Item").setValue(blockPos);
    }

    @NotNull
    public FixedRotationController createRotationController(@Nullable BlockCoordinate targetCoordinate, @Nullable ItemMappingEntry itemMappingEntry) {
        FixedRotationController controller;
        if (targetCoordinate == null) {
            controller = this.MLG.silentAim.getEffectiveValue() != false ? new AdaptiveRotationController(-999.0f, 90.0f) : new FixedRotationController(-999.0f, 90.0f);
        } else {
            Vec3 aimPoint = BlockPlacementUtility.getAimPoint(targetCoordinate, itemMappingEntry);
            controller = this.MLG.silentAim.getEffectiveValue() != false ? new AdaptiveRotationController(aimPoint) : new PointRotationController(aimPoint);
        }
        return this.configureController(controller);
    }
}
