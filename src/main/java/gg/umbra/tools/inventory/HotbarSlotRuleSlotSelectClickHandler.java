package gg.umbra.tools.inventory;

import gg.umbra.tools.inventory.HotbarSlotRule;
import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.tools.inventory.HotbarSlotRuleSlotSelectorComponent;
import gg.umbra.ui.click.component.GuiClickListener;

class HotbarSlotRuleSlotSelectClickHandler
implements GuiClickListener {
    final int slotIndex;
    final HotbarSlotRuleItemPickerFrame pickerFrame;
    final HotbarSlotRuleSlotSelectorComponent slotSelector;

    HotbarSlotRuleSlotSelectClickHandler(HotbarSlotRuleSlotSelectorComponent hotbarSlotRuleSlotSelectorComponent, HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame, int slotIndex) {
        this.slotSelector = hotbarSlotRuleSlotSelectorComponent;
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onSecondaryClick() {
        this.pickerFrame.getGroupComponent().getRules().set(this.slotIndex, new HotbarSlotRule(0));
    }

    @Override
    public void onPrimaryClick() {
        this.pickerFrame.setSelectedSlot(this.slotIndex);
    }
}

