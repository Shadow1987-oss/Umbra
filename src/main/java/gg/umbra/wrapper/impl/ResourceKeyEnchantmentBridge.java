package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MResourceKeyEnchantmentBridge;
import gg.umbra.wrapper.Wrapper;

public class ResourceKeyEnchantmentBridge
extends Wrapper {
    public static ResourceKey enchantment() {
        return new ResourceKey(MResourceKeyEnchantmentBridge.getEnchantment(ResourceKeyEnchantmentBridge.umbraInstance.getMappingsMapperCompat().enchantmentRegistryKey));
    }

    public ResourceKeyEnchantmentBridge(Object handle) {
        super(handle);
    }
}
