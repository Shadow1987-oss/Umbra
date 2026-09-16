package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEnumHandBridge;
import gg.umbra.wrapper.Wrapper;

public class EnumHandValueBridge
extends Wrapper {
    public static EnumHandValueBridge v() {
        return new EnumHandValueBridge(MEnumHandBridge.c(EnumHandValueBridge.umbraInstance.getMappingsMapperCompat().hR));
    }

    public EnumHandValueBridge(Object object) {
        super(object);
    }
}

