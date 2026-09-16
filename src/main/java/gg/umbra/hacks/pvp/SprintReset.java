package gg.umbra.hacks.pvp;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreAttack;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;

public class SprintReset
extends HackModule {
    private final SliderSetting chance = SliderSetting.createWithDescription(this, "Chance", "#", "%", 0.0, 90.0, 100.0, "Chance of WTapping when hitting a target");
    private final TimerUtil rePressTimer;
    private final SliderSetting releaseDelay = SliderSetting.create(this, "Release delay", "#", "", 0.0, 0.0, 500.0, 50.0, "Delay before releasing W key after hitting a target");
    private boolean releasePending;
    private final TimerUtil releaseTimer;
    private final SliderSetting rePressDelay = SliderSetting.create(this, "Re-press delay", "#", "", 0.0, 0.0, 500.0, 50.0, "Delay before re-pressing W key after releasing it");
    private final ToggleSetting selectHits = ToggleSetting.create(this, "Select hits", true, "Only WTap when the target is vulnerable");
    private boolean rePressPending;

    public SprintReset() {
        super("WTap", 0, Category.COMBAT);
        this.releaseTimer = new TimerUtil();
        this.rePressTimer = new TimerUtil();
        this.addValue(this.chance, this.releaseDelay, this.rePressDelay, this.selectHits);
        this.releaseDelay.setMaximumFractionDigits(0);
    }

    @Override
    public String getId() {
        return "wtap";
    }

    private void handleRePress() {
        if (this.rePressTimer.hasTimeElapsed(((Double) this.rePressDelay.getValue()).longValue())) {
            KeyBinding forwardKey = Minecraft.gameSettings().Y();
            if (ClientSettings.isPhysicalKeyDown(forwardKey)) {
                forwardKey.setPressed(true);
            }
            this.rePressPending = false;
        }
    }

    @Listen
    public void onPreAttack(EventPreAttack event) {
        if (Packet.h()) {
            if (!event.getTarget().isInstance(MappedClasses.lG)) {
                return;
            }
            if (this.releasePending || this.rePressPending) {
                return;
            }
            if (this.selectHits.getEffectiveValue() && event.getTarget().V$src$I$fk0dv5() > 14) {
                return;
            }
            if (this.shouldTrigger()) {
                this.releasePending = true;
                this.releaseTimer.reset();
                this.handleRelease();
            }
            return;
        }
        if (event.getTarget().isInstance(MappedClasses.lG)) {
            this.releasePending = true;
            this.releaseTimer.reset();
            this.handleRelease();
        }
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (Packet.A()) {
            if (Minecraft.currentScreen().isNull()) {
                this.handleRePress();
            }
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (this.releasePending) {
            this.handleRelease();
            return;
        }
        if (this.rePressPending) {
            this.handleRePress();
            return;
        }
    }

    private void handleRelease() {
        if (this.releaseTimer.hasTimeElapsed(((Double) this.releaseDelay.getValue()).longValue())) {
            KeyBinding forwardKey = Minecraft.gameSettings().Y();
            forwardKey.setPressed(false);
            this.releasePending = false;
            this.rePressTimer.reset();
            this.rePressPending = true;
        }
    }

    @Override
    public String getDetailedSuffix() {
        return this.releaseDelay.getDisplayValue();
    }

    public boolean shouldTrigger() {
        return (Double) this.chance.getValue() >= Math.random() * 100.0;
    }
}
