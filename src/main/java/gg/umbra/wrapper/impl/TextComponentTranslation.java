package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class TextComponentTranslation
extends Wrapper {
    public Object getModelIdentity() {
        return TextComponentTranslation.umbraInstance.getMappings().trackingItemStackRenderState.getModelIdentity(this.I);
    }

    public TextComponentTranslation(Object handle) {
        super(handle);
    }
}
