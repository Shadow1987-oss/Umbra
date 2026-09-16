package gg.umbra.ui.click.frame.impl;

import gg.umbra.Umbra;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrame;
import gg.umbra.ui.click.frame.impl.ModuleSearchFrameHeader;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.StringUtils;
import gg.umbra.wrapper.impl.Minecraft;

public class ModuleSearchFrame
extends ModuleCategoryFrame {
    private ModuleSearchFrameHeader Rt;

    public void D(String string) {
        this.removeMarkedChildren();
        ClientSettings.activeTooltips = null;
        if (string == null || string.length() < 1) {
            return;
        }
        for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
            if (mod.getCategory().equals(Category.NONE)) continue;
            String string2 = StringUtils.y(mod.getName());
            String string3 = StringUtils.y(string);
            if (mod.getCategory().equals(Category.OTHER) ? !string2.equals(string3) : !string2.contains(string3)) continue;
            ModuleComponent moduleComponent = new ModuleComponent(this, mod);
            this.h(moduleComponent, new Object[0]);
            moduleComponent.buildValueComponents();
        }
    }

    @Override
    public boolean V$src$Z$1xhop3l() {
        return ((ModeSelection)ClientSettings.INSTANCE.searchBarStyle.getValue()).equals(ClientSettings.INSTANCE.floatingSearchBarMode) || ClientSettings.INSTANCE.showLegitMode.getEffectiveValue() != false;
    }

    @Override
    public void w() {
    }

    @Override
    public String getName() {
        return "ModuleSearch";
    }

    public ModuleSearchFrame() {
        super(Category.NONE);
        this.setDisabledOverlayColor(ModuleSearchFrame.J.r);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.I2 = false;
        this.Rt = new ModuleSearchFrameHeader(this);
        this.Y(this.Rt);
    }

    public ModuleSearchFrameHeader n$src$Lgg_umbra_ui_click_frame_impl_ModuleSearchFrameHe$xia8v2() {
        return this.Rt;
    }

    public void p() {
        this.M((double)Minecraft.J() / (4.0 * Umbra.INSTANCE.getClientSettings().getGuiScaleFactor()) - this.A() / 2.0, 7.0);
    }


    @Override
    public void v() {
    }

    @Override
    public void Y() {
        // Always re-center: this frame is a fixed UI element (not draggable),
        // so a stale restored position (e.g. from autosave) must not stick.
        this.p();
    }

    @Override
    public void V() {
    }
}

