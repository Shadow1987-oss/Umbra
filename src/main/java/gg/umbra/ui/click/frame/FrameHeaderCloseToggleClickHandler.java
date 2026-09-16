package gg.umbra.ui.click.frame;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.CloseableFrameHeaderComponent;
import gg.umbra.ui.click.frame.CollapsibleFrame;
import gg.umbra.ui.click.frame.Frame;

class FrameHeaderCloseToggleClickHandler
implements GuiClickListener {
    final Frame h;
    final CloseableFrameHeaderComponent x;

    FrameHeaderCloseToggleClickHandler(CloseableFrameHeaderComponent closeableFrameHeaderComponent, Frame frame) {
        this.x = closeableFrameHeaderComponent;
        this.h = frame;
    }

    @Override
    public void onPrimaryClick() {
        if (this.h instanceof CollapsibleFrame) {
            ((CollapsibleFrame)((Object)this.h)).w();
            CloseableFrameHeaderComponent.c(this.x, ((CollapsibleFrame)((Object)this.h)).q());
        }
    }

}

