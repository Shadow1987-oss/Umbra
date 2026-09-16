package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MSlot;
import gg.umbra.wrapper.Wrapper;

public class Slot
extends Wrapper {
    public Slot(Object object) {
        super(object);
    }


    public int getSlotIndex() {
        return MSlot.getSlotIndex(Slot.umbraInstance.getMappingsMapperCompat().slot, this.getObject());
    }

    public Inventory getInventory() {
        return new Inventory(MSlot.getInventoryOrContainer(Slot.umbraInstance.getMappingsMapperCompat().slot, this.getObject()));
    }

    public int getSlotNumber() {
        return MSlot.getSlotNumber(Slot.umbraInstance.getMappingsMapperCompat().slot, this.getObject());
    }

    public ItemStack getStack() {
        return new ItemStack(Slot.umbraInstance.getMappingsMapperCompat().slot.getStack(this.getObject()));
    }

    public boolean hasStack() {
        if (ForgeVersion.MC_1_21_11.d()) {
            return !this.getStack().isNull();
        }
        return Slot.umbraInstance.getMappingsMapperCompat().slot.getStack(this.getObject()) != null;
    }
}

