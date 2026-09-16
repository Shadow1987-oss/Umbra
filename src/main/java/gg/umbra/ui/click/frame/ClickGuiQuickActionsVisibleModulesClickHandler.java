package gg.umbra.ui.click.frame;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.umbra.ui.click.frame.impl.VisibleModuleListFrame;

public class ClickGuiQuickActionsVisibleModulesClickHandler
implements GuiClickListener {
    final ClickGuiQuickActionsComponent v;

    @Override
    public void onPrimaryClick() {
        ClientSettings.showFrame(VisibleModuleListFrame.class);
    }

    public ClickGuiQuickActionsVisibleModulesClickHandler(ClickGuiQuickActionsComponent clickGuiQuickActionsComponent) {
        this.v = clickGuiQuickActionsComponent;
    }
}
