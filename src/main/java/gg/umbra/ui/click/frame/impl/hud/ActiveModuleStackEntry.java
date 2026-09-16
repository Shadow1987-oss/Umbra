package gg.umbra.ui.click.frame.impl.hud;

import gg.umbra.module.HackModule;
import gg.umbra.module.HackDisplayInfo;

public class ActiveModuleStackEntry {
    public final HackModule module;
    public final HackDisplayInfo displayInfo;

    public ActiveModuleStackEntry(HackModule mod, HackDisplayInfo moduleDisplayInfo) {
        this.module = mod;
        this.displayInfo = moduleDisplayInfo;
    }
}
