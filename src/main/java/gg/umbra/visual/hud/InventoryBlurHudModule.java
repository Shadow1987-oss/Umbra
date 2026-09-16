package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventGuiOpen;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.render.ShaderGroupRenderStateManager;

public class InventoryBlurHudModule
extends HudModule {
    @Override
    public String getId() {
        return "inventoryblur";
    }

    public InventoryBlurHudModule() {
        super("Inventory Blur", HudModuleGroup.GAME, "inventory_blur");
        this.setSuffix("Blurs the background while in an inventory");
    }


    @Listen
    public void onGuiOpen(EventGuiOpen event) {
        if (event.getGuiScreen().isNull() || event.getGuiScreen().isInstance(MappedClasses.qo) || event.getGuiScreen().isInstance(MappedClasses.Fl) || MappedClasses.zL != null && event.getGuiScreen().isInstance(MappedClasses.zL)) {
            ShaderGroupRenderStateManager.getInstance().disable();
            return;
        }
        ShaderGroupRenderStateManager.getInstance().enable();
    }
}

