package gg.umbra.ui.click.frame.impl;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

class ClientSettingsSearchFrameHeaderSearchFocusClickHandler
implements GuiClickListener {
    final ClientSettingsSearchFrameHeader q;

    @Override
    public void onPrimaryClick() {
        this.q.focusSearchInput();
    }

    ClientSettingsSearchFrameHeaderSearchFocusClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        this.q = clientSettingsSearchFrameHeader;
    }
}
