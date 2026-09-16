package gg.umbra.config;

import gg.umbra.hacks.exploits.AutoAnchor;
import gg.umbra.hacks.pvp.AutoAim;
import gg.umbra.hacks.pvp.AutoHit;
import gg.umbra.hacks.pvp.HotbarSwap;
import gg.umbra.hacks.pvp.ObsidianCracker;
import gg.umbra.hacks.pvp.Sprint;
import gg.umbra.tools.AutoTotem;
import gg.umbra.tools.WindCharge;
import gg.umbra.wrapper.impl.ForgeVersion;

public class Minecraft121BuiltinProfile
extends BuiltinProfile {
    private static final String PROFILE_NAME = "Modern PVP";

    @Override
    protected void configureModules() {
        this.selectModule(AutoHit.class);
        this.selectModule(AutoAim.class);
        this.selectModule(HotbarSwap.class);
        this.selectModule(Sprint.class);
        this.selectModule(ObsidianCracker.class);
        this.selectModule(AutoTotem.class);
        this.selectModule(AutoAnchor.class);
        this.selectModule(WindCharge.class);
    }

    public Minecraft121BuiltinProfile() {
        super(PROFILE_NAME);
    }

    @Override
    public boolean isApplicable() {
        return ForgeVersion.MC_1_21_0.d();
    }
}
