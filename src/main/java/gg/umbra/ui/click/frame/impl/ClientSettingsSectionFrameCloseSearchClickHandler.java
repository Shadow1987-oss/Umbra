package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSectionFrame;

class ClientSettingsSectionFrameCloseSearchClickHandler
implements GuiClickListener {
    final ClientSettingsSectionFrame V;

    @Override
    public void onPrimaryClick() {
        ClientSettings.setFrameVisibility(ClientSettingsSearchFrame.class, false);
    }

    ClientSettingsSectionFrameCloseSearchClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
        this.V = clientSettingsSectionFrame;
    }
}
