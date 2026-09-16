package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class GlStateManager$FogState
extends Wrapper {
    public GlStateManager$FogState(Object fogStateHandle) {
        super(fogStateHandle);
    }

    public void reset() {
        GlStateManager$FogState.umbraInstance.getMappingsMapperCompat().glFogStateObject.reset(this.I);
    }

    public GlStateManagerFogStateBridge getFogMode() {
        Object fogModeHandle = GlStateManager$FogState.umbraInstance.getMappingsMapperCompat().glFogStateObject
                .getCurrent(this.I);
        return new GlStateManagerFogStateBridge(fogModeHandle);
    }
}
