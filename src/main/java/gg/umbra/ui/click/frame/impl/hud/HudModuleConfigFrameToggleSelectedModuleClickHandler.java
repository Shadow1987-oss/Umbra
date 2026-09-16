package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.component.GuiClickListener;

class HudModuleConfigFrameToggleSelectedModuleClickHandler
implements GuiClickListener {
    private final HudModuleConfigFrame configFrame;

    @Override
    public void onPrimaryClick() {
        HudModule hudModule = this.configFrame.getSelectedModule();
        hudModule.setFavorite(!hudModule.isFavorite());
        if (ClientSettings.getFrame(HudModuleSelectorFrame.class).getSelectedGroup() == HudModuleGroup.FAVORITE) {
            ClientSettings.getFrame(HudModuleSelectorFrame.class).getModuleListPanel().refreshModules();
        }
    }

    HudModuleConfigFrameToggleSelectedModuleClickHandler(HudModuleConfigFrame hudModuleConfigFrame) {
        this.configFrame = hudModuleConfigFrame;
    }

}
