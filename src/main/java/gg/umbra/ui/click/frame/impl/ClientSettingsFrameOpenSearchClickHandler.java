package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;

public class ClientSettingsFrameOpenSearchClickHandler
implements GuiClickListener {
    final ClientSettingsFrame f;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        clientSettingsSearchFrame.M(this.f.double_G(), this.f.double_n());
        clientSettingsSearchFrame.t(true, false);
    }

    public ClientSettingsFrameOpenSearchClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.f = clientSettingsFrame;
    }
}
