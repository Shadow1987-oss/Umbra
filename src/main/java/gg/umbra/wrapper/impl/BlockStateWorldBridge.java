package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class BlockStateWorldBridge
extends Wrapper {
    public boolean isTag(Object tag) {
        return BlockStateWorldBridge.umbraInstance.getMappingsMapperCompat().hu.isTag(this.getObject(), tag);
    }

    public float getHeight(World world, BlockPos blockPosition) {
        return BlockStateWorldBridge.umbraInstance.getMappingsMapperCompat().hu.getHeight(this.getObject(), world.getObject(), blockPosition.getObject());
    }

    public BlockStateWorldBridge(Object handle) {
        super(handle);
    }

    public AbstractBlockState getType() {
        return new AbstractBlockState(BlockStateWorldBridge.umbraInstance.getMappingsMapperCompat().hu.getType(this.I));
    }

    public boolean isEmpty() {
        return BlockStateWorldBridge.umbraInstance.getMappingsMapperCompat().hu.isEmpty(this.I);
    }
}
