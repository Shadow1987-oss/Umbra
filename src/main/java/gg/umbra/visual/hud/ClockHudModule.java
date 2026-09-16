package gg.umbra.visual.hud;

import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.ui.click.frame.impl.hud.ClockHudFrame;
import gg.umbra.unmap.ModeOption;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;

public class ClockHudModule
extends HudModule {
    private final ModeOption digitalMode;
    public final OptionSetting clockType;
    public final ToggleSetting showDate;
    private final ModeOption analogMode;
    public final ToggleSetting use24HourTime = ToggleSetting.create(this, "24 Hour Time", false);

    @Override
    public String getId() {
        return "clock";
    }

    public ClockHudModule() {
        super("Clock", HudModuleGroup.HUD, "clock_mod", ClockHudFrame.class);
        this.showDate = ToggleSetting.create(this, "Show date", true);
        this.analogMode = new ModeOption("Analog");
        this.digitalMode = new ModeOption("Digital");
        this.clockType = OptionSetting.create((Object)this, "Clock Type", this.analogMode, this.analogMode, this.digitalMode);
        this.setSuffix("Draws a clock with the current real-world time");
        this.clockType.addModeDependentValues(this.digitalMode, this.showDate);
        this.addValue(this.clockType, this.showDate, this.use24HourTime);
    }
}

