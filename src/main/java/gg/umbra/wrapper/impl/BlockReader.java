package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class BlockReader
extends Wrapper {
    public BlockReader(Object object) {
        super(object);
    }

    public BlockStatePredicate f(BlockPos blockPos) {
        return new BlockStatePredicate(BlockReader.umbraInstance.getMappingsMapperCompat().y.getBlockState(this.I, blockPos.getObject()));
    }
}
