package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSectionFrame;

class ClientSettingsSectionFrameCloseSettingsClickHandler
implements GuiClickListener {
    final ClientSettingsSectionFrame J;

    ClientSettingsSectionFrameCloseSettingsClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
        this.J = clientSettingsSectionFrame;
    }

    @Override
    public void onPrimaryClick() {
        ClientSettings.setFrameVisibility(ClientSettingsFrame.class, false);
        this.J.t(false, false);
    }
}
