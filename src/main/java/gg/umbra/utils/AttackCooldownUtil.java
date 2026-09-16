package gg.umbra.utils;

import gg.umbra.event.EventListener;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;

public class AttackCooldownUtil
implements EventListener {
    private static float lastCooldownStrength;

    public static boolean isAttackReady(float partialTicks) {
        if (ForgeVersion.MC_1_12_2.v()) {
            return true;
        }
        float cooldownStrength = Minecraft.a_xH_J().getCooledAttackStrength(partialTicks);
        boolean fullyCooledDown = cooldownStrength == 1.0f;
        return fullyCooledDown;
    }

}

