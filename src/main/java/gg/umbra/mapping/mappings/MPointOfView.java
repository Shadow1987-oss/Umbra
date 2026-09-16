package gg.umbra.mapping.mappings;

import gg.umbra.asm.helper.DescUtils;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MPointOfView
extends Mapping {
    private static final String VALUES_METHOD_NAME = "values";
    private final MappingMethod valuesMethod;

    public Object[] values() {
        return this.valuesMethod.invokeObjectArray(null, new Object[0]);
    }

    public MPointOfView() {
        super(MappedClasses.ZR);
        this.valuesMethod = this.registerStaticMethod(VALUES_METHOD_NAME, true, DescUtils.getArrayType(MappedClasses.ZR), new Class[]{});
    }
}

