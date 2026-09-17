package gg.umbra.tools;

import gg.umbra.config.ClientSettings;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;

public class AutoWalk
extends HackModule {
    public AutoWalk() {
        super("AutoWalk", -1963991869, Category.UTILITY, "Automatically holds the forward key");
    }

    @Override
    public String getId() {
        return "autowalk";
    }

    private KeyBinding getForwardKey() {
        return Minecraft.gameSettings().Y();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        KeyBinding forwardKey = this.getForwardKey();
        if (forwardKey != null) {
            KeyBinding.setKeyBindState(forwardKey, true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        KeyBinding forwardKey = this.getForwardKey();
        if (forwardKey != null) {
            KeyBinding.setKeyBindState(forwardKey, ClientSettings.isPhysicalKeyDown(forwardKey));
        }
    }
}
