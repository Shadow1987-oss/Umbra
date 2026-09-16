package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MLightTexture;
import gg.umbra.wrapper.Wrapper;

public class LightTexture
extends Wrapper {
    public void Z(float f) {
        MLightTexture.B(LightTexture.umbraInstance.getMappingsMapperCompat().hF, this.I, f);
    }

    public void V() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return;
        }
        MLightTexture.f(LightTexture.umbraInstance.getMappingsMapperCompat().hF, this.I);
    }

    public LightTexture(Object object) {
        super(object);
    }


    public void X() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return;
        }
        MLightTexture.z(LightTexture.umbraInstance.getMappingsMapperCompat().hF, this.I);
    }
}

