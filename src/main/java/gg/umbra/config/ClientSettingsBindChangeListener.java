package gg.umbra.config;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.unmap.BindChangeListener;

class ClientSettingsBindChangeListener
implements BindChangeListener {
    final ClientSettings clientSettings;

    ClientSettingsBindChangeListener(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public void onBindChanged() {
        Umbra.INSTANCE.getFriendManager().toggleCrosshairTarget();
    }
}
