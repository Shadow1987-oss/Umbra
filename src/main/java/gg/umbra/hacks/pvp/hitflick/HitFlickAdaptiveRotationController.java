package gg.umbra.hacks.pvp.hitflick;

import gg.umbra.hacks.pvp.HitBypass;
import gg.umbra.hacks.pvp.silentaura.SilentAuraAdaptiveRotationEntry;
import gg.umbra.rotation.AdaptiveRotationController;

public class HitFlickAdaptiveRotationController
extends AdaptiveRotationController {
    private final HitBypass targetingModule;


    public HitFlickAdaptiveRotationController(HitBypass targetingModule) {
        this.targetingModule = targetingModule;
    }

    @Override
    public float getSpeed() {
        switch (SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[this.targetingModule.getRotationMode().ordinal()]) {
            case 1: {
                return this.targetingModule.getAttackRotationSpeed();
            }
            case 2: {
                return this.targetingModule.getFlickAwayRotationSpeed();
            }
        }
        return 48.0f;
    }
}

