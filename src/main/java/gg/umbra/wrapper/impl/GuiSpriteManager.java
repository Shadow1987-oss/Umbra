package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class GuiSpriteManager
extends Wrapper {
    public TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        return new TextureAtlasSprite(GuiSpriteManager.umbraInstance.getMappingsMapperCompat().h4.getSprite(this.I, resourceLocation.getObject()));
    }

    public GuiSpriteManager(Object handle) {
        super(handle);
    }
}
