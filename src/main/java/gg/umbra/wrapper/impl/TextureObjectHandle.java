package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class TextureObjectHandle
extends Wrapper {
    public int resolveFramebufferId(int depthTextureId) {
        return TextureObjectHandle.umbraInstance.getMappingsMapperCompat().gpuTexture.resolveFramebufferId(this.I, depthTextureId);
    }

    public int getId() {
        return TextureObjectHandle.umbraInstance.getMappingsMapperCompat().gpuTexture.getTextureId(this.I);
    }

    public TextureObjectHandle(Object object) {
        super(object);
    }
}
