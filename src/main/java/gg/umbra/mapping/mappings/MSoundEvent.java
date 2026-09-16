package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MSoundEvent
extends Mapping {
    private static final String NAME_FIELD_NAME = "name";
    private final MappingField nameField;

    public MSoundEvent() {
        super(MappedClasses.Y6);
        this.nameField = this.J(NAME_FIELD_NAME, true, MappedClasses.zC);
    }

    public Object getName(Object soundEvent) {
        return this.nameField.getObject(soundEvent);
    }
}

