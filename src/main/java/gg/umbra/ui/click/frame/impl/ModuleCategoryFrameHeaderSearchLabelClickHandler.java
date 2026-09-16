package gg.umbra.ui.click.frame.impl;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderSearchLabelClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader r;


    @Override
    public void onPrimaryClick() {
        boolean bl;
        ClientSettings.moduleSearchActive = bl = !ClientSettings.moduleSearchActive;
        ClientSettings.refreshModuleCategoryHeaders();
    }

    public ModuleCategoryFrameHeaderSearchLabelClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader) {
        this.r = moduleCategoryFrameHeader;
    }
}

