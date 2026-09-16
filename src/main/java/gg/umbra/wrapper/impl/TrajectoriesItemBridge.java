package gg.umbra.wrapper.impl;

public class TrajectoriesItemBridge
extends Item {
    public static boolean isCharged(ItemStack itemStack) {
        return TrajectoriesItemBridge.umbraInstance.getMappingsMapperCompat().RX.isCharged(itemStack.getObject());
    }

    public TrajectoriesItemBridge(Object handle) {
        super(handle);
    }
}
