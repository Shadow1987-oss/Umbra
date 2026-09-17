package gg.umbra.tools;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.BlockUtil;
import gg.umbra.wrapper.impl.Block;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.WorldClient;

public class AutoSneak
extends HackModule {
    public AutoSneak() {
        super("AutoSneak", -1484975919, Category.UTILITY, "Holds sneak when you are about to walk off an edge.");
    }

    @Override
    public String getId() {
        return "autosneak";
    }

    private void setSneak(boolean sneak) {
        KeyBinding sneakKey = Minecraft.gameSettings().d$src$Lgg_umbra_wrapper_impl_KeyBinding_$adn2z0();
        if (sneakKey == null) {
            return;
        }
        if (sneak) {
            KeyBinding.setKeyBindState(sneakKey, true);
        } else {
            KeyBinding.setKeyBindState(sneakKey, ClientSettings.isPhysicalKeyDown(sneakKey));
        }
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        WorldClient world = event.getWorld();
        if (player.isNull() || world.isNull() || !player.b$src$Z$fqlxe4()) {
            return;
        }
        double dx = player.z() - player.f();
        double dz = player.h() - player.R();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 0.05) {
            this.setSneak(false);
            return;
        }
        double nx = dx / horizontal;
        double nz = dz / horizontal;
        double aheadX = player.z() + nx * 0.5;
        double aheadZ = player.h() + nz * 0.5;
        Block below = world.getBlockByPos((int) Math.floor(aheadX), (int) Math.floor(player.N()) - 1, (int) Math.floor(aheadZ));
        boolean shouldSneak = below.isNull() || !BlockUtil.b(below) || BlockUtil.J(below);
        this.setSneak(shouldSneak);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        KeyBinding sneakKey = Minecraft.gameSettings().d$src$Lgg_umbra_wrapper_impl_KeyBinding_$adn2z0();
        if (sneakKey != null) {
            KeyBinding.setKeyBindState(sneakKey, ClientSettings.isPhysicalKeyDown(sneakKey));
        }
    }
}
