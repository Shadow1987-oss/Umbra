package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.mappings.MEquippable;
import gg.umbra.ui.click.component.GuiComponent;

public class MResourceKeyHolder
extends Mapping {
    private static final String GOLD_FIELD_NAME = "GOLD";
    private final MappingField goldField;


    public MResourceKeyHolder() {
        this(MEquippable.p());
    }

    private MResourceKeyHolder(String controlFlowMarker) {
        super(MappedClasses.qL);
        this.goldField = this.registerStaticField(GOLD_FIELD_NAME, true, MappedClasses.qB);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEquippable.z("Xwix5b");
        }
    }

    public Object getGold() {
        return this.goldField.getObject(null);
    }
}

