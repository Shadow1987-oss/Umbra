package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.OptionSetting;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.WorldClient;

public class WeatherChangerHudModule
extends HudModule {
    private float savedPreviousRainStrength;
    public final OptionSetting weatherMode;
    private float savedThunderStrength;
    private final ModeOption rainingOption;
    private float savedPreviousThunderStrength;
    private float savedRainStrength;
    private final ModeOption clearOption = new ModeOption("Clear");

    @Listen
    public void onPreRenderTick(EventPreRenderTick event) {
        WorldClient worldClient = event.getWorld();
        if (worldClient.isNotNull()) {
            if (((ModeSelection)this.weatherMode.getValue()).equals(this.clearOption)) {
                worldClient.T(0.0f);
                worldClient.o(0.0f);
                worldClient.f(0.0f);
                worldClient.g(0.0f);
            } else {
                worldClient.T(1.0f);
                worldClient.o(1.0f);
            }
        }
    }

    @Override
    public void onEnable() {
        if (Minecraft.theWorld().isNotNull()) {
            WorldClient worldClient = Minecraft.theWorld();
            this.savedRainStrength = worldClient.n();
            this.savedPreviousRainStrength = worldClient.N();
            this.savedThunderStrength = worldClient.y();
            this.savedPreviousThunderStrength = worldClient.V();
        }
    }

    @Override
    public String getId() {
        return "weather";
    }

    public WeatherChangerHudModule() {
        super("Weather", HudModuleGroup.GAME, "weather");
        this.rainingOption = new ModeOption("Raining");
        this.weatherMode = OptionSetting.create((Object)this, "Weather", this.clearOption, this.clearOption, this.rainingOption);
        this.addValue(this.weatherMode);
        this.setSuffix("Change the weather");
    }

    @Override
    public void onDisable() {
        if (Minecraft.theWorld().isNotNull()) {
            WorldClient worldClient = Minecraft.theWorld();
            worldClient.T(this.savedRainStrength);
            worldClient.o(this.savedPreviousRainStrength);
            worldClient.f(this.savedThunderStrength);
            worldClient.g(this.savedPreviousThunderStrength);
        }
    }

}

