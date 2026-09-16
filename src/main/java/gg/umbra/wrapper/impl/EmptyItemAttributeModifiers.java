package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEmptyItemAttributeModifiers;

public class EmptyItemAttributeModifiers
extends ItemAttributeModifiers {
    public static EmptyItemAttributeModifiers create() {
        return new EmptyItemAttributeModifiers(MEmptyItemAttributeModifiers.create(EmptyItemAttributeModifiers.umbraInstance.getMappingsMapperCompat().Rw));
    }

    public EmptyItemAttributeModifiers(Object wrappedObject) {
        super(wrappedObject);
    }
}
