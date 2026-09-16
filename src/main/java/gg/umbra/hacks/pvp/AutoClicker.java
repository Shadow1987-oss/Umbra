package gg.umbra.hacks.pvp;

import gg.umbra.click.ClickButton;
import gg.umbra.click.ClickEngine;
import gg.umbra.click.ClickerWorker;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreRenderTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.rotation.RotationManager;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.utils.SleepUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GuiScreen;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import java.util.Random;

public class AutoClicker
extends HackModule {
    private long activationStartTime;
    private boolean activationWasHeld;
    protected boolean suppressNextRelease;
    private ClickEngine clickEngine;
    private final ClickerWorker clickerWorker = new ClickerWorker(this);

    public boolean isHitSelectActive() {
        return false;
    }

    public boolean shouldBlockClick(EntityPlayerSP player) {
        return false;
    }

    public boolean shouldSimulateBlockHit(ClickEngine clickEngine, EntityPlayerSP player) {
        return false;
    }

    public boolean isTriggerModeEnabled() {
        return false;
    }

    @Override
    public void onEnable() {
        this.clickerWorker.startOrResume();
    }

    @Override
    public void onDisable() {
        this.clickerWorker.pause();
    }


    public AutoClicker(String moduleName, int moduleId, Category category) {
        super(moduleName, moduleId, category, "");
    }

    public AutoClicker(String moduleName) {
        super(moduleName, 0, Category.COMBAT, "");
    }

    public void setClickEngine(ClickEngine clickEngine) {
        this.clickEngine = clickEngine;
    }

    public ClickEngine getClickEngine() {
        return this.clickEngine;
    }

    public boolean isClickCycleBlocked() {
        return false;
    }

    public boolean shouldDeferAttack(EntityPlayerSP player) {
        RayTraceResult rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace();
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (ClickEngine.getClickStateMarker() != null) {
            boolean triggerModeEnabled = this.isTriggerModeEnabled();
            GuiComponent.setLegacyComponentState(new GuiComponent[4]);
            return triggerModeEnabled;
        }

        boolean isEntityHit = rayTraceResult.isNotNull()
                && rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.entity());
        if (isEntityHit) {
            if (this.isTriggerModeEnabled() && ForgeVersion.MC_1_12_2.d()
                    && Minecraft.thePlayer().getCooledAttackStrength(0.5f) < 1.0f) {
                return true;
            }
            if (this.isHitSelectActive() && rayTraceResult.getEntity().isNotNull()
                    && player.b$src$Z$fqlxe4() && rayTraceResult.getEntity().V$src$I$fk0dv5() > 12) {
                return true;
            }
            return false;
        }

        if (this.isTriggerModeEnabled()) {
            if (guiScreen.isNull()) {
                return true;
            }
            if (ForgeVersion.MC_1_12_2.d()
                    && Minecraft.thePlayer().getCooledAttackStrength(0.5f) < 1.0f) {
                return true;
            }
        }
        return false;
    }

    public double getStartDelayMillis() {
        return 0.0;
    }

    @Listen
    public final void onTick(EventPreTick eventPreTick) {
        this.clickEngine.updateJitter(eventPreTick);
    }

    @Listen
    public final void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
        this.clickEngine.applyJitterRotation(eventPreRenderTick);
    }

    public void runClickCycle() {
        EntityPlayerSP player = Minecraft.thePlayer();
        GuiScreen guiScreen = Minecraft.currentScreen();
        if (guiScreen.isNotNull()) {
            return;
        }
        if (!InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            return;
        }
        if (!this.clickEngine.isActivationHeld()) {
            this.activationWasHeld = false;
            return;
        }
        if (!this.activationWasHeld) {
            this.activationWasHeld = true;
            this.activationStartTime = System.currentTimeMillis();
        }
        long activationDelayMillis = 50L + (long)this.getStartDelayMillis();
        if (System.currentTimeMillis() - this.activationStartTime < activationDelayMillis) {
            return;
        }
        if (!this.clickEngine.canClick(player)) {
            return;
        }
        if (this.shouldBlockClick(player)) {
            return;
        }

        boolean isHighCps = this.clickEngine.getCpsRange().getMinimumInt() > 20;
        if (isHighCps) {
            long clickDelayMillis = this.clickEngine.calculateNextClickDelay() - 5L;
            double delayScale = (double)(100L - Math.min(clickDelayMillis, 99L) + 45L) / 100.0;
            double holdBiasPercent = 40.0 * delayScale;
            Random random = new Random();
            double releaseFraction = ((double)(30 + random.nextInt() % 10) + holdBiasPercent) / 100.0;
            long holdDurationMillis = (long)((double)clickDelayMillis * (1.0 - releaseFraction));
            long releaseDurationMillis = (long)((double)clickDelayMillis * releaseFraction);
            if (!this.clickEngine.isActivationHeld()) {
                this.activationWasHeld = false;
                return;
            }
            if (this.clickEngine.getButton() == ClickButton.LEFT && this.shouldDeferAttack(player)) {
                return;
            }
            if (!Minecraft.a()) {
                return;
            }
            TimerUtil phaseTimer = new TimerUtil();
            holdDurationMillis = Math.max(0L, holdDurationMillis - phaseTimer.getLastMS());
            if (this.suppressNextRelease) {
                this.suppressNextRelease = false;
            }
            this.clickEngine.pressClickButton();
            boolean simulateBlockHit = this.shouldSimulateBlockHit(this.clickEngine, player);
            if (simulateBlockHit) {
                this.clickEngine.pressUseItem();
            }
            SleepUtil.sleep(holdDurationMillis);
            phaseTimer.reset();
            if (simulateBlockHit) {
                this.clickEngine.releaseUseItem();
            }
            if (!this.suppressNextRelease) {
                this.clickEngine.releaseClickButton();
            } else {
                this.suppressNextRelease = false;
            }
            releaseDurationMillis = Math.max(0L, releaseDurationMillis - phaseTimer.getLastMS());
            SleepUtil.sleep(releaseDurationMillis);
            if (GuiComponent.getLegacyComponentState() == null) {
                ClickEngine.setClickStateMarker(new GuiComponent[1]);
            }
            return;
        }
        long clickDelayMillis = this.clickEngine.calculateNextClickDelay() - 5L;
        if (clickDelayMillis - 50L <= 0L) {
            clickDelayMillis = 45L;
        }
        double delayScale = (double)(100L - Math.min(clickDelayMillis, 99L) + 45L) / 100.0;
        double holdBiasPercent = 40.0 * delayScale;
        Random random = new Random();
        double releaseFraction = ((double)(30 + random.nextInt() % 10) + holdBiasPercent) / 100.0;
        long holdDurationMillis = (long)((double)clickDelayMillis * (1.0 - releaseFraction));
        long releaseDurationMillis = (long)((double)clickDelayMillis * releaseFraction);
        if (!this.clickEngine.isActivationHeld()) {
            this.activationWasHeld = false;
            return;
        }
        if (this.clickEngine.getButton() == ClickButton.LEFT && this.shouldDeferAttack(player)) {
            return;
        }
        if (!Minecraft.a()) {
            return;
        }
        TimerUtil phaseTimer = new TimerUtil();
        if (this.suppressNextRelease) {
            this.suppressNextRelease = false;
        }
        this.clickEngine.pressClickButton();
        boolean simulateBlockHit = this.shouldSimulateBlockHit(this.clickEngine, player);
        if (simulateBlockHit) {
            this.clickEngine.pressUseItem();
        }
        SleepUtil.sleep(holdDurationMillis);
        phaseTimer.reset();
        if (simulateBlockHit) {
            this.clickEngine.releaseUseItem();
        }
        if (!this.suppressNextRelease) {
            this.clickEngine.releaseClickButton();
        } else {
            this.suppressNextRelease = false;
        }
        SleepUtil.sleep(releaseDurationMillis);
        if (GuiComponent.getLegacyComponentState() == null) {
            ClickEngine.setClickStateMarker(new GuiComponent[1]);
        }
    }
}
