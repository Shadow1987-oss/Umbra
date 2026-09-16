package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MItem_ToolMaterial;
import gg.umbra.wrapper.Wrapper;

public class ToolMaterial
extends Wrapper {
    public float I() {
        return MItem_ToolMaterial.j(ToolMaterial.umbraInstance.getMappings().Re, this.I);
    }

    public static ToolMaterial S() {
        return new ToolMaterial(MItem_ToolMaterial.H(ToolMaterial.umbraInstance.getMappings().Re));
    }

    public static ToolMaterial u() {
        return new ToolMaterial(MItem_ToolMaterial.r(ToolMaterial.umbraInstance.getMappings().Re));
    }

    public ToolMaterial(Object object) {
        super(object);
    }

    public static ToolMaterial f() {
        return new ToolMaterial(MItem_ToolMaterial.U(ToolMaterial.umbraInstance.getMappings().Re));
    }

    public static ToolMaterial I$src$Lgg_umbra_wrapper_impl_ToolMaterial_$3t5lsk() {
        return new ToolMaterial(MItem_ToolMaterial.M(ToolMaterial.umbraInstance.getMappings().Re));
    }
}

