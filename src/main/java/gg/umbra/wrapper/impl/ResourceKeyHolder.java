package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ResourceKeyHolder
extends Wrapper {
    public static ResourceKey gold() {
        return new ResourceKey(ResourceKeyHolder.umbraInstance.getMappingsMapperCompat().qV.getGold());
    }

    public ResourceKeyHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}
