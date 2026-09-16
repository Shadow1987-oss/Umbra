package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ResourceLocation
extends Wrapper {
    public static ResourceLocation create(String location) {
        return new ResourceLocation(ResourceLocation.umbraInstance.getMappingsMapperCompat().resourceLocation.create(location));
    }

    public String getResourcePath() {
        return ResourceLocation.umbraInstance.getMappingsMapperCompat().resourceLocation.getPath(this.I);
    }

    public ResourceLocation(Object resourceLocationHandle) {
        super(resourceLocationHandle);
    }
}
