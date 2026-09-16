package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MEnumWorldBlockLayer
extends Mapping {
    public MappingField solidField;
    public MappingField translucentField;

    public MEnumWorldBlockLayer() {
        super(MappedClasses.E);
        Class translucentFieldType = MappedClasses.E;
        boolean translucentFieldPublic = true;
        String translucentFieldName = "TRANSLUCENT";
        MEnumWorldBlockLayer mapping = this;
        this.translucentField = mapping.registerStaticField(translucentFieldName, translucentFieldPublic, translucentFieldType);
        Class solidFieldType = MappedClasses.E;
        boolean solidFieldPublic = true;
        String solidFieldName = "SOLID";
        MEnumWorldBlockLayer solidMapping = this;
        this.solidField = solidMapping.registerStaticField(solidFieldName, solidFieldPublic, solidFieldType);
    }

    private Object readSolid() {
        return this.solidField.getObject(null);
    }

    private Object readTranslucent() {
        return this.translucentField.getObject(null);
    }

    public static Object getSolid(MEnumWorldBlockLayer mapping) {
        return mapping.readSolid();
    }

    public static Object getTranslucent(MEnumWorldBlockLayer mapping) {
        return mapping.readTranslucent();
    }
}

