package gg.umbra.wrapper.impl;

public class GuiChest
extends GuiContainer {
    public String getTitle() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.F().getFormattedText();
        }
        return this.getLowerChestInventory().getName();
    }

    public GuiChest(Object chestScreenHandle) {
        super(chestScreenHandle);
    }

    public Inventory getLowerChestInventory() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object containerScreenHandle = GuiChest.umbraInstance.getMappingsMapperCompat().hK.t(this.I);
            Object inventoryHandle = GuiChest.umbraInstance.getMappingsMapperCompat().guiChest
                    .getLowerChestInventory(containerScreenHandle);
            return new Inventory(inventoryHandle);
        }
        return new Inventory(GuiChest.umbraInstance.getMappingsMapperCompat().guiChest.getLowerChestInventory(this.I));
    }

}

