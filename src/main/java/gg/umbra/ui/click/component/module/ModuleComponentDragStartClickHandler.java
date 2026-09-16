package gg.umbra.ui.click.component.module;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.MousePosition;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.utils.render.RenderUtils;

class ModuleComponentDragStartClickHandler
implements GuiClickListener {
    final ModuleComponent owner;

    ModuleComponentDragStartClickHandler(ModuleComponent owner) {
        this.owner = owner;
    }

    @Override
    public void onPrimaryClick() {
        MousePosition mousePosition = RenderUtils.h();
        this.owner.setLastDragMouseY(mousePosition.H);
        this.owner.setDragStartY(this.owner.double_n());
        this.owner.setDragging(true);
        ClientSettings.activeComponent = this.owner;
    }
}
