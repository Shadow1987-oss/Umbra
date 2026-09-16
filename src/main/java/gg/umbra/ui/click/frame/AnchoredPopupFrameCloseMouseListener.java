package gg.umbra.ui.click.frame;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.GuiMouseListener;
import gg.umbra.ui.click.MouseClickButton;
import gg.umbra.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameCloseMouseListener
implements GuiMouseListener {
    final AnchoredPopupFrame O;


    AnchoredPopupFrameCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame) {
        this.O = anchoredPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton uA) {
        ClientSettings.removePopup(this.O);
        Runnable runnable = AnchoredPopupFrame.b(this.O);
        if (runnable != null) {
            runnable.run();
        }
    }
}

