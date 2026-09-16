package gg.umbra.ui.click.component;

import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.frame.impl.main.ClickGuiSidecarPanelBase;
import java.awt.Point;

public class SelectableTextRowToggleClickListener
implements GuiMouseListener {
    private final ClickGuiSidecarPanelBase sidecar;


    @Override
    public void g(Point point, MouseClickButton button) {
        Runnable action = this.sidecar.getLeadingAction();
        if (button == MouseClickButton.LEFT_CLICK && action != null) {
            action.run();
        }
    }

    public SelectableTextRowToggleClickListener(ClickGuiSidecarPanelBase sidecar) {
        this.sidecar = sidecar;
    }
}

