package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class GlStateManager$TextureState
extends Wrapper {
    public GlStateManager$TextureState(Object textureStateHandle) {
        super(textureStateHandle);
    }

    public int getTextureName() {
        return GlStateManager$TextureState.umbraInstance.getMappingsMapperCompat().glTextureState.getTextureName(this.I);
    }

    public void setTextureName(int textureName) {
        GlStateManager$TextureState.umbraInstance.getMappingsMapperCompat().glTextureState
                .setTextureName(this.I, textureName);
    }
}
