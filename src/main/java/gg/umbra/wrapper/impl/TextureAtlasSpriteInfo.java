package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class TextureAtlasSpriteInfo
extends Wrapper {
    public TextureAtlasSpriteInfo(Object handle) {
        super(handle);
    }

    public ResourceLocation getTexturePath() {
        return new ResourceLocation(TextureAtlasSpriteInfo.umbraInstance.getMappingsMapperCompat().textureAtlasSpriteInfo.getTexturePath(this.I));
    }
}
