package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.PotionEffectsHudFrame;
import gg.umbra.value.ToggleSetting;

public class PotionEffectsHudModule
extends HudModule {
    public final ToggleSetting showPositiveEffects = ToggleSetting.create(this, "Show Positive Effects", true);
    public final ToggleSetting showNegativeEffects = ToggleSetting.create(this, "Show Negative Effects", true);

    public PotionEffectsHudModule() {
        super("Potion Status", HudModuleGroup.HUD, "potion_status", PotionEffectsHudFrame.class);
        this.setSuffix("Shows your currently active potion effects");
        this.addValue(this.showPositiveEffects, this.showNegativeEffects);
    }
}
