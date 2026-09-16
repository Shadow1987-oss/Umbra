package gg.umbra.ui.click.component.value;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.value.ListValueOptionsPanel;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.value.ItemFilterList;

public class RemoveItemFilterListEntryHandler
implements GuiClickListener {
    final ItemLimitData entry;
    final ListValueOptionsPanel optionsPanel;

    public RemoveItemFilterListEntryHandler(ListValueOptionsPanel listValueOptionsPanel, ItemLimitData itemLimitData) {
        this.optionsPanel = listValueOptionsPanel;
        this.entry = itemLimitData;
    }

    @Override
    public void onPrimaryClick() {
        ((ItemFilterList)ListValueOptionsPanel.getListValueCompat(this.optionsPanel)).removeEntry(this.entry);
        ListValueOptionsPanel.getListValueCompat(this.optionsPanel).notifyChanged();
        this.optionsPanel.refreshEntries();
    }
}
