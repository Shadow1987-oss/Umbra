package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTextureManager;
import gg.umbra.wrapper.Wrapper;

public class TextureManager
extends Wrapper {

    public TextureManager(Object object) {
        super(object);
    }

    public void bindTexture(ResourceLocation location) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        MTextureManager.bindTexture(TextureManager.umbraInstance.getMappingsMapperCompat().textureManager, this.I, location.getObject());
    }

    public TextureObject getTexture(ResourceLocation location) {
        return new TextureObject(MTextureManager.getTexture(TextureManager.umbraInstance.getMappingsMapperCompat().textureManager, this.I, location.getObject()));
    }
}

