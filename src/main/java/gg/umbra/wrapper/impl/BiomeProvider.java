package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MBiomeProviderBridge;
import gg.umbra.wrapper.Wrapper;

public class BiomeProvider
extends Wrapper {
    public Iterable getAllEffects() {
        return MBiomeProviderBridge.getAllEffects(BiomeProvider.umbraInstance.getMappingsMapperCompat().K, this.I);
    }

    public BiomeProvider(Object wrappedObject) {
        super(wrappedObject);
    }
}
