package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MItemBlock
extends Mapping {
    private static final String STORAGE_FIELD_NAME = "storage";
    private final MappingField storageField;

    public Object getStorage(Object itemBlock) {
        return this.storageField.getObject(itemBlock);
    }

    public MItemBlock() {
        super(MappedClasses.I);
        this.storageField = this.J(STORAGE_FIELD_NAME, true, MappedClasses.zd);
    }
}

