package gg.umbra.input;

import gg.umbra.event.impl.SyntheticAttackRequestEvent;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.module.HackModule;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;

public class AttackKeyController {
    public static void releaseAttackKey() {
        KeyBinding attackKey = Minecraft.a_w3_0_S().F();
        KeyBindingHelper.updateKeyBinding(attackKey, false, false);
    }


    public static boolean requestSyntheticAttack(HackModule module) {
        SyntheticAttackRequestEvent event = new SyntheticAttackRequestEvent(module);
        if (event.fire()) {
            return false;
        }
        AttackKeyController.pressAttackKey();
        return true;
    }

    public static void pressAttackKey() {
        KeyBindingHelper.updateKeyBinding(Minecraft.a_w3_0_S().F(), true, true);
    }
}

