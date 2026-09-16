package gg.umbra.ui.click.frame;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.SettingsSubpageFrame;

public class SettingsSubpageFrameRefreshClickHandler
implements GuiClickListener {
    final SettingsSubpageFrame C;

    public SettingsSubpageFrameRefreshClickHandler(SettingsSubpageFrame settingsSubpageFrame) {
        this.C = settingsSubpageFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.C.w();
    }
}
