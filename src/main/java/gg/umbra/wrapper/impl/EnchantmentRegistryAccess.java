package gg.umbra.wrapper.impl;

public class EnchantmentRegistryAccess
extends RegistryLookup {
    public EnchantmentRegistryAccess(Object wrappedObject) {
        super(wrappedObject);
    }

    public EnchantmentRegistry lookupOrThrow(ResourceKey resourceKey) {
        return new EnchantmentRegistry(EnchantmentRegistryAccess.umbraInstance.getMappings().CT.lookupOrThrow(this.getObject(), resourceKey.getObject()));
    }
}
