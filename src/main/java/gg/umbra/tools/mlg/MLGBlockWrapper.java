package gg.umbra.tools.mlg;

import gg.umbra.mapping.mappings.MMLGBlockWrapper;
import gg.umbra.wrapper.Wrapper;

public class MLGBlockWrapper
extends Wrapper {
    public MLGBlockWrapper(Object handle) {
        super(handle);
    }

    public static Object getWaterBlock() {
        return MMLGBlockWrapper.getWater(MLGBlockWrapper.umbraInstance.getMappingsMapperCompat().Rj);
    }

    public static Object getLavaBlock() {
        return MMLGBlockWrapper.getLava(MLGBlockWrapper.umbraInstance.getMappingsMapperCompat().Rj);
    }
}
