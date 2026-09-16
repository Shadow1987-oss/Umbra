package gg.umbra.wrapper.impl;

public class ItemActionResult
extends EnumActionResult {
    public static ItemActionResult create(GlStateManagerTexGenCoord swingSource, ItemRenderContext itemContext) {
        return new ItemActionResult(ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.create(swingSource.getObject(), itemContext.getObject()));
    }

    public boolean consumesAction() {
        return ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.consumesAction(this.getObject());
    }

    public ItemActionResult withoutItem() {
        return new ItemActionResult(ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.withoutItem(this.getObject()));
    }

    public ItemStack getHeldItemTransformedTo() {
        return new ItemStack(ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.getHeldItemTransformedTo(this.getObject()));
    }

    public ItemActionResult heldItemTransformedTo(ItemStack itemStack) {
        return new ItemActionResult(ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.heldItemTransformedTo(this.getObject(), itemStack.getObject()));
    }

    public boolean wasItemInteraction() {
        return ItemActionResult.umbraInstance.getMappingsMapperCompat().itemActionResult.wasItemInteraction(this.getObject());
    }

    public ItemActionResult(Object handle) {
        super(handle);
    }
}
