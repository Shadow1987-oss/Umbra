package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRayTraceContext_FluidMode;
import gg.umbra.wrapper.Wrapper;

public class RayTraceContext$BlockMode
extends Wrapper {
    public RayTraceContext$BlockMode(Object handle) {
        super(handle);
    }

    public static RayTraceContext$BlockMode any() {
        return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getAny(RayTraceContext$BlockMode.umbraInstance.getMappingsMapperCompat().rayTraceFluidMode));
    }

    public static RayTraceContext$BlockMode sourceOnly() {
        return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getSourceOnly(RayTraceContext$BlockMode.umbraInstance.getMappingsMapperCompat().rayTraceFluidMode));
    }

    public static RayTraceContext$BlockMode none() {
        return new RayTraceContext$BlockMode(MRayTraceContext_FluidMode.getNone(RayTraceContext$BlockMode.umbraInstance.getMappingsMapperCompat().rayTraceFluidMode));
    }
}
