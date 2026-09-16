package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MRegistryAccessBridge
extends Mapping {
    private static final String LOOKUP_OR_THROW_METHOD_NAME = "lookupOrThrow";
    private final MappingMethod lookupOrThrowMethod;

    public Object lookupOrThrow(Object registryAccess, Object resourceKey) {
        return this.lookupOrThrowMethod.invokeObject(registryAccess, resourceKey);
    }

    public MRegistryAccessBridge() {
        super(MappedClasses.zi);
        this.lookupOrThrowMethod = this.Y(LOOKUP_OR_THROW_METHOD_NAME, true, MappedClasses.l1, new Class[]{MappedClasses.qB});
    }
}

