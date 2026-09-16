package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MSoundEventRegistryName;
import gg.umbra.wrapper.Wrapper;

public class SoundEventRegistryName
extends Wrapper {
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(MSoundEventRegistryName.getName(SoundEventRegistryName.umbraInstance.getMappingsMapperCompat().h9, this.I));
    }

    public SoundEventRegistryName(Object wrappedObject) {
        super(wrappedObject);
    }
}
