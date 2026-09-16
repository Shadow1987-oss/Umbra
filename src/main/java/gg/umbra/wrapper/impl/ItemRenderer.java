package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MItemRenderer;
import gg.umbra.wrapper.Wrapper;

public class ItemRenderer
extends Wrapper {
    public void g(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemRendererBridge itemRendererBridge) {
        ItemRenderer.umbraInstance.getMappingsMapperCompat().qE.Z(this.I, entityLivingBase.getObject(), itemStack.getObject(), itemRendererBridge.getObject());
    }

    public ItemRenderer(Object object) {
        super(object);
    }

    public void X(AbstractClientPlayer abstractClientPlayer) {
        MItemRenderer.K(ItemRenderer.umbraInstance.getMappingsMapperCompat().qE, this.I, abstractClientPlayer.getObject());
    }

    public float e() {
        return ItemRenderer.umbraInstance.getMappingsMapperCompat().qE.S(this.I);
    }

    public ItemStack k() {
        return new ItemStack(ItemRenderer.umbraInstance.getMappingsMapperCompat().qE.v(this.I));
    }

    public float R() {
        return ItemRenderer.umbraInstance.getMappingsMapperCompat().qE.Y(this.I);
    }
}

