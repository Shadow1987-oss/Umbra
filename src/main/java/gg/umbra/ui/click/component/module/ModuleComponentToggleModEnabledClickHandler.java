package gg.umbra.ui.click.component.module;

import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiClickListener;

class ModuleComponentToggleModEnabledClickHandler
implements GuiClickListener {
    final HackModule module;
    final ModuleComponent owner;


    @Override
    public void onPrimaryClick() {
        this.module.K(!this.module.isFavorite());
    }

    ModuleComponentToggleModEnabledClickHandler(ModuleComponent moduleComponent, HackModule mod) {
        this.owner = moduleComponent;
        this.module = mod;
    }
}

