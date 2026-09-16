package gg.umbra.visual.animations;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.module.HackModule;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.hacks.pvp.ClickerLeft;
import gg.umbra.hacks.pvp.SilentCombat;
import gg.umbra.visual.Animations;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.Minecraft;

public class AnimationsBlockingState
extends AnimationsMode {
    private boolean blocking = false;
    private long releaseTime;
    private final RandomRangeSetting chance = RandomRangeSetting.createWithDescription(this, "Chance", "#", "%", 0.0, 70.0, 90.0, 100.0, 1.0, "Chance that a click will blockhit\n(Blocks per second = Your CPS * Chance)");

    public AnimationsBlockingState(HackModule parent, String name) {
        super(parent, name);
        this.addValue(this.chance);
    }

    @Override
    public boolean shouldBlock() {
        if (!((Animations)this.getParent()).isHoldingSword()) {
            return false;
        }
        if (((Animations)this.getParent()).requiresMouseDown() && !ClientSettings.isUseItemButtonDown()) {
            return false;
        }
        return this.chance.getRandomRangeSetting() >= Math.random() * 100.0;
    }

    public void setBlocking(boolean blocking) {
        if (this.blocking != blocking) {
            this.blocking = blocking;
            this.releaseTime = 0L;
            Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362().setPressed(blocking);
        }
    }

    @Override
    public String getDetailedSuffix() {
        return this.chance.getDisplayValue();
    }

    @Override
    public boolean isBlocking() {
        return this.blocking;
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (this.isAutoClickerActive()) {
            return;
        }
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        boolean hurtTimeExpired = event.getThePlayer().c$src$I$15a9iwo() > AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
        boolean releaseTimeReached = this.releaseTime > 0L && System.currentTimeMillis() >= this.releaseTime;
        if (hurtTimeExpired || releaseTimeReached) {
            this.setBlocking(false);
            return;
        }
    }


    @Listen
    public void onMouseButton(EventMouseButton event) {
        if (!event.getButtonState()) {
            return;
        }
        if (this.isAutoClickerActive()) {
            return;
        }
        int buttonBinding = -100 + event.getButton();
        if (event.getButtonState() && buttonBinding == Minecraft.gameSettings().F().getKeyCode()) {
            if (!this.shouldBlock()) {
                return;
            }
            if (!this.blocking && !event.getThePlayer().o$src$Z$1iprrmi()) {
                this.setBlocking(true);
                this.releaseTime = System.currentTimeMillis() + 50L;
            }
        }
    }

    private boolean isAutoClickerActive() {
        ClickerLeft leftClicker = Umbra.INSTANCE.getHackManager().getMod(ClickerLeft.class);
        if (leftClicker.isEnabled()) {
            return true;
        }
        SilentCombat silentAura = Umbra.INSTANCE.getHackManager().getMod(SilentCombat.class);
        return silentAura.isEnabled();
    }
}
