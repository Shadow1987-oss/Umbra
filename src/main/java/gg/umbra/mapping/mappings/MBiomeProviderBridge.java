package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MBiomeProviderBridge
extends Mapping {
    private static final String GET_ALL_EFFECTS_METHOD_NAME = "getAllEffects";
    private final MappingMethod getAllEffectsMethod;

    public static Iterable getAllEffects(MBiomeProviderBridge mapping, Object biomeProvider) {
        return mapping.invokeGetAllEffects(biomeProvider);
    }

    public MBiomeProviderBridge() {
        super(MappedClasses.uV);
        this.getAllEffectsMethod = this.Y(GET_ALL_EFFECTS_METHOD_NAME, true, Iterable.class, new Class[]{});
    }

    private Iterable invokeGetAllEffects(Object biomeProvider) {
        return (Iterable)this.getAllEffectsMethod.invokeObject(biomeProvider, new Object[0]);
    }
}

