package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MInputMappingsInput
extends Mapping {
    private static final String KEY_CODE_FIELD_NAME = "keyCode";
    private final MappingField keyCodeField;

    public MInputMappingsInput() {
        super(MappedClasses.zp);
        this.keyCodeField = this.J(KEY_CODE_FIELD_NAME, true, Integer.TYPE);
    }

    private int readKeyCode(Object input) {
        return this.keyCodeField.getInt(input);
    }

    public static int getKeyCode(MInputMappingsInput mapping, Object input) {
        return mapping.readKeyCode(input);
    }
}

