package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SharedMonsterAttributesVariantBridge
extends Wrapper {
    public SharedMonsterAttributesVariantBridge(Object object) {
        super(object);
    }

    public static SharedMonsterAttributesVariantBridge C() {
        return new SharedMonsterAttributesVariantBridge(SharedMonsterAttributesVariantBridge.umbraInstance.getMappingsMapperCompat().D7.getAll());
    }

    public static SharedMonsterAttributesVariantBridge l() {
        return new SharedMonsterAttributesVariantBridge(SharedMonsterAttributesVariantBridge.umbraInstance.getMappingsMapperCompat().D7.getNone());
    }
}
