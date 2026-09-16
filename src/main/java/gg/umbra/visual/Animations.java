package gg.umbra.visual;

import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.visual.animations.AnimationsBlockingState;
import gg.umbra.visual.animations.AnimationsMode;
import gg.umbra.visual.animations.DamageResponsiveAnimationsMode;
import gg.umbra.visual.animations.LegacyBlockingPacketBufferedAnimationsMode;
import gg.umbra.visual.animations.SwordUseMouseGuardAnimationsMode;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.SubHackValue;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.Minecraft;

public class Animations
extends HackModule {
    private static final long MODULE_COLOR = -1033271949203766542L;
    private final OptionSetting mode;
    private final SubHackValue<DamageResponsiveAnimationsMode> predictiveMode;
    private final SubHackValue<AnimationsBlockingState> manualMode = new AnimationsBlockingState(this, "Manual").getSelectionValue();
    private final SubHackValue<LegacyBlockingPacketBufferedAnimationsMode> lagMode;
    public final ToggleSetting requireMouseDown;
    public final ToggleSetting ignoreManualBlock;
    public final SliderSetting targetDistance;
    private final SubHackValue<AnimationsMode> automaticMode;
    public final SliderSetting targetAngle;

    public boolean isHoldingSword() {
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        return Minecraft.thePlayer().getHeldItemHand().isNotNull() && ItemStackScoreUtil.h(Minecraft.thePlayer().getHeldItemHand().getItem());
    }

    public boolean shouldBlock() {
        return this.isEnabled() && this.getActiveMode().shouldBlock();
    }


    public boolean requiresMouseDown() {
        return this.requireMouseDown.getEffectiveValue();
    }

    @Override
    public String getDetailedSuffix() {
        if (this.mode.getValue() == this.manualMode) {
            return this.manualMode.getInstance().getDetailedSuffix();
        }
        if (this.mode.getValue() == this.lagMode) {
            return this.lagMode.getInstance().getDetailedSuffix();
        }
        return this.getSimpleSuffix();
    }

    public EntityLivingBase findTarget(double angle, double distance) {
        EntityLivingBase target = RotationUtil.u(distance, angle / 2.0);
        if (target == null) {
            return null;
        }
        if (RotationUtil.o(Minecraft.thePlayer(), target, distance, angle / 2.0, true)) {
            return target;
        }
        return null;
    }

    @Override
    public String getId() {
        return "blockhit";
    }

    public Animations() {
        super("BlockHit", (int)MODULE_COLOR, Category.COMBAT, "Automatically blockhit");
        this.predictiveMode = new DamageResponsiveAnimationsMode(this, "Predict").getSelectionValue();
        this.automaticMode = new SwordUseMouseGuardAnimationsMode(this, "Auto").getSelectionValue();
        this.lagMode = new LegacyBlockingPacketBufferedAnimationsMode(this, "Lag").getSelectionValue();
        this.requireMouseDown = ToggleSetting.create(this, "Require mouse down", true, "Require block to be pressed to blockhit");
        this.ignoreManualBlock = ToggleSetting.create(this, "Ignore manual block", true, "Prevents manually blocking, useful for holding right click to activate");
        this.targetAngle = SliderSetting.createWithDescription(this, "Angle", "#", "", 0.0, 90.0, 360.0, "Max target angle to blockhit");
        this.targetDistance = SliderSetting.createWithDescription(this, "Distance", "#.#", "", 0.0, 5.0, 6.0, "Max target distance to blockhit");
        this.mode = OptionSetting.create((Object)this, "Mode", this.manualMode, this.manualMode, this.predictiveMode, this.automaticMode, this.lagMode);
        this.mode.setDescription("Manual: Blockhit based on your CPS\nPredict: Predicts when a player can hit you and blocks ahead of time\nAuto: Legacy auto mode from AutoClicker\nLag: Lags you after blocking to maximize server side block time\n");
        this.requireMouseDown.addDependentValues(this.ignoreManualBlock);
        this.mode.addModeDependentValues(this.lagMode, this.ignoreManualBlock);
        this.mode.addModeDependentValues(this.lagMode, this.targetAngle);
        this.mode.addModeDependentValues(this.lagMode, this.targetDistance);
        this.addValue(this.mode, this.requireMouseDown, this.ignoreManualBlock, this.targetAngle, this.targetDistance);
    }

    public AnimationsMode getActiveMode() {
        return (AnimationsMode)((SubHackValue)this.mode.getValue()).getInstance();
    }

    public boolean isBlocking() {
        return this.getActiveMode().isBlocking();
    }

    @Override
    public String getSimpleSuffix() {
        return this.mode.getDisplayValue();
    }

    public EntityLivingBase findDefaultTarget() {
        return this.findTarget(90.0, 5.0);
    }
}

