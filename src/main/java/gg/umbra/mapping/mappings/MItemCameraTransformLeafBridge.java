package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MItemCameraTransformLeafBridge
extends Mapping {
    private static final String KEY_FIELD_NAME = "key";
    private final MappingField keyField;

    public Object getKey(Object transform) {
        return this.keyField.getObject(transform);
    }

    public MItemCameraTransformLeafBridge() {
        super(MappedClasses.zo);
        this.keyField = this.J(KEY_FIELD_NAME, true, MappedClasses.qC);
    }
}

