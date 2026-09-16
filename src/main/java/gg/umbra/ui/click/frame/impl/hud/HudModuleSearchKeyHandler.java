package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.ui.click.component.GuiKeyTypedListener;
import gg.umbra.ui.click.frame.impl.hud.HudModuleSearchBox;
import gg.umbra.ui.click.frame.impl.hud.HudModuleSelectorFrame;

public class HudModuleSearchKeyHandler
implements GuiKeyTypedListener {
    private final HudModuleSelectorFrame selectorFrame;
    private final HudModuleSearchBox searchBox;

    @Override
    public void onKeyTyped(char c, int n) {
        this.selectorFrame.setSearchQuery(this.searchBox.getInput().getText());
    }

    public HudModuleSearchKeyHandler(HudModuleSearchBox hudModuleSearchBox, HudModuleSelectorFrame hudModuleSelectorFrame) {
        this.searchBox = hudModuleSearchBox;
        this.selectorFrame = hudModuleSelectorFrame;
    }
}
