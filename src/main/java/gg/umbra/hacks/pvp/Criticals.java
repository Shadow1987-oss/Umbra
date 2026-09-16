package gg.umbra.hacks.pvp;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.config.ClientSettings;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;

public class Criticals
extends HackModule {
    public Criticals() {
        super("Criticals", 0, Category.COMBAT, "Jumps before attacking so your hits land as critical hits.\nWorks with KillAura and with manual clicks (best paired with an autoclicker).");
    }

    @Override
    public String getId() {
        return "criticals";
    }

    public static boolean canJump(EntityPlayerSP player) {
        if (player == null || player.isNull()) {
            return false;
        }
        if (!player.b$src$Z$fqlxe4()) {
            return false;
        }
        return !player.h$src$Z$ftwoya() && !player.Q$src$Z$fh9faz();
    }

    @Listen
    public void onPrePlayerTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (player == null || player.isNull()) {
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (!ClientSettings.isAttackButtonDown()) {
            return;
        }
        if (!Criticals.canJump(player)) {
            return;
        }
        player.k(0.42);
    }
}
