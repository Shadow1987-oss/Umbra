package gg.umbra.wrapper.impl;

public class EntityPotion
extends EntityEnderPearl {
    public EntityPotion(Object potionEntityHandle) {
        super(potionEntityHandle);
    }

    public ItemStack getPotion() {
        Object itemHandle = EntityPotion.umbraInstance.getMappingsMapperCompat().entityPotion
                .getPotionItem(this.getObject());
        return new ItemStack(itemHandle);
    }
}
