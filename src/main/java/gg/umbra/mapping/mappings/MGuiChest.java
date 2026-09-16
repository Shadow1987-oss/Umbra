package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.mappings.MGuiContainer;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MGuiChest
extends Mapping {
    private final MappingField lowerChestInventoryField;

    public Object getLowerChestInventory(Object screenHandle) {
        return this.lowerChestInventoryField.getObject(screenHandle);
    }

    public MGuiChest() {
        this(MGuiContainer.l());
    }

    private MGuiChest(String[] legacyMappingState) {
        super(MappedClasses.qs);
        if (legacyMappingState != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.lowerChestInventoryField = this.registerInstanceFieldForOwner(
                        MappedClasses.zZ, "lowerChestInventory", true, MappedClasses.l0);
            } else {
                this.lowerChestInventoryField = this.J("lowerChestInventory", true, MappedClasses.l0);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MGuiContainer.r(new String[4]);
            }
            return;
        }
        this.lowerChestInventoryField = this.J("lowerChestInventory", true, MappedClasses.l0);
        if (GuiComponent.getLegacyComponentState() == null) {
            MGuiContainer.r(new String[4]);
        }
    }

}
