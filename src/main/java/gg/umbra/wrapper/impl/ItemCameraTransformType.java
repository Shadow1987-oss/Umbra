package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ItemCameraTransformType
extends Wrapper {
    public ItemCameraTransformBase getItems() {
        return new ItemCameraTransformBase(ItemCameraTransformType.umbraInstance.getMappingsMapperCompat().repairable.getItems(this.I));
    }

    public ItemCameraTransformType(Object handle) {
        super(handle);
    }
}
