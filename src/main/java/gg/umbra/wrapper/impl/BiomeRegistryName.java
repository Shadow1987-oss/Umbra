package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MBiomeRegistryName;
import gg.umbra.wrapper.Wrapper;

public class BiomeRegistryName
extends Wrapper {
    public String n() {
        return MBiomeRegistryName.getName(BiomeRegistryName.umbraInstance.getMappingsMapperCompat().hq, this.I);
    }

    public BiomeRegistryName(Object object) {
        super(object);
    }
}
