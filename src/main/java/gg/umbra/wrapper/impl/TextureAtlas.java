package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTextureAtlasSpriteInfo;
import gg.umbra.wrapper.Wrapper;

public class TextureAtlas
extends Wrapper {
    public TextureAtlasSprite getSprite(ResourceLocation location) {
        return new TextureAtlasSprite(MTextureAtlasSpriteInfo.getSprite(TextureAtlas.umbraInstance.getMappings().textureAtlas, this.I, location.getObject()));
    }

    public TextureAtlas(Object object) {
        super(object);
    }

    public static ResourceLocation getBlocksAtlasLocation() {
        return new ResourceLocation(MTextureAtlasSpriteInfo.getBlocksAtlasLocation(TextureAtlas.umbraInstance.getMappings().textureAtlas));
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(MTextureAtlasSpriteInfo.getTextureLocation(TextureAtlas.umbraInstance.getMappings().textureAtlas, this.I));
    }
}
