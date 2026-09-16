package gg.umbra.visual.hud;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.HudModuleGroup;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;

public class NoHurtDelayHudModule
extends HudModule {
    @Override
    public String getId() {
        return "nojumpdelay";
    }

    public NoHurtDelayHudModule() {
        super("NoJumpDelay", HudModuleGroup.GAME, "no_jump_delay");
        this.setSuffix("Removes the delay between jumps when hitting a block above you");
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        player.L(0);
    }
}
