package gg.umbra.ui.click.component.value;

import gg.umbra.config.ClientSettings;
import gg.umbra.ui.click.component.value.BooleanToggleComponent;
import gg.umbra.value.ToggleSetting;
import java.awt.Color;

public final class ClientSettingsEntityCheckDependentToggleComponent
extends BooleanToggleComponent {
    final ClientSettings clientSettings;

    @Override
    public boolean V$src$Z$1xhop3l() {
        return this.clientSettings.healthPrediction.getEffectiveValue();
    }

    public ClientSettingsEntityCheckDependentToggleComponent(ToggleSetting booleanValue, ClientSettings clientSettings) {
        super(booleanValue);
        this.clientSettings = clientSettings;
    }

    @Override
    public Color getDisabledOverlayColor() {
        return ClientSettingsEntityCheckDependentToggleComponent.J.r;
    }
}
