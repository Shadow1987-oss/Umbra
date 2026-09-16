package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiKeyTypedListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

public class ClientSettingsSearchFrameHeaderInputChangeListener
implements GuiKeyTypedListener {
    final ClientSettingsSearchFrameHeader W;
    final ClientSettingsSearchFrame y;


    @Override
    public void onKeyTyped(char c, int n) {
        if (ClientSettingsSearchFrameHeader.b(this.W) != ClientSettingsSearchFrameHeader.j(this.W).getText().length()) {
            this.y.rebuildContent();
            ClientSettings.activeTooltips = null;
        }
        ClientSettingsSearchFrameHeader.L(this.W, ClientSettingsSearchFrameHeader.j(this.W).getText().length());
    }

    public ClientSettingsSearchFrameHeaderInputChangeListener(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader, ClientSettingsSearchFrame clientSettingsSearchFrame) {
        this.W = clientSettingsSearchFrameHeader;
        this.y = clientSettingsSearchFrame;
    }
}
