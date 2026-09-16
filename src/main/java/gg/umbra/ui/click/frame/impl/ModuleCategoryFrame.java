package gg.umbra.ui.click.frame.impl;

import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.module.ModuleDisplayScope;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.ui.click.frame.CollapsibleFrame;
import gg.umbra.ui.click.frame.FrameHeaderComponent;
import gg.umbra.ui.click.frame.OutlinedFrameBase;
import gg.umbra.utils.NameComparator;
import java.util.ArrayList;
import java.util.Collections;

public class ModuleCategoryFrame
extends OutlinedFrameBase
implements CollapsibleFrame {
    protected ModuleCategoryFrameHeader HF;
    private Category Hp;
    private static final String fb = "wrap";
    private boolean HU;
    private String HK;
    private HackModule HI;

    @Override
    public String getName() {
        return this.Hp.getName();
    }

    public HackModule N$src$Lgg_umbra_module_Mod_$1rbaf6a() {
        return this.HI;
    }

    public void s$src$V$1a2f6mi() {
        this.HF = new ModuleCategoryFrameHeader(this, this.Hp.getIconKey(), this.HK);
        this.Y(this.HF);
        ArrayList<HackModule> arrayList = new ArrayList<HackModule>(Umbra.INSTANCE.getHackManager().collectMods());
        Collections.sort(arrayList, new NameComparator());
        for (HackModule mod : arrayList) {
            if (!mod.getCategory().equals(this.Hp) || mod.getModuleDisplayScope() == ModuleDisplayScope.STANDALONE_ONLY) continue;
            ModuleComponent moduleComponent = new ModuleComponent(this, mod);
            this.h(moduleComponent, new Object[0]);
            moduleComponent.buildValueComponents();
        }
    }

    public void G(HackModule mod) {
    }


    @Override
    public void w() {
        if (this.HF == null) {
            return;
        }
        this.HU = !this.HU;
        this.HF.h();
    }

    public int A$src$I$wwnvku() {
        int n = 0;
        for (GuiComponent guiComponent : this.f()) {
            if (guiComponent instanceof ModuleComponent) {
                boolean bl = ((ModuleComponent)guiComponent).getModule().isVisible();
                ((ModuleComponent)guiComponent).setExpanded(false);
                if (!bl) {
                    ++n;
                }
                if (ClientSettings.moduleSearchActive) {
                    guiComponent.setVisible(true);
                    continue;
                }
                guiComponent.setVisible(bl);
                continue;
            }
            if (guiComponent instanceof FrameHeaderComponent) continue;
            guiComponent.setVisible(false);
        }
        this.P$src$V$i0cha4();
        return n;
    }

    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        this.G(null);
    }

    public String L$src$Ljava_lang_String_$ahld16() {
        return this.HK;
    }

    @Override
    public void c() {
        super.c();
    }

    public ModuleComponent W(HackModule mod) {
        for (GuiComponent guiComponent : this.f()) {
            if (!(guiComponent instanceof ModuleComponent) || !((ModuleComponent)guiComponent).getModule().equals(mod)) continue;
            return (ModuleComponent)guiComponent;
        }
        return null;
    }

    @Override
    public boolean q() {
        return this.HU;
    }

    public ModuleCategoryFrame(Category category) {
        this.HK = category.getName();
        this.Hp = category;
        if (category.equals(Category.NONE)) {
            return;
        }
        this.setDisabledOverlayColor(ModuleCategoryFrame.J.i);
        this.K(200.0);
        this.S(100.0);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M(false);
        this.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M(fb);
        this.s$src$V$1a2f6mi();
        this.setVisible(false);
    }

    public Category G$src$Lgg_umbra_module_Category_$qyt4o7() {
        return this.Hp;
    }
}
