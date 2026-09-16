package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class RegistryAccess
extends Wrapper {
    public RegistryAccess(Object wrappedObject) {
        super(wrappedObject);
    }

    public Registry lookupOrThrow(ResourceKey resourceKey) {
        return new Registry(RegistryAccess.umbraInstance.getMappingsMapperCompat().qs.lookupOrThrow(this.I, resourceKey.getObject()));
    }
}
