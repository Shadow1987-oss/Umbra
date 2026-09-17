package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.notification.NotificationType;
import gg.umbra.rotation.PointRotationController;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.utils.BlockUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.BlockPos;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumFacing;
import gg.umbra.wrapper.impl.InventoryPlayer;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Vec3;
import gg.umbra.wrapper.impl.Vec3i;
import gg.umbra.wrapper.impl.World;

public class Surround
extends HackModule {
    private final SliderSetting aimSpeed;
    private final SliderSetting actionDelay;
    private final ToggleSetting silentAim;
    private final ToggleSetting autoDisable;
    private final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
    private final TimerUtil timer = new TimerUtil();
    private PointRotationController rotationController;
    private BlockPos[] targetHoles;
    private int currentIndex;
    private int obsidianSlot = -1;
    private int retries;
    private boolean waiting;

    public Surround() {
        super("Surround", 651101232, Category.COMBAT, "Places obsidian in the 4 blocks around you");
        this.aimSpeed = SliderSetting.create(this, "Aim speed", "#.#", "", 1.0, 12.0, 20.0, 0.1);
        this.actionDelay = SliderSetting.create(this, "Action delay", "#", "ms", 0.0, 80.0, 500.0, 5.0);
        this.silentAim = ToggleSetting.create(this, "Silent aim", true, "Uses the Silent Aim system");
        this.autoDisable = ToggleSetting.create(this, "Auto disable", true, "Disables once all 4 blocks are placed");
        this.rotationClaim.setPriority(this, 8);
        this.addValue(this.aimSpeed, this.actionDelay, this.silentAim, this.autoDisable);
    }

    @Override
    public String getId() {
        return "surround";
    }

    @Override
    public void onEnable() {
        this.currentIndex = 0;
        this.obsidianSlot = -1;
        this.retries = 0;
        this.waiting = false;
        this.targetHoles = null;
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            this.setEnabled(false, true);
            return;
        }
        InventoryPlayer inventory = player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
        this.obsidianSlot = this.findHotbarSlot(inventory, "obsidian");
        if (this.obsidianSlot == -1) {
            Umbra.INSTANCE.getNotificationManager().show("Surround", "Obsidian not in hotbar", NotificationType.WARNING, 3000L);
            this.setEnabled(false, true);
        }
    }

    private int findHotbarSlot(InventoryPlayer inventory, String fragment) {
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack itemStack = inventory.c(slot);
            if (itemStack.isNull() || itemStack.getItem().isNull()) {
                continue;
            }
            String itemName = itemStack.getItem().A();
            if (itemName == null || !itemName.toLowerCase().contains(fragment)) {
                continue;
            }
            return slot;
        }
        return -1;
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        World world = player.getWorld();
        if (player.isNull() || world.isNull()) {
            this.disableQuietly();
            return;
        }
        if (!this.rotationClaim.isOwnedBy(this) && !this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue())) {
            return;
        }
        if (this.targetHoles == null) {
            this.targetHoles = this.computeTargetHoles(player);
            this.currentIndex = 0;
        }
        if (this.currentIndex >= this.targetHoles.length) {
            this.cleanup();
            if (this.autoDisable.getEffectiveValue().booleanValue()) {
                this.setEnabled(false, true);
            }
            return;
        }
        BlockPos hole = this.targetHoles[this.currentIndex];
        Block holeBlock = world.getBlockByPos(hole.getX(), hole.getY(), hole.getZ());
        if (!BlockUtil.u(holeBlock)) {
            ++this.currentIndex;
            this.retries = 0;
            return;
        }
        EnumFacing supportFacing = this.findSupportFacing(world, hole);
        if (supportFacing == null) {
            if (++this.retries > 10) {
                ++this.currentIndex;
                this.retries = 0;
            }
            return;
        }
        BlockPos supportPos = hole.offset(supportFacing);
        Vec3 aimPoint = this.faceCenter(supportPos, supportFacing.getOpposite());
        this.aimAt(aimPoint);
        if (this.rotationController != null && !this.rotationController.isComplete()) {
            return;
        }
        if (!this.timer.hasTimeElapsed(((Double) this.actionDelay.getValue()).longValue())) {
            return;
        }
        this.timer.reset();
        InventoryPlayer inventory = player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
        if (this.obsidianSlot == -1) {
            this.obsidianSlot = this.findHotbarSlot(inventory, "obsidian");
        }
        if (this.obsidianSlot == -1) {
            this.disableQuietly();
            return;
        }
        inventory.g(this.obsidianSlot);
        this.rightClick();
        ++this.currentIndex;
        this.retries = 0;
    }

    private BlockPos[] computeTargetHoles(EntityPlayerSP player) {
        int playerX = (int) Math.floor(player.z());
        int playerY = (int) Math.floor(player.N());
        int playerZ = (int) Math.floor(player.h());
        return new BlockPos[]{
                BlockPos.create(playerX + 1, playerY, playerZ),
                BlockPos.create(playerX - 1, playerY, playerZ),
                BlockPos.create(playerX, playerY, playerZ + 1),
                BlockPos.create(playerX, playerY, playerZ - 1)};
    }

    private EnumFacing findSupportFacing(World world, BlockPos hole) {
        EnumFacing[] facings = new EnumFacing[]{EnumFacing.B(), EnumFacing.F$src$Lgg_umbra_wrapper_impl_EnumFacing_$glfxl5(), EnumFacing.X(), EnumFacing.g$src$Lgg_umbra_wrapper_impl_EnumFacing_$1ii8mzu(), EnumFacing.w(), EnumFacing.M()};
        for (EnumFacing facing : facings) {
            BlockPos adjacent = hole.offset(facing);
            Block block = world.getBlockByPos(adjacent.getX(), adjacent.getY(), adjacent.getZ());
            if (block.isNull() || !BlockUtil.b(block) || BlockUtil.u(block)) {
                continue;
            }
            return facing;
        }
        return null;
    }

    private Vec3 faceCenter(BlockPos blockPos, EnumFacing facing) {
        Vec3i direction = facing.getDirectionVector();
        return Vec3.create((double) blockPos.getX() + 0.5 + (double) direction.getX() * 0.5, (double) blockPos.getY() + 0.5 + (double) direction.getY() * 0.5, (double) blockPos.getZ() + 0.5 + (double) direction.getZ() * 0.5);
    }

    private void aimAt(Vec3 target) {
        if (this.rotationController == null) {
            PointRotationController controller = new PointRotationController(target);
            controller.setRetainAfterCompletion(true);
            controller.setClampStepToRemaining(true);
            controller.setTolerance(0.1f);
            controller.setSpeed(((Double) this.aimSpeed.getValue()).floatValue());
            controller.setAngleBasedAcceleration(true);
            controller.setScaleAxesProportionally(true);
            controller.setLinearAcceleration(true);
            controller.setCubicAcceleration(true);
            this.rotationController = controller;
        } else {
            this.rotationController.setTarget(target);
        }
        if (this.rotationClaim.isOwnedBy(this)) {
            RotationManager.INSTANCE.setController(this.rotationController);
        }
    }

    private void rightClick() {
        KeyBinding keyBinding = Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
        KeyBinding.setKeyBindState(keyBinding, true);
        KeyBinding.onTick(keyBinding);
        KeyBinding.setKeyBindState(keyBinding, false);
    }

    private void cleanup() {
        if (this.rotationController != null) {
            RotationManager.INSTANCE.releaseController(this.rotationController);
            this.rotationController = null;
        }
        this.rotationClaim.release(this);
        this.targetHoles = null;
    }

    private void disableQuietly() {
        this.cleanup();
        if (this.isEnabled()) {
            this.setEnabled(false, true);
        }
    }

    @Override
    public void onDisable() {
        this.cleanup();
    }
}
