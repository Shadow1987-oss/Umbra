package gg.umbra.ui.click.frame.impl;

import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderNextModuleClickHandler
implements GuiClickListener {
    final HackModule i;
    final ModuleCategoryFrameHeader U;

    public ModuleCategoryFrameHeaderNextModuleClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, HackModule mod) {
        this.U = moduleCategoryFrameHeader;
        this.i = mod;
    }

    @Override
    public void onPrimaryClick() {
        ModuleCategoryFrameHeader.e(this.U).G(this.i);
        ModuleCategoryFrameHeader.e(this.U).W(this.i).expandValueComponents();
        ModuleCategoryFrameHeader.e(this.U).l$src$V$1mibm4x();
    }
}
