package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.input.AttackKeyController;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.utils.BlockUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.BlockPos;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumFacing;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import gg.umbra.wrapper.impl.Vec3;
import gg.umbra.wrapper.impl.WorldClient;
import java.util.ArrayList;

public class AutoCity
extends HackModule {
    private final SliderSetting range;
    private final SliderSetting delay;
    private final ToggleSetting obsidianOnly;
    private final TimerUtil actionTimer;
    private BlockPos cityBlock;
    private EntityLivingBase lockedTarget;

    public AutoCity() {
        super("AutoCity", 224416193, Category.COMBAT, "Mines the block next to the nearest enemy to city them.");
        this.range = SliderSetting.create(this, "Range", "#", "", 1.0, 4.0, 8.0, 1.0);
        this.delay = SliderSetting.create(this, "Delay", "#", "ms", 0.0, 150.0, 1000.0, 10.0);
        this.obsidianOnly = ToggleSetting.create(this, "Obsidian only", false, "Only breaks obsidian blocks (crystal pvp)");
        this.actionTimer = new TimerUtil();
        this.addValue(this.range, this.delay, this.obsidianOnly);
    }

    @Override
    public String getId() {
        return "autocity";
    }

    private EntityLivingBase findTarget(EntityPlayerSP player, WorldClient world) {
        ArrayList<EntityLivingBase> candidates = new ArrayList<EntityLivingBase>();
        for (Object handle : world.z()) {
            Entity entity = new Entity(handle);
            if (!entity.isInstance(MappedClasses.zm) || entity.equals(player)) {
                continue;
            }
            EntityLivingBase candidate = new EntityLivingBase(handle);
            if (candidate.w$src$F$15l9epb() <= 0.0f || candidate.M$src$Z$ff28xj()) {
                continue;
            }
            if (player.getDistanceToEntity(candidate) > (Double) this.range.getValue()) {
                continue;
            }
            if (Umbra.INSTANCE.getFriendManager().isFriend(candidate)) {
                continue;
            }
            if (!Umbra.INSTANCE.getClientSettings().isValidTarget(candidate, false)) {
                continue;
            }
            candidates.add(candidate);
        }
        EntityLivingBase best = null;
        float bestDistance = Float.MAX_VALUE;
        for (EntityLivingBase candidate : candidates) {
            float distance = player.getDistanceToEntity(candidate);
            if (distance >= bestDistance) {
                continue;
            }
            bestDistance = distance;
            best = candidate;
        }
        return best;
    }

    private BlockPos findCityBlock(EntityLivingBase target, EntityPlayerSP player) {
        int targetX = (int) Math.floor(target.z());
        int targetY = (int) Math.floor(target.N());
        int targetZ = (int) Math.floor(target.h());
        WorldClient world = Minecraft.theWorld();
        int[][] offsets = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}};
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (int[] offset : offsets) {
            int x = targetX + offset[0];
            int y = targetY + offset[1];
            int z = targetZ + offset[2];
            Block block = world.getBlockByPos(x, y, z);
            if (block.isNull() || BlockUtil.p(block) || BlockUtil.u(block)) {
                continue;
            }
            if (this.obsidianOnly.getEffectiveValue().booleanValue()) {
                String blockName = block.U();
                if (blockName == null || !blockName.contains("obsidian")) {
                    continue;
                }
            }
            double dist = player.i((double) x + 0.5, (double) y + 0.5, (double) z + 0.5);
            if (dist >= bestDistance || dist > (Double) this.range.getValue()) {
                continue;
            }
            bestDistance = dist;
            best = BlockPos.create(x, y, z);
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
        EntityLivingBase target = this.findTarget(player, world);
        if (target == null) {
            this.lockedTarget = null;
            this.releaseControl();
            return;
        }
        if (this.lockedTarget == null || !this.lockedTarget.equals(target)) {
            this.lockedTarget = target;
            this.cityBlock = null;
            return;
        }
        BlockPos blockPos = this.findCityBlock(target, player);
        if (blockPos == null) {
            this.cityBlock = null;
            this.releaseControl();
            return;
        }
        if (!blockPos.equals(this.cityBlock)) {
            this.actionTimer.reset();
            this.cityBlock = blockPos;
            return;
        }
        if (!this.actionTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        this.actionTimer.reset();
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
        if (this.cityBlock != null) {
            this.cityBlock = null;
            SharedModuleControlClaims.mouseOverUpdate.clearClaimed();
            AttackKeyController.releaseAttackKey();
        }
    }

    @Override
    public void onDisable() {
        this.lockedTarget = null;
        this.releaseControl();
    }
}
