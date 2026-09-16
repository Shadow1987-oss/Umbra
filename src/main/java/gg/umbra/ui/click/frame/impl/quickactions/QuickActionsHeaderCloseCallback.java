package gg.umbra.ui.click.frame.impl.quickactions;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.quickactions.QuickActionsFrame;

public class QuickActionsHeaderCloseCallback
implements GuiClickListener {
    final QuickActionsFrame u;

    @Override
    public void onPrimaryClick() {
        QuickActionsFrame.Y(this.u, 3);
    }

    public QuickActionsHeaderCloseCallback(QuickActionsFrame sz_02) {
        this.u = sz_02;
    }
}
