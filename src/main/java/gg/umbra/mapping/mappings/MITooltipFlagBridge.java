package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;

public class MITooltipFlagBridge
extends Mapping {
    private static final String WATER_FIELD_NAME;
    private final MappingField waterField;
    private static GuiComponent[] fluidModeControlFlowState;


    public static void setFluidModeControlFlowState(GuiComponent[] controlFlowState) {
        fluidModeControlFlowState = controlFlowState;
    }

    static {
        MITooltipFlagBridge.setFluidModeControlFlowState(new GuiComponent[2]);
        WATER_FIELD_NAME = "WATER";
    }

    public Object getWater() {
        return this.waterField.getObject(null);
    }

    public MITooltipFlagBridge() {
        this(MITooltipFlagBridge.getFluidModeControlFlowState());
    }

    private MITooltipFlagBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.Y9);
        GuiComponent[] currentControlFlowState = controlFlowState;
        Class waterFieldType = MappedClasses.Y9;
        boolean waterFieldPublic = true;
        String waterFieldName = WATER_FIELD_NAME;
        MITooltipFlagBridge mapping = this;
        this.waterField = mapping.registerStaticField(waterFieldName, waterFieldPublic, waterFieldType);
        if (GuiComponent.getLegacyComponentState() == null) {
            MITooltipFlagBridge.setFluidModeControlFlowState(new GuiComponent[1]);
        }
    }

    public static GuiComponent[] getFluidModeControlFlowState() {
        return fluidModeControlFlowState;
    }
}

