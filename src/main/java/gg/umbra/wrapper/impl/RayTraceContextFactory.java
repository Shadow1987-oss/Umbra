package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class RayTraceContextFactory
extends Wrapper {
    public static RayTraceContextFactory create(Vec3 from, Vec3 to, ChestType blockMode, ITooltipFlag fluidMode, Entity entity) {
        return new RayTraceContextFactory(RayTraceContextFactory.umbraInstance.getMappingsMapperCompat().DV.createClipContext(from.getObject(), to.getObject(), blockMode.getObject(), fluidMode.getObject(), entity.getObject()));
    }

    public RayTraceContextFactory(Object handle) {
        super(handle);
    }
}
