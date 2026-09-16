package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EnchantmentHelperBridge
extends Wrapper {
    public EnchantmentHelperBridge(Object handle) {
        super(handle);
    }

    public static EnchantmentRegistryAccess createLookup() {
        return new EnchantmentRegistryAccess(EnchantmentHelperBridge.umbraInstance.getMappingsMapperCompat().vanillaRegistries.createLookup());
    }
}
