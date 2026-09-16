package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MResourceKey;
import gg.umbra.wrapper.Wrapper;

public class ResourceKey
extends Wrapper {
    public ResourceKey(Object wrappedObject) {
        super(wrappedObject);
    }

    public ResourceLocation getLocation() {
        return new ResourceLocation(MResourceKey.getLocation(ResourceKey.umbraInstance.getMappingsMapperCompat().P, this.I));
    }
}
