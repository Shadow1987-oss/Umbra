package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.CompassHudFrame;

public class CompassHudModule
extends HudModule {
    @Override
    public String getId() {
        return "compass";
    }

    public CompassHudModule() {
        super("Compass", HudModuleGroup.HUD, "compass_active", CompassHudFrame.class);
        this.setSuffix("Shows a compass indicating your direction");
    }
}
