package gg.umbra.tools.inventory;

import gg.umbra.tools.inventory.HotbarSlotRuleItemPickerFrame;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;

public class HotbarSlotRuleItemPickerHeaderComponent
extends PublicProfilesFrameHeaderActionComponent {
    final HotbarSlotRuleItemPickerFrame pickerFrame;

    @Override
    public double x() {
        return 330.0;
    }

    public HotbarSlotRuleItemPickerHeaderComponent(HotbarSlotRuleItemPickerFrame hotbarSlotRuleItemPickerFrame, Frame frame, String string, String string2) {
        super(frame, string, string2);
        this.pickerFrame = hotbarSlotRuleItemPickerFrame;
    }
}
