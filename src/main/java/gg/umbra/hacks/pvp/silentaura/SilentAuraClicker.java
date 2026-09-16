package gg.umbra.hacks.pvp.silentaura;

import gg.umbra.Umbra;
import gg.umbra.click.ClickButton;
import gg.umbra.click.ClickEngine;
import gg.umbra.module.Category;
import gg.umbra.hacks.pvp.AutoClicker;
import gg.umbra.hacks.pvp.SilentCombat;
import gg.umbra.visual.Animations;
import gg.umbra.value.ToggleSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;

public class SilentAuraClicker
extends AutoClicker {
    private static final String CLICKER_NAME = "auraClicker";
    private final SilentCombat silentAura;

    @Override
    public boolean isClickCycleBlocked() {
        if (!this.silentAura.isEnabled()) {
            return true;
        }
        return !this.silentAura.canClickAttack();
    }

    @Override
    public boolean shouldSimulateBlockHit(ClickEngine clickEngine, EntityPlayerSP player) {
        Animations animations = Umbra.INSTANCE.getHackManager().getMod(Animations.class);
        return animations != null && animations.shouldBlock();
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    public SilentAuraClicker(SilentCombat silentAura) {
        super(CLICKER_NAME, 0, Category.NONE);
        this.silentAura = silentAura;
        ClickEngine clickEngine = new ClickEngine(ClickButton.LEFT, silentAura.getAttackRate(), silentAura.getLimitToItems(), silentAura.getAllowedItems(), silentAura.getRequireMouseDown(), null, new ToggleSetting((Object)null, "", false), this);
        this.setClickEngine(clickEngine);
        this.setEnabled(true);
    }
}
