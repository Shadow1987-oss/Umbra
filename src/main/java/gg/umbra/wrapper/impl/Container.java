package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MContainer;
import gg.umbra.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class Container
extends Wrapper {
    public Slot getSlot(int slotIndex) {
        return new Slot(MContainer.getSlot(Container.umbraInstance.getMappings().container, this.I, slotIndex));
    }

    public Container(Object object) {
        super(object);
    }

    public ItemStack getCarried() {
        return new ItemStack(MContainer.getCarried(Container.umbraInstance.getMappings().container, this.I));
    }

    public List<Slot> getInventorySlots() {
        List slotHandles = Container.umbraInstance.getMappings().container.getSlots(this.I);
        ArrayList<Slot> slots = new ArrayList<Slot>();
        for (Object slotHandle : slotHandles) {
            slots.add(new Slot(slotHandle));
        }
        return slots;
    }

    public int getWindowId() {
        return Container.umbraInstance.getMappings().container.getWindowId(this.I);
    }
}
