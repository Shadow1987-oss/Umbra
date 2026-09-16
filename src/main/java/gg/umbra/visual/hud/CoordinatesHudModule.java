package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.CoordinatesHudFrame;
import gg.umbra.unmap.ModeOption;
import gg.umbra.value.OptionSetting;

public class CoordinatesHudModule
extends HudModule {
    public final ModeOption verticalMode = new ModeOption("Vertical");
    public final ModeOption horizontalMode = new ModeOption("Horizontal");
    public final OptionSetting displayMode = OptionSetting.create((Object)this, "Display Type", this.horizontalMode, this.horizontalMode, this.verticalMode);

    public CoordinatesHudModule() {
        super("Coords", HudModuleGroup.HUD, "coords", CoordinatesHudFrame.class);
        this.setSuffix("Shows your current XYZ coordinates");
        this.addValue(this.displayMode);
    }
}
