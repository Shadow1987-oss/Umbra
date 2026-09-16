package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MInventoryListBridge;
import gg.umbra.wrapper.Wrapper;
import java.util.List;

public class InventoryListBridge
extends Wrapper {
    public List getSlots() {
        return MInventoryListBridge.getSlots(InventoryListBridge.umbraInstance.getMappingsMapperCompat().equipmentSlotGroup, this.I);
    }

    public InventoryListBridge(Object handle) {
        super(handle);
    }

    public static InventoryListBridge armor() {
        return new InventoryListBridge(MInventoryListBridge.getArmor(InventoryListBridge.umbraInstance.getMappingsMapperCompat().equipmentSlotGroup));
    }
}
