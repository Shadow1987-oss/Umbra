package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;

public class ClientSettingsFrameOpenSearchAndCloseSettingsClickHandler
implements GuiClickListener {
    final ClientSettingsFrame b;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        clientSettingsSearchFrame.M(this.b.double_G(), this.b.double_n());
        clientSettingsSearchFrame.t(true, false);
        this.b.t(false, false);
    }

    public ClientSettingsFrameOpenSearchAndCloseSettingsClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.b = clientSettingsFrame;
    }
}
