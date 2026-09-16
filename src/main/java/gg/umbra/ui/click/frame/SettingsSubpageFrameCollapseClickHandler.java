package gg.umbra.ui.click.frame;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.SettingsSubpageFrame;

public class SettingsSubpageFrameCollapseClickHandler
implements GuiClickListener {
    final SettingsSubpageFrame y;

    @Override
    public void onPrimaryClick() {
        this.y.p();
    }

    public SettingsSubpageFrameCollapseClickHandler(SettingsSubpageFrame settingsSubpageFrame) {
        this.y = settingsSubpageFrame;
    }
}
