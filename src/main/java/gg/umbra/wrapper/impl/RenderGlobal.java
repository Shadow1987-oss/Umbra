package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class RenderGlobal
extends Wrapper {
    public RenderGlobal(Object renderGlobalHandle) {
        super(renderGlobalHandle);
    }

    public void loadRenderers() {
        RenderGlobal.umbraInstance.getMappingsMapperCompat().renderGlobal.loadRenderers(this.I);
    }
}
