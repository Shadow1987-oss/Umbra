package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingFieldBuilder;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MResourceKey
extends Mapping {
    private final MappingField locationField;

    public MResourceKey() {
        super(MappedClasses.qB);
        this.locationField = ((MappingFieldBuilder)this.fieldBuilder("location", MappedClasses.zC).setNameForVersion(ForgeVersion.MC_1_21_11.n(), "identifier")).buildField();
    }

    public static Object getLocation(MResourceKey mapping, Object resourceKey) {
        return mapping.readLocation(resourceKey);
    }

    private Object readLocation(Object resourceKey) {
        return this.locationField.getObject(resourceKey);
    }
}

