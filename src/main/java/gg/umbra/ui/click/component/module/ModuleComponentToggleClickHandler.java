package gg.umbra.ui.click.component.module;

import gg.umbra.utils.RectData;
import gg.umbra.module.HackModule;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.utils.render.RenderUtils;

class ModuleComponentToggleClickHandler
implements GuiClickListener {
    final HackModule module;
    final ModuleComponent owner;

    @Override
    public void onSecondaryClick() {
        if (ClientSettings.moduleSearchActive) {
            return;
        }
        this.owner.getSettingsButton().dispatchPrimaryClick();
    }

    ModuleComponentToggleClickHandler(ModuleComponent moduleComponent, HackModule mod) {
        this.owner = moduleComponent;
        this.module = mod;
    }


    @Override
    public void onPrimaryClick() {
        if (ClientSettings.moduleSearchActive) {
            RectData toggleBounds = this.owner.getToggleBounds();
            if (!this.owner.isFavoriteMode() && toggleBounds != null && toggleBounds.Z(RenderUtils.h())) {
                this.module.setVisibility(!this.module.isVisible());
                if (this.module.isEnabled() && !this.module.isVisible()) {
                    this.module.setEnabled(false);
                }
                if (this.owner.isExpanded()) {
                    this.owner.setExpanded(false);
                }
                ClientSettings.refreshModuleCategoryHeaders();
            }
            return;
        }
        if (this.module.isRequiresBind()) {
            if (!this.module.getBind().hasValidBinding()) {
                this.owner.setStatusText("must be bound");
                this.owner.getBindInput().setHighlighted(true);
                return;
            }
            this.owner.setStatusText("use via bind");
            this.owner.getBindInput().setHighlighted(true);
            return;
        }
        this.module.setEnabled(!this.module.isEnabled(), true);
        if (!this.module.isVisible()) {
            this.module.setVisibility(true);
            ClientSettings.refreshModuleCategoryHeaders();
        }
    }
}
