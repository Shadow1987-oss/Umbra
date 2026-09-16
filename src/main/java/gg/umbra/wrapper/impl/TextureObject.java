package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTextureObject;
import gg.umbra.wrapper.Wrapper;

public class TextureObject
extends Wrapper {
    public void setFilter(boolean blur, boolean mipmap) {
        MTextureObject.setFilter(TextureObject.umbraInstance.getMappingsMapperCompat().textureObject, this.I, blur, mipmap);
    }

    public TextureObject(Object object) {
        super(object);
    }

    public int getId() {
        if (ForgeVersion.MC_1_21_6.d()) {
            TextureObjectHandle textureHandle = new TextureObjectHandle(MTextureObject.getTexture(TextureObject.umbraInstance.getMappingsMapperCompat().textureObject, this.I));
            return textureHandle.getId();
        }
        return MTextureObject.getGlTextureId(TextureObject.umbraInstance.getMappingsMapperCompat().textureObject, this.I);
    }
}
