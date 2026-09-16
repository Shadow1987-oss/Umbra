package gg.umbra.tools.clutch;

import gg.umbra.utils.datas.BlockData;
import gg.umbra.wrapper.impl.EnumFacing;
import gg.umbra.wrapper.impl.Vec3;

public class PlacementTarget {
    private BlockData cachedBlockData;
    public int depth;
    public Vec3 hitPoint;
    public final BlockData supportBlock;
    public final EnumFacing facing;
    public final boolean offsetFromSupport;

    public PlacementTarget(BlockData blockData, EnumFacing enumFacing) {
        this(blockData, enumFacing, true);
    }

    public EnumFacing getFacing() {
        return this.facing;
    }

    public BlockData getPlacedBlock() {
        if (this.cachedBlockData == null) {
            this.cachedBlockData = this.offsetFromSupport && this.facing != null ? this.supportBlock.R(this.facing) : this.supportBlock;
        }
        return this.cachedBlockData;
    }

    public PlacementTarget(BlockData supportBlock, EnumFacing facing, boolean offsetFromSupport) {
        this.supportBlock = supportBlock;
        this.facing = facing;
        this.offsetFromSupport = offsetFromSupport;
    }

    public BlockData getBlockData() {
        return this.supportBlock;
    }
}

