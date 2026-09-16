package gg.umbra.ui.click.frame.impl;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrame;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderCollapseToggleClickHandler
implements GuiClickListener {
    final ModuleCategoryFrame o;
    final ModuleCategoryFrameHeader u;

    @Override
    public void onPrimaryClick() {
        this.o.w();
    }

    public ModuleCategoryFrameHeaderCollapseToggleClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, ModuleCategoryFrame moduleCategoryFrame) {
        this.u = moduleCategoryFrameHeader;
        this.o = moduleCategoryFrame;
    }
}
