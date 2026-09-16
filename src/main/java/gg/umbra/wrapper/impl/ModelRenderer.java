package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MModelRenderer;
import gg.umbra.wrapper.Wrapper;

public class ModelRenderer
extends Wrapper {
    public float j() {
        return MModelRenderer.h(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float s() {
        return MModelRenderer.k(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public void n(boolean bl) {
        MModelRenderer.r(ModelRenderer.umbraInstance.getMappings().Rz, this.I, bl);
    }

    public ModelRenderer(Object object) {
        super(object);
    }

    public float M() {
        return MModelRenderer.i(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float B() {
        return MModelRenderer.v(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float q() {
        return MModelRenderer.b(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float getRotateAngleY() {
        return MModelRenderer.s(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float getRotateAngleZ() {
        return MModelRenderer.U(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public void S(boolean bl) {
        MModelRenderer.j(ModelRenderer.umbraInstance.getMappings().Rz, this.I, bl);
    }

    public float getRotateAngleX() {
        return MModelRenderer.c(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public int s$src$I$x0ut69() {
        return MModelRenderer.z(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public float F() {
        return MModelRenderer.t(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }

    public int C() {
        return MModelRenderer.j(ModelRenderer.umbraInstance.getMappings().Rz, this.I);
    }
}

