package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MEntityRayTraceResult
extends Mapping {
    private static final String CONSTRUCTOR = "<init>";
    private final MappingMethod getEntityMethod;
    private final MappingMethod constructor;

    public Object create(Object entityHandle, Object hitPositionHandle) {
        return this.constructor.newInstance(entityHandle, hitPositionHandle);
    }

    public MEntityRayTraceResult() {
        super(MappedClasses.zl);
        this.constructor = this.Y(CONSTRUCTOR, false, Void.TYPE, MappedClasses.zc, MappedClasses.qP);
        this.getEntityMethod = this.Y("getEntity", true, MappedClasses.zc);
    }

    public Object getEntity(Object rayTraceResultHandle) {
        return this.getEntityMethod.invokeObject(rayTraceResultHandle);
    }
}

