package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MMappedFieldSingletonWrapper;
import gg.umbra.wrapper.Wrapper;

public class MappedFieldSingletonWrapper
extends Wrapper {
    private static MappedFieldSingletonWrapper humanoidArmorInstance;

    public MappedFieldSingletonWrapper(Object wrappedObject) {
        super(wrappedObject);
    }


    public static MappedFieldSingletonWrapper humanoidArmor() {
        if (humanoidArmorInstance == null) {
            humanoidArmorInstance = new MappedFieldSingletonWrapper(MMappedFieldSingletonWrapper.getHumanoidArmorField(Umbra.INSTANCE.getMappings().CL).getObject(null));
        }
        return humanoidArmorInstance;
    }
}

