package gg.umbra.ui.click.frame.impl.main;

import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.frame.impl.main.ClickGuiModulesSidecarPanel;
import java.awt.Point;

class ClickGuiModulesSidecarPrimaryMouseListener
implements GuiMouseListener {
    private final Runnable action;

    @Override
    public void g(Point point, MouseClickButton button) {
        this.action.run();
    }

    ClickGuiModulesSidecarPrimaryMouseListener(Runnable action) {
        this.action = action;
    }
}
