package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EnumHand
extends Wrapper {
    public static EnumHand mainHand() {
        return new EnumHand(EnumHand.umbraInstance.getMappingsMapperCompat().DX.getMainHand());
    }

    public static EnumHand offHand() {
        return new EnumHand(EnumHand.umbraInstance.getMappingsMapperCompat().DX.getOffHand());
    }

    public EnumHand(Object wrappedObject) {
        super(wrappedObject);
    }
}
