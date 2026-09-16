package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ResourceLocationKey
extends Wrapper {
    public static ResourceLocationKey L() {
        return new ResourceLocationKey(ResourceLocationKey.umbraInstance.getMappingsMapperCompat().qK.r());
    }

    public ResourceLocationKey(Object object) {
        super(object);
    }
}

