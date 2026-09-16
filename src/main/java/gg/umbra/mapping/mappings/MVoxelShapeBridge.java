package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MVoxelShapeBridge
extends Mapping {
    private final MappingMethod bufferSourceMethod;

    public MVoxelShapeBridge() {
        super(MappedClasses.ZL);
        this.bufferSourceMethod = this.Y("bufferSource", true, MappedClasses.lp, new Class[]{});
    }

    public Object getBufferSource(Object renderer) {
        return this.bufferSourceMethod.invokeObject(renderer, new Object[0]);
    }
}

