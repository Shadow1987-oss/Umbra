package gg.umbra.ui.click.frame;

import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiClickListener;

public class FrameHeaderMainLayerClickHandler
implements GuiClickListener {
    @Override
    public void onPrimaryClick() {
        ClientSettings.INSTANCE.switchFrameStack(ClientSettings.mainStack);
    }
}
