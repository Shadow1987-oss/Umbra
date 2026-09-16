package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;
import java.util.List;

public class MItemAttributeModifiersComponent
extends Mapping {
    private static GuiComponent[] controlFlowState;
    private final MappingField modifiersField;
    private static final String MODIFIERS_FIELD_NAME;

    public MItemAttributeModifiersComponent() {
        super(MappedClasses.Dp);
        this.modifiersField = this.J(MODIFIERS_FIELD_NAME, true, List.class);
    }

    public static void setItemAttributeModifiersControlFlowState(GuiComponent[] state) {
        controlFlowState = state;
    }

    static {
        MItemAttributeModifiersComponent.setItemAttributeModifiersControlFlowState(new GuiComponent[4]);
        MODIFIERS_FIELD_NAME = "modifiers";
    }

    public static GuiComponent[] getItemAttributeModifiersControlFlowState() {
        return controlFlowState;
    }

    public List<Object> getModifiers(Object component) {
        return (List)this.modifiersField.getObject(component);
    }
}

