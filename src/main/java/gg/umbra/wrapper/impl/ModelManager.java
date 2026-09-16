package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ModelManager
extends Wrapper {
    public TextureAtlas getAtlas(ResourceLocation location) {
        return new TextureAtlas(ModelManager.umbraInstance.getMappingsMapperCompat().CZ.getAtlas(this.I, location.getObject()));
    }

    public ModelManager(Object object) {
        super(object);
    }
}
