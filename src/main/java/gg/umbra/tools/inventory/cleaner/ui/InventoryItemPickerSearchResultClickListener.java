package gg.umbra.tools.inventory.cleaner.ui;

import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.settings.ClientSettings;
import gg.umbra.tools.inventory.cleaner.ui.InventoryItemPickerPanel;
import gg.umbra.tools.inventory.cleaner.ui.ItemPickerSelection;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import java.awt.Point;

public class InventoryItemPickerSearchResultClickListener
implements GuiMouseListener {
    final InventoryItemPickerPanel panel;
    final ItemMappingEntry mappingEntry;

    private void selectItem(ItemMappingEntry itemMappingEntry) {
        InventoryItemPickerPanel.select(this.panel, ItemPickerSelection.ofRight(itemMappingEntry));
        InventoryItemPickerPanel.getSelectedItemIds(this.panel).remove(itemMappingEntry.getResourceKey());
        this.panel.showSearchView();
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        ClientSettings.UI_EXECUTOR.execute(() -> this.selectItem(this.mappingEntry));
    }

    public InventoryItemPickerSearchResultClickListener(InventoryItemPickerPanel inventoryItemPickerPanel, ItemMappingEntry itemMappingEntry) {
        this.panel = inventoryItemPickerPanel;
        this.mappingEntry = itemMappingEntry;
    }
}

