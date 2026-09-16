package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderTypeBufferBridge;
import gg.umbra.wrapper.Wrapper;

public class RenderTypeBuffer
extends Wrapper {
    public RenderTypeBuffer(Object handle) {
        super(handle);
    }

    public void onInputReceived() {
        MRenderTypeBufferBridge.onInputReceived(RenderTypeBuffer.umbraInstance.getMappingsMapperCompat().framerateLimitTracker, this.getObject());
    }
}
