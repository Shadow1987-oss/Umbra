package gg.umbra.ui.click.frame.impl;

import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderActionClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader d;
    final HackModule E;

    public ModuleCategoryFrameHeaderActionClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, HackModule mod) {
        this.d = moduleCategoryFrameHeader;
        this.E = mod;
    }

    @Override
    public void onPrimaryClick() {
        ModuleCategoryFrameHeader.e(this.d).G(this.E);
        ModuleCategoryFrameHeader.e(this.d).W(this.E).expandValueComponents();
        ModuleCategoryFrameHeader.e(this.d).l$src$V$1mibm4x();
    }
}
