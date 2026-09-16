package gg.umbra.ui.click.component.value;

import gg.umbra.config.PublicProfileSettings;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.value.ToggleSetting;
import java.awt.Color;

public final class ClientSettingsPrimaryBooleanToggle
extends BooleanToggleComponent {
    final PublicProfileSettings publicProfileSettings;

    public ClientSettingsPrimaryBooleanToggle(ToggleSetting booleanValue, PublicProfileSettings publicProfileSettings) {
        super(booleanValue);
        this.publicProfileSettings = publicProfileSettings;
    }

    @Override
    public Color getDisabledOverlayColor() {
        return ClientSettingsPrimaryBooleanToggle.J.r;
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return this.publicProfileSettings.notifications.getEffectiveValue();
    }
}
