package gg.umbra.tools.inventory;

import gg.umbra.settings.ClientSettings;
import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.tools.inventory.HotbarSlotRuleItemSearchComponent;
import gg.umbra.ui.click.component.GuiClickListener;

public class HotbarSlotRuleItemPickerSearchCloseClickHandler
implements GuiClickListener {
    final HotbarSlotRuleItemSearchComponent searchComponent;

    @Override
    public void onPrimaryClick() {
        ClientSettings.getFrame(HotbarSlotRuleItemPickerFrame.class).commitSelection();
        ClientSettings.getFrame(HotbarSlotRuleItemPickerFrame.class).closePicker();
    }

    public HotbarSlotRuleItemPickerSearchCloseClickHandler(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent) {
        this.searchComponent = hotbarSlotRuleItemSearchComponent;
    }
}
