package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.tools.inventory.cleaner.InventoryCleanerProfile;
import gg.umbra.tools.inventory.cleaner.InventoryCleanerProfileValue;

public class InventoryCleanerProfileEditContext {
    public InventoryCleanerProfileValue profileValue;
    public InventoryCleanerProfile profile;
    public Runnable onClose;

    public InventoryCleanerProfileEditContext(InventoryCleanerProfileValue inventoryCleanerProfileValue, InventoryCleanerProfile inventoryCleanerProfile, Runnable runnable) {
        this.profileValue = inventoryCleanerProfileValue;
        this.profile = inventoryCleanerProfile;
        this.onClose = runnable;
    }
}
