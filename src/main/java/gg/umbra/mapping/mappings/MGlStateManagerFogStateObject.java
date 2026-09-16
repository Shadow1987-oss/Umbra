package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MTextComponentTranslationBridge;
import gg.umbra.ui.click.component.GuiComponent;

public class MGlStateManagerFogStateObject
extends Mapping {
    private final MappingMethod resetMethod;
    private final MappingField currentField;

    public Object getCurrent(Object fogStateHandle) {
        return this.currentField.getObject(fogStateHandle);
    }

    public MGlStateManagerFogStateObject() {
        this(MTextComponentTranslationBridge.isControlFlowStateEnabled());
    }

    private MGlStateManagerFogStateObject(boolean controlFlowState) {
        super(MappedClasses.i);
        this.currentField = this.J("current", true, MappedClasses.zM);
        this.resetMethod = this.Y("reset", true, Void.TYPE);
        if (controlFlowState) {
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[4]);
    }


    public void reset(Object fogStateHandle) {
        this.resetMethod.invokeVoidNoArgs(fogStateHandle);
    }
}

