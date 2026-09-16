package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ItemAttributeModifiersComponent$Entry
extends Wrapper {
    public EquipmentSlotGroup getEquipmentSlotGroup() {
        Holder attributeHolder = new Holder(ItemAttributeModifiersComponent$Entry.umbraInstance.getMappingsMapperCompat().DN.getAttribute(this.I));
        return new EquipmentSlotGroup(attributeHolder.N());
    }

    public AttributeModifier getModifier() {
        return new AttributeModifier(ItemAttributeModifiersComponent$Entry.umbraInstance.getMappingsMapperCompat().DN.getModifier(this.I));
    }

    public ItemAttributeModifiersComponent$Entry(Object wrappedObject) {
        super(wrappedObject);
    }
}
