package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class NonNullList
extends Wrapper {
    public NonNullList(Object listHandle) {
        super(listHandle);
    }

    public static NonNullList create() {
        return new NonNullList(NonNullList.umbraInstance.getMappingsMapperCompat().nonNullList.create());
    }
}
