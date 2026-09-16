package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventFogDensity;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;

public class NoFogHudModule
extends HudModule {
    @Listen
    public void onFogDensity(EventFogDensity event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        if (!player.h$src$Z$ftwoya()) {
            return;
        }
        event.setCancelled(true);
        if (ForgeVersion.MC_1_16_5.d()) {
            event.setDensity(0.01f);
        }
    }


    public NoFogHudModule() {
        super("Clear Water", HudModuleGroup.GAME, "clearwater");
        this.setSuffix("Makes water clear when under water");
    }
}

