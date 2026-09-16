package gg.umbra.mapping;

import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMemberBuilder;

public class MappingFieldBuilder
extends MappingMemberBuilder<MappingFieldBuilder, MappingField> {
    private int arrayDimensions = 0;

    public MappingField buildField() {
        return MappingField.fromBuilder(this);
    }

    @Override
    public MappingField build() {
        return this.buildField();
    }

    public int getArrayDimensions() {
        return this.arrayDimensions;
    }

    public MappingFieldBuilder setArrayDimensions(int arrayDimensions) {
        this.arrayDimensions = arrayDimensions;
        return this;
    }
}
