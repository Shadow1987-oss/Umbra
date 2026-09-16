package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.ArmorStatusHudFrame;
import gg.umbra.value.ToggleSetting;

public class ArmorStatusHudModule
extends HudModule {
    public final ToggleSetting compact = ToggleSetting.create(this, "Compact", false);

    public ArmorStatusHudModule() {
        super("Armor Status", HudModuleGroup.HUD, "armor_status", ArmorStatusHudFrame.class);
        this.setSuffix("Shows your currently equipped armor, and its durability");
        this.addValue(this.compact);
    }
}
