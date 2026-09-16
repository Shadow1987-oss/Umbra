package gg.umbra.hacks.pvp.silentaura;

import gg.umbra.hacks.pvp.silentaura.SilentAuraRotationMode;
import gg.umbra.Umbra;

public class SilentAuraAdaptiveRotationEntry {
    public static final int[] MODE_ORDINALS = new int[SilentAuraRotationMode.values().length];

    SilentAuraAdaptiveRotationEntry() {
    }

    static {
        try {
            SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[SilentAuraRotationMode.FLICKING_AWAY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
                Umbra.logThrowable(noSuchFieldError);
            }
        try {
            SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[SilentAuraRotationMode.ATTACKING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
                Umbra.logThrowable(noSuchFieldError);
            }
        try {
            SilentAuraAdaptiveRotationEntry.MODE_ORDINALS[SilentAuraRotationMode.IDLE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
                Umbra.logThrowable(noSuchFieldError);
            }
    }
}
