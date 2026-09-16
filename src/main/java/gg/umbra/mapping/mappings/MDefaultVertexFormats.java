package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MDefaultVertexFormats
extends Mapping {
    private static final String POSITION_COLOR_FIELD_NAME = "POSITION_COLOR";
    private final MappingField positionColorField;

    public static Object getPositionColor(MDefaultVertexFormats mapping) {
        return mapping.readPositionColor();
    }

    private Object readPositionColor() {
        return this.positionColorField.getObject(null);
    }

    public MDefaultVertexFormats() {
        super(MappedClasses.Yo);
        this.positionColorField = this.registerStaticField(POSITION_COLOR_FIELD_NAME, true, MappedClasses.zG);
    }
}

