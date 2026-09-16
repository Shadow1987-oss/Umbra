package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ResourceLocationConstantPair;

public class MResourceLocationConstantsBridge
extends Mapping {
    private final MappingField guiField;
    private final MappingField itemsField;

    public Object getItems() {
        return this.itemsField.getObject(null);
    }

    public Object getGui() {
        return this.guiField.getObject(null);
    }


    public MResourceLocationConstantsBridge() {
        this(ResourceLocationConstantPair.getControlFlowState());
    }

    private MResourceLocationConstantsBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.qq);
        this.guiField = this.registerStaticField("GUI", true, MappedClasses.zC);
        this.itemsField = this.registerStaticField("ITEMS", true, MappedClasses.zC);
        if (controlFlowState != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }
}

