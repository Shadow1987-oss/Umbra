package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MBlockHorizontal;

public class BlockHorizontal
extends Block {
    public BlockHorizontal(Object wrappedObject) {
        super(wrappedObject);
    }

    public static BlockProperty facing() {
        return new BlockProperty(MBlockHorizontal.getFacing(BlockHorizontal.umbraInstance.getMappingsMapperCompat().a));
    }
}
