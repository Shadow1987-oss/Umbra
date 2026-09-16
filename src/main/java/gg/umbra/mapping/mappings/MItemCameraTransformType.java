package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;

public class MItemCameraTransformType
extends Mapping {
    private static final String ITEMS_FIELD_NAME;
    private MappingField itemsField;
    private static boolean repairableControlFlowState;


    public static boolean getRepairableControlFlowState() {
        return repairableControlFlowState;
    }

    public static boolean shouldSkipLegacyStateUpdate() {
        boolean controlFlowState = MItemCameraTransformType.getRepairableControlFlowState();
        return true;
    }

    public MItemCameraTransformType() {
        super(MappedClasses.Z4);
        Class itemsFieldType = MappedClasses.u_;
        boolean itemsFieldPublic = true;
        String itemsFieldName = ITEMS_FIELD_NAME;
        MItemCameraTransformType mapping = this;
        this.itemsField = mapping.J(itemsFieldName, itemsFieldPublic, itemsFieldType);
        if (MItemCameraTransformType.shouldSkipLegacyStateUpdate()) {
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[2]);
    }

    public Object getItems(Object repairableHandle) {
        return this.itemsField.getObject(repairableHandle);
    }

    static {
        MItemCameraTransformType.setRepairableControlFlowState(false);
        ITEMS_FIELD_NAME = "items";
    }

    public static void setRepairableControlFlowState(boolean state) {
        repairableControlFlowState = state;
    }
}

