package gg.umbra.ui.click.frame;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.FrameNavigationButtonComponent;

class FrameNavigationButtonClickListener
implements GuiClickListener {
    final String d;
    final FrameNavigationButtonComponent D;

    FrameNavigationButtonClickListener(FrameNavigationButtonComponent frameNavigationButtonComponent, String string) {
        this.D = frameNavigationButtonComponent;
        this.d = string;
    }


    @Override
    public void onPrimaryClick() {
        FrameNavigationButtonComponent frameNavigationButtonComponent = this.D;
        boolean bl = !FrameNavigationButtonComponent.K(this.D);
        FrameNavigationButtonComponent.u(frameNavigationButtonComponent, bl);
        FrameNavigationButtonComponent.d(this.D).J();
        ClientSettings.toggleCategoryFrame(this.d);
    }
}

