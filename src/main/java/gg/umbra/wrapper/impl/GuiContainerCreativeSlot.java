package gg.umbra.wrapper.impl;

public class GuiContainerCreativeSlot
extends Slot {
    public Slot getWrappedSlot() {
        Object slotHandle = GuiContainerCreativeSlot.umbraInstance.getMappingsMapperCompat().guiContainerCreativeSlot
                .getWrappedSlot(this.I);
        return new Slot(slotHandle);
    }

    public GuiContainerCreativeSlot(Object creativeSlotHandle) {
        super(creativeSlotHandle);
    }
}
