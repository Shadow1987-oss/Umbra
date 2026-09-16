package gg.umbra.tools.inventory;

import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.tools.inventory.HotbarSlotRuleItemSearchComponent;
import gg.umbra.ui.click.component.GuiKeyTypedListener;

public class HotbarSlotRuleSearchInputKeyTypedListener
implements GuiKeyTypedListener {
    final HotbarSlotRuleItemPickerFrame pickerFrame;
    final HotbarSlotRuleItemSearchComponent searchComponent;

    public HotbarSlotRuleSearchInputKeyTypedListener(HotbarSlotRuleItemSearchComponent hotbarSlotRuleItemSearchComponent, HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame) {
        this.searchComponent = hotbarSlotRuleItemSearchComponent;
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }

    @Override
    public void onKeyTyped(char c, int n) {
        this.pickerFrame.s(HotbarSlotRuleItemSearchComponent.g(this.searchComponent).isShowEditButton());
    }
}
