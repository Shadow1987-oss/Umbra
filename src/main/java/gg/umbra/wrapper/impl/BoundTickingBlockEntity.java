package gg.umbra.wrapper.impl;

public class BoundTickingBlockEntity
extends TickingBlockEntity {
    public BoundTickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBlockEntity() {
        return BoundTickingBlockEntity.umbraInstance.getMappingsMapperCompat().boundTickingBlockEntity.getBlockEntity(this.I);
    }
}
