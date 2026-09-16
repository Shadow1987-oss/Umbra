package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.FrameComponent;
import gg.umbra.ui.click.frame.impl.ClientSettingsFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

public class ClientSettingsSearchFrameHeaderSettingsToggleClickHandler
implements GuiClickListener {
    final ClientSettingsSearchFrameHeader x;

    @Override
    public void onPrimaryClick() {
        ClientSettingsFrame clientSettingsFrame = ClientSettings.getFrame(ClientSettingsFrame.class);
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        if (clientSettingsFrame == null || clientSettingsSearchFrame == null) {
            return;
        }
        clientSettingsFrame.t(!clientSettingsFrame.V$src$Z$1xhop3l(), false);
        if (clientSettingsFrame.V$src$Z$1xhop3l()) {
            clientSettingsFrame.U();
        }
        clientSettingsFrame.K(clientSettingsSearchFrame.G$src$D$1b2f02a());
        clientSettingsFrame.S(clientSettingsSearchFrame.n());
        clientSettingsFrame.l$src$V$1mibm4x();
        ((FrameComponent)clientSettingsFrame).u();
    }


    public ClientSettingsSearchFrameHeaderSettingsToggleClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        this.x = clientSettingsSearchFrameHeader;
    }
}

