package gg.umbra.ui.click.component.module;

import gg.umbra.Umbra;
import gg.umbra.module.HackModule;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.component.module.ModuleComponent;
import gg.umbra.ui.click.frame.impl.ModuleCategoryFrame;

class ModuleComponentSelectModuleClickHandler
implements GuiClickListener {
    final HackModule module;
    final ModuleComponent owner;
    final ModuleCategoryFrame categoryFrame;

    ModuleComponentSelectModuleClickHandler(ModuleComponent moduleComponent, HackModule mod, ModuleCategoryFrame moduleCategoryFrame) {
        this.owner = moduleComponent;
        this.module = mod;
        this.categoryFrame = moduleCategoryFrame;
    }

    @Override
    public void onPrimaryClick() {
        Umbra.INSTANCE.getModuleProfileMetadataCodec().removeModule(this.module);
        this.categoryFrame.l$src$Z$193vdc5();
    }
}
