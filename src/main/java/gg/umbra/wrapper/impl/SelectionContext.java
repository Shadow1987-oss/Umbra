package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SelectionContext
extends Wrapper {
    public SelectionContext(Object handle) {
        super(handle);
    }

    public static SelectionContext forEntity(Entity entity) {
        return new SelectionContext(SelectionContext.umbraInstance.getMappingsMapperCompat().selectionContext.forEntity(entity.getObject()));
    }
}
