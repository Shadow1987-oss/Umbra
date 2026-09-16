package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MPlayerDiggingAction
extends Mapping {
    private static final String RELEASE_USE_ITEM_FIELD_NAME = "RELEASE_USE_ITEM";
    private final MappingField releaseUseItemField;

    public MPlayerDiggingAction() {
        super(MappedClasses.FL);
        this.releaseUseItemField = this.registerStaticField(RELEASE_USE_ITEM_FIELD_NAME, true, MappedClasses.FL);
    }

    public Object getReleaseUseItem() {
        return this.releaseUseItemField.getObject(null);
    }
}

