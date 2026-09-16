package gg.umbra.config;

import gg.umbra.config.SettingsPayload;

public interface RefreshableSettingsPayload
extends SettingsPayload {
    @Override
    public void initializeDefaults();

    default public void refreshFromCurrentSettings() {
    }
}
