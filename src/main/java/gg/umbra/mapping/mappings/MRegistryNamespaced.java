package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.MappingMethodBuilder;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MRegistryNamespaced
extends Mapping {
    private final MappingMethod getByValueMethod;

    public MRegistryNamespaced() {
        super(MappedClasses.lz);
        this.getByValueMethod = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder("getByValue", Object.class, new Class[]{Integer.TYPE}).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "byId")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.ua)).buildMethod();
    }

    public Object getByValue(Object registry, int id) {
        return this.getByValueMethod.invokeObject(registry, id);
    }
}

