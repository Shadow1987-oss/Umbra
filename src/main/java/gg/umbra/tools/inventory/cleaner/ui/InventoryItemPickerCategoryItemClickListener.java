package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.tools.inventory.cleaner.ui.InventoryItemPickerPanel;
import gg.umbra.tools.inventory.cleaner.ui.ItemPickerSelection;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import java.awt.Point;

public class InventoryItemPickerCategoryItemClickListener
implements GuiMouseListener {
    final ItemMappingEntry mappingEntry;
    final InventoryItemPickerPanel panel;

    public InventoryItemPickerCategoryItemClickListener(InventoryItemPickerPanel inventoryItemPickerPanel, ItemMappingEntry itemMappingEntry) {
        this.panel = inventoryItemPickerPanel;
        this.mappingEntry = itemMappingEntry;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        InventoryItemPickerPanel.select(this.panel, ItemPickerSelection.ofRight(this.mappingEntry));
    }
}
