package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRayTraceContext_BlockMode;
import gg.umbra.wrapper.Wrapper;

public class RayTraceContext$FluidMode
extends Wrapper {
    public static RayTraceContext$FluidMode visual() {
        return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getVisual(RayTraceContext$FluidMode.umbraInstance.getMappingsMapperCompat().rayTraceBlockMode));
    }

    public RayTraceContext$FluidMode(Object handle) {
        super(handle);
    }

    public static RayTraceContext$FluidMode outline() {
        return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getOutline(RayTraceContext$FluidMode.umbraInstance.getMappingsMapperCompat().rayTraceBlockMode));
    }

    public static RayTraceContext$FluidMode collider() {
        return new RayTraceContext$FluidMode(MRayTraceContext_BlockMode.getCollider(RayTraceContext$FluidMode.umbraInstance.getMappingsMapperCompat().rayTraceBlockMode));
    }
}
