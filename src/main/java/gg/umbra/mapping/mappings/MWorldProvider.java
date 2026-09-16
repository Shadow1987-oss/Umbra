package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MWorldProvider
extends Mapping {
    private static final String GET_HAS_NO_SKY_METHOD_NAME = "getHasNoSky";
    private final MappingMethod getHasNoSkyMethod;

    public MWorldProvider() {
        super(MappedClasses.WORLD_PROVIDER);
        this.getHasNoSkyMethod = this.Y(GET_HAS_NO_SKY_METHOD_NAME, true, Boolean.TYPE, new Class[]{});
    }

    public boolean hasNoSky(Object worldProvider) {
        return this.getHasNoSkyMethod.invokeBoolean(worldProvider, new Object[0]);
    }
}
