package gg.umbra.worldmods;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.input.AttackKeyController;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.utils.BlockUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.BlockPos;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumFacing;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import gg.umbra.wrapper.impl.Vec3;
import gg.umbra.wrapper.impl.WorldClient;

public class BreakAll
extends HackModule {
    private final SliderSetting range;
    private final SliderSetting delay;
    private final SliderSetting scanDelay;
    private final ToggleSetting flatten;
    private final ToggleSetting ignoreLiquids;
    private final TimerUtil actionTimer;
    private final TimerUtil scanTimer = new TimerUtil();
    private BlockPos targetBlock;

    public BreakAll() {
        super("BreakAll", 0, Category.WORLD, "Mines every block around you.");
        this.range = SliderSetting.create(this, "Range", "#", "", 1.0, 4.0, 8.0, 1.0);
        this.delay = SliderSetting.create(this, "Delay", "#", "ms", 0.0, 100.0, 1000.0, 10.0);
        this.scanDelay = SliderSetting.createWithDescription(this, "Scan delay", "#", "ms", 25.0, 150.0, 1000.0, "How often the surrounding blocks are re-scanned once the current target is broken.");
        this.flatten = ToggleSetting.create(this, "Flatten", false, "Only breaks blocks at your feet level (and below)");
        this.ignoreLiquids = ToggleSetting.create(this, "Ignore liquids", true);
        this.actionTimer = new TimerUtil();
        this.addValue(this.range, this.delay, this.scanDelay, this.flatten, this.ignoreLiquids);
    }

    @Override
    public String getId() {
        return "breakall";
    }

    private boolean isBreakable(WorldClient world, int x, int y, int z, EntityPlayerSP player) {
        if (y < 0) {
            return false;
        }
        Block block = world.getBlockByPos(x, y, z);
        if (block.isNull() || BlockUtil.p(block)) {
            return false;
        }
        if (this.ignoreLiquids.getEffectiveValue().booleanValue() && BlockUtil.C(block)) {
            return false;
        }
        String blockName = block.U();
        if (blockName != null && blockName.contains("bedrock")) {
            return false;
        }
        if (this.flatten.getEffectiveValue().booleanValue() && y > (int) Math.floor(player.N())) {
            return false;
        }
        double dx = (double) x + 0.5 - player.z();
        double dy = (double) y + 0.5 - player.N();
        double dz = (double) z + 0.5 - player.h();
        return Math.sqrt(dx * dx + dy * dy + dz * dz) <= (Double) this.range.getValue();
    }

    private BlockPos findClosestBlock(EntityPlayerSP player, WorldClient world) {
        int radius = ((Double) this.range.getValue()).intValue();
        int playerX = (int) Math.floor(player.z());
        int playerY = (int) Math.floor(player.N());
        int playerZ = (int) Math.floor(player.h());
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -1; dy <= radius; ++dy) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    if (dy == -1 && (dx != 0 || dz != 0)) {
                        continue;
                    }
                    if (dy == 0 && dx == 0 && dz == 0) {
                        continue;
                    }
                    int x = playerX + dx;
                    int y = playerY + dy;
                    int z = playerZ + dz;
                    if (!this.isBreakable(world, x, y, z, player)) {
                        continue;
                    }
                    double dist = (double) (dx * dx + dy * dy + dz * dz);
                    if (dist >= bestDistance) {
                        continue;
                    }
                    bestDistance = dist;
                    best = BlockPos.create(x, y, z);
                }
            }
        }
        return best;
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (player.isNull() || world.isNull() || Minecraft.currentScreen().isNotNull()) {
            this.releaseControl();
            return;
        }
        // Keep breaking the current target while it still exists; only re-scan when it is gone.
        if (this.targetBlock != null && !this.isBreakable(world, this.targetBlock.getX(), this.targetBlock.getY(), this.targetBlock.getZ(), player)) {
            this.releaseControl();
        }
        if (this.targetBlock == null) {
            if (!this.scanTimer.hasTimeElapsed(((Double) this.scanDelay.getValue()).longValue())) {
                return;
            }
            this.scanTimer.reset();
            BlockPos nextTarget = this.findClosestBlock(player, world);
            if (nextTarget == null) {
                this.releaseControl();
                return;
            }
            this.actionTimer.reset();
            this.targetBlock = nextTarget;
            return;
        }
        if (!this.actionTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.actionTimer.reset();
        BlockPos blockPos = this.targetBlock;
        Vec3 hitVec = Vec3.create((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.5, (double) blockPos.getZ() + 0.5);
        EnumFacing facing = this.getFacingTowardPlayer(blockPos, player);
        RayTraceResult rayTraceResult = RayTraceResult.create(RayTraceResult_type.block(), hitVec, facing, blockPos);
        Minecraft.O(rayTraceResult);
        SharedModuleControlClaims.mouseOverUpdate.setClaimed(true);
        AttackKeyController.requestSyntheticAttack(this);
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
        if (this.targetBlock != null) {
            this.targetBlock = null;
            SharedModuleControlClaims.mouseOverUpdate.clearClaimed();
            AttackKeyController.releaseAttackKey();
        }
    }

    @Override
    public void onDisable() {
        this.releaseControl();
    }
}
