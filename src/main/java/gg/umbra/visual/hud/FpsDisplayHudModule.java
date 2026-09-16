package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.FpsDisplayHudFrame;

public class FpsDisplayHudModule
extends HudModule {
    @Override
    public String getId() {
        return "fps";
    }

    public FpsDisplayHudModule() {
        super("FPS", HudModuleGroup.HUD, "fps", FpsDisplayHudFrame.class);
        this.setSuffix("Shows your current frames per second");
    }
}
