package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventWorldTime;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.WorldClient;

public class TimeChangerHudModule
extends HudModule {
    private long overriddenWorldTime;
    private final SliderSetting timeValue = SliderSetting.create((Object)this, "Time", "#", "hours", 0.0, 12.0, 24.0, 1.0);

    private void applyWorldTime(WorldClient worldClient, long time) {
        if (ForgeVersion.MC_1_16_5.d()) {
            worldClient.getWorldInfo().setGameTime(time);
            worldClient.getWorldInfo().setDayTime(time);
        } else {
            worldClient.i(time);
        }
    }


    @Listen
    public void onPreRenderTick(EventPreRenderTick event) {
        if (ForgeVersion.MC_1_8_9.L()) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        double hours = (Double)this.timeValue.getValue();
        if ((hours -= 6.0) < 0.0) {
            hours = 24.0 + hours;
        }
        this.applyWorldTime(event.getWorld(), Math.round(hours * 1000.0));
    }

    @Override
    public String getId() {
        return "timechanger";
    }

    public TimeChangerHudModule() {
        super("Time Changer", HudModuleGroup.GAME, "time_changer");
        this.setSuffix("Sets the in-game world time");
        this.addValue(this.timeValue);
    }

    @Listen
    public void onWorldTime(EventWorldTime event) {
        event.setWorldTime(this.overriddenWorldTime);
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (ForgeVersion.MC_1_8_9.A()) {
            return;
        }
        double hours = (Double)this.timeValue.getValue();
        if ((hours -= 6.0) < 0.0) {
            hours = 24.0 + hours;
        }
        this.overriddenWorldTime = Math.round(hours * 1000.0);
    }
}

