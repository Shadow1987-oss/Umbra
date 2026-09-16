package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MOpenedChestTypeSentinelBridge
extends Mapping {
    private static final String BASIC_FIELD_NAME = "BASIC";
    private final MappingField basicField;

    public MOpenedChestTypeSentinelBridge() {
        super(MappedClasses.q1);
        this.basicField = this.registerStaticField(BASIC_FIELD_NAME, true, MappedClasses.q1);
    }

    public Object getBasic() {
        return this.basicField.getObject(null);
    }
}

