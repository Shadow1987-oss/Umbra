package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityEquipmentSlot;
import gg.umbra.wrapper.Wrapper;

public class EntityEquipmentSlot
extends Wrapper {
    public static EntityEquipmentSlot L() {
        return new EntityEquipmentSlot(MEntityEquipmentSlot.Q(EntityEquipmentSlot.umbraInstance.getMappingsMapperCompat().CY));
    }

    public boolean v() {
        return MEntityEquipmentSlot.S(EntityEquipmentSlot.umbraInstance.getMappingsMapperCompat().CY, this.I);
    }

    public EntityEquipmentSlot(Object object) {
        super(object);
    }

    public int W() {
        return MEntityEquipmentSlot.c(EntityEquipmentSlot.umbraInstance.getMappingsMapperCompat().CY, this.I);
    }
}

