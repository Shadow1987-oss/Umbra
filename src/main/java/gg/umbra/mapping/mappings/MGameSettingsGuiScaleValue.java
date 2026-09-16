package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MGameSettingsGuiScaleValue
extends Mapping {
    private static final String ANTIALIASING_LEVEL_FIELD;
    private static String controlFlowMarker;
    private final MappingField antialiasingLevelField;

    public int getAntialiasingLevel() {
        if (this.antialiasingLevelField.hasResolutionFailed()) {
            return 1;
        }
        return this.antialiasingLevelField.getInt(null);
    }

    static {
        MGameSettingsGuiScaleValue.setControlFlowMarker("x0yBJc");
        ANTIALIASING_LEVEL_FIELD = "antialiasingLevel";
    }


    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public MGameSettingsGuiScaleValue() {
        this(MGameSettingsGuiScaleValue.getControlFlowMarker());
    }

    private MGameSettingsGuiScaleValue(String marker) {
        super(MappedClasses.Ym);
        this.antialiasingLevelField = this.registerStaticField(
                ANTIALIASING_LEVEL_FIELD, false, Integer.TYPE);
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }
}

