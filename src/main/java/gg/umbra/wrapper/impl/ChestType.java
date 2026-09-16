package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ChestType
extends Wrapper {
    public ChestType(Object object) {
        super(object);
    }

    public static ChestType e() {
        return new ChestType(ChestType.umbraInstance.getMappingsMapperCompat().qa.getFallDamageResetting());
    }
}
