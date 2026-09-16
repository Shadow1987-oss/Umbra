package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.click.ClickButton;
import gg.umbra.click.ClickEngine;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.Animations;
import gg.umbra.rotation.RotationManager;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.unmap.ModeOption;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.Packet;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import java.util.Arrays;

public class ClickerLeft
extends AutoClicker {
    private boolean wasClicking = false;
    private final ToggleSetting jitter;
    private final ModeOption normalMode;
    private final ModeOption extraPlusMode;
    private final ItemFilterList blockBreakItems;
    private final ModeOption extraMode;
    private final RandomRangeSetting cps;
    private final ToggleSetting breakBlocks;
    private final ToggleSetting triggerMode;
    private final ItemFilterList itemWhitelist;
    private final ToggleSetting breakBlocksWhitelist;
    private final ToggleSetting limitItems;
    private final ToggleSetting holdToClick = ToggleSetting.create(this, "Hold to click", true);
    private final TimerUtil breakBlockTimer;
    private boolean blocked = false;
    private final RandomRangeSetting breakBlocksDelay;
    private final OptionSetting randomization;

    @Override
    public boolean shouldSimulateBlockHit(ClickEngine clickEngine, EntityPlayerSP player) {
        Animations animations = Umbra.INSTANCE.getHackManager().getMod(Animations.class);
        if (Packet.A()) {
            boolean animationsEnabled = animations.shouldBlock();
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
            return animationsEnabled;
        }
        return animations != null && animations.shouldBlock();
    }

    @Override
    public boolean isTriggerModeEnabled() {
        return this.triggerMode.getEffectiveValue();
    }

    private boolean computeBlocked() {
        if (!ClientSettings.INSTANCE.isInputEnabled()) {
            return true;
        }
        if (!InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            return true;
        }
        if (SharedModuleControlClaims.mouseButtons.isLocked()) {
            return true;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return true;
        }
        if (!this.shouldAllowClick(player)) {
            if (gg.umbra.config.ClientSettings.isAttackButtonDown()) {
                if (!Minecraft.gameSettings().F().isKeyDown()) {
                    // empty if block
                }
                this.suppressNextRelease = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isHitSelectActive() {
        TargetSelect hitSelect = Umbra.INSTANCE.getHackManager().getMod(TargetSelect.class);
        if (hitSelect == null) {
            return false;
        }
        return hitSelect.isEnabled() && hitSelect.isRightClickCancelActive();
    }

    public boolean shouldAllowClick(EntityPlayerSP player) {
        if (!gg.umbra.config.ClientSettings.isAttackButtonDown()) {
            this.breakBlockTimer.reset();
        }
        if (this.breakBlocks.getEffectiveValue().booleanValue() && this.breakBlockTimer.hasTimeElapsed((long)this.breakBlocksDelay.getRandomRangeSetting())) {
            if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
                return true;
            }
            if (this.breakBlocksWhitelist.getEffectiveValue().booleanValue() && !this.blockBreakItems.matches(player.getHeldItemHand())) {
                return true;
            }
            RayTraceResult rayTraceResult = RotationManager.INSTANCE.getNormalReachRayTrace();
            if (rayTraceResult.isNotNull() && rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.block())) {
                return false;
            }
            this.breakBlockTimer.reset();
        }
        return true;
    }


    @Override
    public boolean isClickCycleBlocked() {
        return this.blocked;
    }

    @Listen
    public void updateBlockedState(EventPreTick eventPreTick) {
        this.blocked = this.computeBlocked();
        ClickEngine clickEngine = this.getClickEngine();
        if (this.blocked && InputEventDispatcher.getInstance().getFocusState().isFocused() && ClientSettings.INSTANCE.inputEnabled && Minecraft.currentScreen().isNull() && clickEngine.isActivationHeld() && !Minecraft.gameSettings().F().isKeyDown() && !this.wasClicking) {
            this.wasClicking = true;
            clickEngine.pressClickButton();
        } else {
            this.wasClicking = false;
        }
    }

    @Override
    public String getDetailedSuffix() {
        return this.cps.getDisplayValue() + "cps";
    }

    @Override
    public String getId() {
        return "autoclicker";
    }

    public ClickerLeft() {
        super("AutoClicker");
        this.normalMode = new ModeOption("Normal");
        this.extraMode = new ModeOption("Extra");
        this.extraPlusMode = new ModeOption("Extra+");
        this.randomization = OptionSetting.create((Object)this, "Randomization", this.extraMode, this.normalMode, this.extraMode, this.extraPlusMode);
        this.jitter = ToggleSetting.create(this, "Jitter", false);
        this.cps = RandomRangeSetting.create(this, "CPS", "#.#", "", 1.0, 6.0, 13.0, 20.0);
        this.limitItems = ToggleSetting.create(this, "Limit items", false);
        this.itemWhitelist = ItemFilterList.create(this, "autoclicker-allowed-items", "Item whitelist", ItemFilterList.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
        this.triggerMode = ToggleSetting.create(this, "Trigger mode", false, "Only clicks while hovering an entity");
        this.breakBlocks = ToggleSetting.create(this, "Break blocks", false);
        this.breakBlocksDelay = RandomRangeSetting.create(this, "Break blocks delay", "#", "", 0.0, 0.0, 10.0, 2000.0);
        this.breakBlocksWhitelist = ToggleSetting.create(this, "Break blocks whitelist", false);
        this.blockBreakItems = ItemFilterList.create(this, "autoclicker-blockbreak-items", "Items", ItemFilterList.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("pickaxes"), new ItemLimitData("shovels")));
        this.breakBlockTimer = new TimerUtil();
        this.limitItems.addDependentValues(this.itemWhitelist);
        this.limitItems.setCompactListValue(this.itemWhitelist);
        this.breakBlocks.addDependentValues(this.breakBlocksDelay, this.breakBlocksWhitelist);
        this.breakBlocksWhitelist.setCompactListValue(this.blockBreakItems);
        this.breakBlocksWhitelist.addDependentValues(this.blockBreakItems);
        this.addValue(this.holdToClick, this.triggerMode, this.breakBlocks, this.breakBlocksDelay, this.breakBlocksWhitelist, this.blockBreakItems, this.cps, this.randomization, this.jitter, this.limitItems, this.itemWhitelist);
        ClickEngine clickEngine = new ClickEngine(ClickButton.LEFT, this.cps, this.limitItems,
                this.itemWhitelist, this.holdToClick, this.randomization, this.jitter, this);
        this.setClickEngine(clickEngine);
        this.cps.setMaximumFractionDigits(0);
    }
}
