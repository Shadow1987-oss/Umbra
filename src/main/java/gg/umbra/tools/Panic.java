package gg.umbra.tools;

import gg.umbra.Umbra;
import gg.umbra.module.HackModule;
import gg.umbra.module.UtilityHack;
import gg.umbra.value.ToggleSetting;
import java.util.ArrayList;
import java.util.List;

public class Panic
extends UtilityHack {
    private boolean reEnableActive = false;
    private final ToggleSetting reEnable = ToggleSetting.create(this, "Re-enable", false, "Re-enables all previously enabled modules upon pressing bind a second time");
    private final List<HackModule> disabledMods = new ArrayList<HackModule>();

    public Panic() {
        super("Panic", "Disables all currently enabled modules");
        this.setDefaultVisibility(false);
        this.addValue(this.reEnable);
    }


    @Override
    public void U(HackModule mod) {
        if (mod != this) {
            this.reEnableActive = false;
            this.disabledMods.clear();
        }
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        if (this.reEnable.getEffectiveValue().booleanValue()) {
            this.reEnableActive = !this.reEnableActive;
            if (!this.reEnableActive) {
                for (HackModule mod : this.disabledMods) {
                    mod.setEnabled(true);
                }
                this.disabledMods.clear();
                return;
            }
        } else {
            this.reEnableActive = false;
        }
        for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
            if (!mod.isEnabled() || mod == this) continue;
            mod.setEnabled(false);
            if (!this.reEnable.getEffectiveValue().booleanValue()) continue;
            this.disabledMods.add(mod);
        }
    }
}

