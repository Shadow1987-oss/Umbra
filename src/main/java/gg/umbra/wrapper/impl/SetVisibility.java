package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SetVisibility
extends Wrapper {
    public SetVisibility(Object wrappedObject) {
        super(wrappedObject);
    }

    public void setAllVisible(boolean visible) {
        SetVisibility.umbraInstance.getMappingsMapperCompat().setVisibility.setAllVisible(this.I, visible);
    }
}
