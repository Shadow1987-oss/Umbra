package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

import java.util.List;

public class ShaderGroup
extends Wrapper {
    public ShaderGroup(Object object) {
        super(object);
    }

    public void resize(int n, int n2) {
        ShaderGroup.umbraInstance.getMappingsMapperCompat().Qv.G(this.I, n, n2);
    }

    public static ShaderGroup create(TextureManager textureManager, ShaderGroupState shaderGroupState, Framebuffer framebuffer, ResourceLocation resourceLocation) {
        return new ShaderGroup(ShaderGroup.umbraInstance.getMappingsMapperCompat().Qv.k(textureManager.getObject(), shaderGroupState.getObject(), framebuffer.getObject(), resourceLocation.getObject()));
    }

    public List getFramebuffers() {
        return ShaderGroup.umbraInstance.getMappingsMapperCompat().Qv.h(this.I);
    }
}

