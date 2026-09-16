package gg.umbra.worldmods;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.BlockPos;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumFacing;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import gg.umbra.wrapper.impl.TileEntity;
import gg.umbra.wrapper.impl.TileEntityOpenedChest;
import gg.umbra.wrapper.impl.Vec3;
import gg.umbra.wrapper.impl.WorldClient;

public class AutoOpen
extends HackModule {
    private final SliderSetting range;
    private final SliderSetting delay;
    private final ToggleSetting openEnderChests;
    private final TimerUtil scanTimer;
    private final TimerUtil actionTimer;
    private BlockPos targetChest;
    private long lastOpenedWorldTime;

    public AutoOpen() {
        super("AutoOpen", 0, Category.WORLD, "Opens nearby chests for you (pair with Loot Stealer to loot them).");
        this.range = SliderSetting.create(this, "Range", "#", "", 1.0, 4.0, 8.0, 1.0);
        this.delay = SliderSetting.create(this, "Delay", "#", "ms", 50.0, 400.0, 2000.0, 10.0);
        this.openEnderChests = ToggleSetting.create(this, "Open ender chests", false);
        this.scanTimer = new TimerUtil();
        this.actionTimer = new TimerUtil();
        this.addValue(this.range, this.delay, this.openEnderChests);
    }

    @Override
    public String getId() {
        return "autoopen";
    }

    private BlockPos findChest(EntityPlayerSP player, WorldClient world) {
        double range = (Double) this.range.getValue();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (Object handle : world.R$src$Ljava_util_List_$1ycbpra()) {
            boolean isChest = MappedClasses.DZ.isInstance(handle);
            boolean isEnderChest = this.openEnderChests.getEffectiveValue().booleanValue() && MappedClasses.u0.isInstance(handle);
            if (!isChest && !isEnderChest) {
                continue;
            }
            TileEntity tileEntity = new TileEntity(handle);
            double dist = player.i((double) tileEntity.getX() + 0.5, (double) tileEntity.getY() + 0.5, (double) tileEntity.getZ() + 0.5);
            if (dist > range || dist >= bestDistance) {
                continue;
            }
            bestDistance = dist;
            best = BlockPos.create(tileEntity.getX(), tileEntity.getY(), tileEntity.getZ());
        }
        return best;
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (player.isNull() || world.isNull()) {
            this.releaseControl();
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            this.releaseControl();
            return;
        }
        if (!this.actionTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.actionTimer.reset();
        if (this.targetChest == null) {
            this.targetChest = this.findChest(player, world);
            if (this.targetChest == null) {
                return;
            }
            this.scanTimer.reset();
            return;
        }
        TileEntity chest = this.findChestAt(world, this.targetChest);
        if (chest == null) {
            this.targetChest = null;
            this.releaseControl();
            return;
        }
        double dist = player.i((double) chest.getX() + 0.5, (double) chest.getY() + 0.5, (double) chest.getZ() + 0.5);
        if (dist > (Double) this.range.getValue()) {
            this.targetChest = null;
            this.releaseControl();
            return;
        }
        Vec3 hitVec = Vec3.create((double) chest.getX() + 0.5, (double) chest.getY() + 0.5, (double) chest.getZ() + 0.5);
        EnumFacing facing = this.getFacingTowardPlayer(this.targetChest, player);
        RayTraceResult rayTraceResult = RayTraceResult.create(RayTraceResult_type.block(), hitVec, facing, this.targetChest);
        Minecraft.O(rayTraceResult);
        SharedModuleControlClaims.mouseOverUpdate.setClaimed(true);
        KeyBinding useKey = Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
        KeyBindingHelper.setPressedAndTick(useKey, true);
        KeyBindingHelper.updateKeyBinding(useKey, false, false);
        this.targetChest = null;
    }

    private TileEntity findChestAt(WorldClient world, BlockPos blockPos) {
        for (Object handle : world.R$src$Ljava_util_List_$1ycbpra()) {
            if (!MappedClasses.DZ.isInstance(handle)) {
                continue;
            }
            TileEntityOpenedChest chest = new TileEntityOpenedChest(handle);
            if (chest.getX() == blockPos.getX() && chest.getY() == blockPos.getY() && chest.getZ() == blockPos.getZ()) {
                return chest;
            }
        }
        return null;
    }

    private EnumFacing getFacingTowardPlayer(BlockPos blockPos, EntityPlayerSP player) {
        double dx = player.z() - ((double) blockPos.getX() + 0.5);
        double dy = player.N() - ((double) blockPos.getY() + 0.5);
        double dz = player.h() - ((double) blockPos.getZ() + 0.5);
        if (Math.abs(dx) >= Math.abs(dz)) {
            return dx > 0.0 ? EnumFacing.T(4) : EnumFacing.T(5);
        }
        return dz > 0.0 ? EnumFacing.T(2) : EnumFacing.T(3);
    }

    private void releaseControl() {
        SharedModuleControlClaims.mouseOverUpdate.clearClaimed();
    }

    @Override
    public void onDisable() {
        this.targetChest = null;
        this.releaseControl();
    }
}
