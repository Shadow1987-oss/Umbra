package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class OpenedChestTypeSentinel
extends Wrapper {
    public OpenedChestTypeSentinel(Object wrappedObject) {
        super(wrappedObject);
    }

    public static OpenedChestTypeSentinel basic() {
        return new OpenedChestTypeSentinel(OpenedChestTypeSentinel.umbraInstance.getMappingsMapperCompat().CD.getBasic());
    }
}
