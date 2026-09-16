package gg.umbra.tools.inventory.cleaner;

import gg.umbra.tools.inventory.cleaner.SlotInventoryFilterRule;

public class ArmorSlotInventoryFilterRule
extends SlotInventoryFilterRule {
    @Override
    public int getContainerSlot() {
        return 5 + super.getSlot();
    }

    public ArmorSlotInventoryFilterRule(int armorSlot) {
        super(armorSlot);
    }
}
