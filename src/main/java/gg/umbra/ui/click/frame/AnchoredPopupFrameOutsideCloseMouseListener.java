package gg.umbra.ui.click.frame;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameOutsideCloseMouseListener
implements GuiMouseListener {
    final GuiComponent J;
    final AnchoredPopupFrame q;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.q.w$src$Z$e457mb() && !this.J.w$src$Z$e457mb()) {
            ClientSettings.removePopup(this.q);
            Runnable runnable = AnchoredPopupFrame.b(this.q);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    AnchoredPopupFrameOutsideCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame, GuiComponent guiComponent) {
        this.q = anchoredPopupFrame;
        this.J = guiComponent;
    }

}

