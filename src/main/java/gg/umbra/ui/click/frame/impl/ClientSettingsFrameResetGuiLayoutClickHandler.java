package gg.umbra.ui.click.frame.impl;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrame;

public class ClientSettingsFrameResetGuiLayoutClickHandler
implements GuiClickListener {
    final ClientSettingsFrame j;

    @Override
    public void onPrimaryClick() {
        ClientSettingsFrame.n(this.j);
    }

    public ClientSettingsFrameResetGuiLayoutClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.j = clientSettingsFrame;
    }
}
