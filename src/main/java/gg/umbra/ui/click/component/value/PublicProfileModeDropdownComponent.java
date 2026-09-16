package gg.umbra.ui.click.component.value;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.DropdownSelectComponent;
import gg.umbra.ui.font.FontOption;
import gg.umbra.value.OptionSetting;

public final class PublicProfileModeDropdownComponent
extends DropdownSelectComponent<FontOption> {
    public PublicProfileModeDropdownComponent(OptionSetting modeValue) {
        super(modeValue);
    }

    @Override
    public void onSelectionChanged() {
        Umbra.INSTANCE.getFontSelector().N(this.getSelectedValue());
    }
}
