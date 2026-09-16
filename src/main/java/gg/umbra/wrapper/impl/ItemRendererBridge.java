package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ItemRendererBridge
extends Wrapper {
    public static ItemRendererBridge firstPerson() {
        return new ItemRendererBridge(ItemRendererBridge.umbraInstance.getMappingsMapperCompat().legacyItemCameraTransformType.getFirstPerson());
    }

    public ItemRendererBridge(Object handle) {
        super(handle);
    }
}
