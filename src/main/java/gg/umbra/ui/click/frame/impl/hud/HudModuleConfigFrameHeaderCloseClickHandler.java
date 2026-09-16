package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.hud.HudModuleConfigFrame;
import gg.umbra.ui.click.frame.impl.hud.HudModuleConfigFrameHeaderComponent;

class HudModuleConfigFrameHeaderCloseClickHandler
implements GuiClickListener {
    private final HudModuleConfigFrame configFrame;

    @Override
    public void onPrimaryClick() {
        if (this.configFrame.isClosing()) {
            return;
        }
        if (this.configFrame.V$src$Z$1xhop3l()) {
            this.configFrame.beginClosing();
            this.configFrame.U();
        }
    }

    HudModuleConfigFrameHeaderCloseClickHandler(HudModuleConfigFrameHeaderComponent hudModuleConfigFrameHeaderComponent, HudModuleConfigFrame hudModuleConfigFrame) {
        this.configFrame = hudModuleConfigFrame;
    }

}

