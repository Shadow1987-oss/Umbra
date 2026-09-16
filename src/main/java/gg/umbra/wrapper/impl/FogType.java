package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class FogType
extends Wrapper {
    public static FogType noneOrSky() {
        return new FogType(FogType.umbraInstance.getMappingsMapperCompat().fogType.getNoneOrSky());
    }

    public FogType(Object wrappedObject) {
        super(wrappedObject);
    }

    public static FogType terrainOrWorld() {
        return new FogType(FogType.umbraInstance.getMappingsMapperCompat().fogType.getTerrainOrWorld());
    }
}
