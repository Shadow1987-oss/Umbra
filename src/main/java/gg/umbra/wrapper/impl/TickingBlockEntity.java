package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTickingBlockEntity;
import gg.umbra.wrapper.Wrapper;

public class TickingBlockEntity
extends Wrapper {
    public BlockPos getPos() {
        return new BlockPos(MTickingBlockEntity.getPos(TickingBlockEntity.umbraInstance.getMappingsMapperCompat().tickingBlockEntity, this.I));
    }

    public TickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }
}
