package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MScreen;
import gg.umbra.wrapper.Wrapper;

public class Screen
extends Wrapper {
    private static int[] controlFlowState;

    public static void setControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public static int[] getControlFlowState() {
        return controlFlowState;
    }

    public Screen(Object screenHandle) {
        super(screenHandle);
    }

    static {
        if (Screen.getControlFlowState() != null) {
            Screen.setControlFlowState(new int[1]);
        }
    }

    public static ResourceLocation getMobEffectSprite(Holder holder) {
        Object spriteLocationHandle = Screen.umbraInstance.getMappings().screen.getMobEffectSprite(holder.getObject());
        return new ResourceLocation(spriteLocationHandle);
    }
}
