package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityEquipmentSlotHolder;
import gg.umbra.wrapper.Wrapper;

public class EntityEquipmentSlotHolder
extends Wrapper {
    public EntityEquipmentSlot getSlot() {
        return new EntityEquipmentSlot(MEntityEquipmentSlotHolder.getSlot(EntityEquipmentSlotHolder.umbraInstance.getMappingsMapperCompat().hh, this.I));
    }

    public EntityEquipmentSlotHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}
