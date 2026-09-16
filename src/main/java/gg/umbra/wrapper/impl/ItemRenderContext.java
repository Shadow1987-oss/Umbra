package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ItemRenderContext
extends Wrapper {
    public static ItemRenderContext create(boolean wasItemInteraction, ItemStack heldItem) {
        return new ItemRenderContext(ItemRenderContext.umbraInstance.getMappingsMapperCompat().itemRenderContext.create(wasItemInteraction, heldItem.getObject()));
    }

    public static ItemRenderContext defaultContext() {
        return new ItemRenderContext(ItemRenderContext.umbraInstance.getMappingsMapperCompat().itemRenderContext.getDefault());
    }

    public ItemRenderContext(Object handle) {
        super(handle);
    }

    public static ItemRenderContext none() {
        return new ItemRenderContext(ItemRenderContext.umbraInstance.getMappingsMapperCompat().itemRenderContext.getNone());
    }
}
