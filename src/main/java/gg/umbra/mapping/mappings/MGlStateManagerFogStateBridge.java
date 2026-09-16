package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import java.util.List;

public class MGlStateManagerFogStateBridge
extends Mapping {
    private static final String ITEM_STATES_FIELD_NAME = "itemStates";
    private final MappingField itemStatesField;

    public MGlStateManagerFogStateBridge() {
        super(MappedClasses.zM);
        this.itemStatesField = this.J(ITEM_STATES_FIELD_NAME, true, List.class);
    }

    public List getItemStates(Object fogState) {
        return (List)this.itemStatesField.getObject(fogState);
    }
}

