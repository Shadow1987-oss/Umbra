package gg.umbra.visual.hud;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;

public class NoClickDelayHudModule
extends HudModule {
    public NoClickDelayHudModule() {
        super("NoClickDelay", HudModuleGroup.GAME, "no_click_delay2");
        this.setSuffix("Removes the click delay that normally occurs after missing an attack");
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (ForgeVersion.MC_1_8_9.d() && ClientSettings.isAttackButtonDown() && Minecraft.currentScreen().isNull()) {
            Minecraft.r(0);
        }
    }

}

