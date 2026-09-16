package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.hud.HudModuleConfigFrame;

public class HudModuleConfigFrameCloseClickHandler
implements GuiClickListener {
    private final HudModuleConfigFrame configFrame;

    public HudModuleConfigFrameCloseClickHandler(HudModuleConfigFrame hudModuleConfigFrame) {
        this.configFrame = hudModuleConfigFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.configFrame.beginClosing();
    }
}
