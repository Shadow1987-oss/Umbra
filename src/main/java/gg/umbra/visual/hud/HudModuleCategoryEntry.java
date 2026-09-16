package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.HudModuleCategoryConfigFrame;

public class HudModuleCategoryEntry
extends HudModule {
    public HudModuleCategoryEntry() {
        super("Cooldown", HudModuleGroup.HUD, "cooldowns", HudModuleCategoryConfigFrame.class);
    }
}
