package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MSoundEventRegistryName
extends Mapping {
    private static final String NAME_FIELD_NAME = "name";
    private final MappingField nameField;

    public static Object getName(MSoundEventRegistryName mapping, Object soundEvent) {
        return mapping.readName(soundEvent);
    }

    private Object readName(Object soundEvent) {
        return this.nameField.getObject(soundEvent);
    }

    public MSoundEventRegistryName() {
        super(MappedClasses.V4);
        this.nameField = this.J(NAME_FIELD_NAME, true, MappedClasses.zC);
    }
}

