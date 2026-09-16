package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreEntityUpdate;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.hacks.exploits.BridgeBuilder;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.value.ToggleSetting;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PotionRegistry;

public class Sprint
extends HackModule {
    private final ToggleSetting cancelInvis = ToggleSetting.create(this, "Cancel Invis", false, "Does not sprint when you are invisible.\nUseful to prevent sprint particles.");
    private BridgeBuilder scaffold;

    public Sprint() {
        super("Sprint", 0, Category.COMBAT, "Sets your sprinting to true.");
        this.addValue(this.cancelInvis);
    }

    @Override
    public String getId() {
        return "sprint";
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Minecraft.gameSettings().r(), false);
    }

    @Listen
    public void onPreEntityUpdate(EventPreEntityUpdate event) {
        if (this.scaffold == null) {
            this.scaffold = Umbra.INSTANCE.getHackManager().getMod(BridgeBuilder.class);
        }
        if (!Minecraft.currentScreen().isNull()) {
            return;
        }
        if (SharedModuleControlClaims.movementInput.isLocked()) {
            return;
        }
        if (this.cancelInvis.getEffectiveValue() && Minecraft.thePlayer().i(PotionRegistry.R)
                && !Minecraft.thePlayer().C$src$Lgg_umbra_wrapper_impl_ModelPlayer_$19uhx86().H()) {
            return;
        }
        KeyBinding sprintKey = Minecraft.gameSettings().r();
        boolean shouldStartSprinting = !this.scaffold.isActivelyScaffolding()
                && !Minecraft.thePlayer().B$src$Z$f90iek() && !Minecraft.thePlayer().r();
        if (!sprintKey.isKeyDown() && shouldStartSprinting) {
            KeyBinding.setKeyBindState(sprintKey, true);
        }
    }
}
