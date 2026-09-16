package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ChestTypeHolder
extends Wrapper {
    public static ItemCameraTransformSubtypeValue woodenToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.umbraInstance.getMappingsMapperCompat().L.getWoodenToolMaterials());
    }

    public static ItemCameraTransformSubtypeValue goldToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.umbraInstance.getMappingsMapperCompat().L.getGoldToolMaterials());
    }

    public ChestTypeHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}
