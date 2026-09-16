package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEnumWorldBlockLayer;
import gg.umbra.wrapper.Wrapper;

public class EnumWorldBlockLayer
extends Wrapper {
    private static EnumWorldBlockLayer translucent;
    private static EnumWorldBlockLayer solid;

    public static EnumWorldBlockLayer translucent() {
        if (translucent == null) {
            translucent = new EnumWorldBlockLayer(MEnumWorldBlockLayer.getTranslucent(EnumWorldBlockLayer.umbraInstance.getMappingsMapperCompat().worldBlockLayer));
        }
        return translucent;
    }


    public EnumWorldBlockLayer(Object handle) {
        super(handle);
    }

    public static EnumWorldBlockLayer solid() {
        if (solid == null) {
            solid = new EnumWorldBlockLayer(MEnumWorldBlockLayer.getSolid(EnumWorldBlockLayer.umbraInstance.getMappingsMapperCompat().worldBlockLayer));
        }
        return solid;
    }
}

