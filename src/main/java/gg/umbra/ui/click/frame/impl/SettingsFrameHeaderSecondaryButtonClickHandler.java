package gg.umbra.ui.click.frame.impl;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.SettingsFrameHeaderComponent;

class SettingsFrameHeaderSecondaryButtonClickHandler
implements GuiClickListener {
    final SettingsFrameHeaderComponent Q;

    SettingsFrameHeaderSecondaryButtonClickHandler(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        this.Q = settingsFrameHeaderComponent;
    }

    @Override
    public void onPrimaryClick() {
        if (SettingsFrameHeaderComponent.O(this.Q) != null) {
            SettingsFrameHeaderComponent.O(this.Q).onPrimaryClick();
        }
    }

}

