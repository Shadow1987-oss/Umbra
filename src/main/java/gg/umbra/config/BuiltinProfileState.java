package gg.umbra.config;

import gg.umbra.hacks.exploits.HitRewind;
import gg.umbra.hacks.pvp.AutoAim;
import gg.umbra.hacks.pvp.ClickerLeft;
import gg.umbra.hacks.pvp.Sprint;
import gg.umbra.hacks.pvp.FallReset;
import gg.umbra.hacks.pvp.TargetSelect;
import gg.umbra.wrapper.impl.ForgeVersion;

public class BuiltinProfileState
extends BuiltinProfile {
    private static final String PROFILE_NAME = "Classic PVP";

    @Override
    protected void configureModules() {
        this.selectModule(AutoAim.class);
        this.selectModule(ClickerLeft.class);
        this.selectModule(FallReset.class);
        this.selectModule(TargetSelect.class);
        this.selectModule(Sprint.class);
        this.selectModule(HitRewind.class);
    }

    @Override
    public boolean isApplicable() {
        return !ForgeVersion.MC_1_21_0.d();
    }

    public BuiltinProfileState() {
        super(PROFILE_NAME);
    }

}

