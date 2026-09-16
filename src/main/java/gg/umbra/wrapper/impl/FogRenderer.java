package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class FogRenderer
extends Wrapper {
    public FogRenderer(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBuffer(FogType fogType) {
        return FogRenderer.umbraInstance.getMappingsMapperCompat().fogRenderer.getBuffer(this.I, fogType.getObject());
    }
}
