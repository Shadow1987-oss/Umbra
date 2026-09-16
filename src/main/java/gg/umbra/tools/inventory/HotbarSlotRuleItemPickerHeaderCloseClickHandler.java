package gg.umbra.tools.inventory;

import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.ui.click.component.GuiClickListener;

class HotbarSlotRuleItemPickerHeaderCloseClickHandler
implements GuiClickListener {
    final HotbarSlotRuleItemPickerFrame pickerFrame;

    HotbarSlotRuleItemPickerHeaderCloseClickHandler(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.pickerFrame.commitSelection();
        this.pickerFrame.closePicker();
    }
}
