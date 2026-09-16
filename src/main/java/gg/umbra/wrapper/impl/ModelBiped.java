package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MModelBiped;

public class ModelBiped
extends ModelBipedSkeletonBridge {
    public ModelBiped(Object object) {
        super(object);
    }

    public ModelRenderer getBipedLeftArm() {
        return new ModelRenderer(MModelBiped.j(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedLeftLeg() {
        return new ModelRenderer(MModelBiped.k(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedRightLeg() {
        return new ModelRenderer(MModelBiped.J(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedRightArm() {
        return new ModelRenderer(MModelBiped.R(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer Z() {
        return new ModelRenderer(MModelBiped.a(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer F() {
        return new ModelRenderer(MModelBiped.x(ModelBiped.umbraInstance.getMappingsMapperCompat().Ct, this.I));
    }
}

