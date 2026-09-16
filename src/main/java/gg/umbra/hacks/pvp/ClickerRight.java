package gg.umbra.hacks.pvp;

import gg.umbra.click.ClickButton;
import gg.umbra.click.ClickEngine;
import gg.umbra.hacks.pvp.AutoClicker;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.unmap.ModeOption;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumHand;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.ItemStack;

public class ClickerRight
extends AutoClicker {
    private final SliderSetting startDelay;
    private final ModeOption modeExtraPlus;
    private final ItemFilterList itemWhitelist;
    private final ToggleSetting useItemWhitelist;
    private final ToggleSetting jitter;
    private final ModeOption modeNormal;
    private final ToggleSetting holdToClick;
    private final ModeOption modeExtra;
    private final OptionSetting randomization;
    private final RandomRangeSetting cps = RandomRangeSetting.create(this, "CPS", "#.#", "", 1.0, 7.0, 13.0, 20.0);

    @Override
    public boolean shouldBlockClick(EntityPlayerSP player) {
        if (SharedModuleControlClaims.rightClickUse.isClaimed()) {
            return true;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            ItemStack mainHandItem = player.i(EnumHand.mainHand());
            ItemStack offHandItem = player.i(EnumHand.offHand());
            if (!this.itemWhitelist.matches(mainHandItem) && this.itemWhitelist.matches(offHandItem)
                    && this.isUsableItem(mainHandItem, player)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public double getStartDelayMillis() {
        return (Double)this.startDelay.getValue();
    }

    private boolean isUsableItem(ItemStack itemStack, EntityPlayerSP player) {
        return itemStack.isNotNull() && itemStack.getItem().isNotNull()
                && itemStack.getItem().I(itemStack, player) > 0;
    }

    @Override
    public String getDetailedSuffix() {
        return this.cps.getDisplayValue() + "cps";
    }

    @Override
    public String getId() {
        return "rightclicker";
    }

    public ClickerRight() {
        super("RightClicker");
        this.holdToClick = ToggleSetting.create(this, "Hold to Click", true);
        this.itemWhitelist = ItemFilterList.create(this, "autoclicker-allowed-items", "Item whitelist", ItemFilterList.ALLOW_LIST_COLOR, new ItemLimitData("blocks")).setIncludeOffhand(true);
        this.jitter = ToggleSetting.create(this, "Jitter", false);
        this.useItemWhitelist = ToggleSetting.create(this, "Use item whitelist", false);
        this.modeExtra = new ModeOption("Extra");
        this.modeExtraPlus = new ModeOption("Extra+");
        this.modeNormal = new ModeOption("Normal");
        this.randomization = OptionSetting.create((Object)this, "Randomization", this.modeExtraPlus, this.modeNormal, this.modeExtra, this.modeExtraPlus);
        this.startDelay = SliderSetting.create(this, "Start Delay", "#.#", "", 0.0, 0.0, 1000.0);
        this.useItemWhitelist.addDependentValues(this.itemWhitelist);
        this.addValue(this.cps, this.startDelay, this.randomization, this.jitter, this.useItemWhitelist, this.itemWhitelist);
        ClickEngine clickEngine = new ClickEngine(ClickButton.RIGHT, this.cps, this.useItemWhitelist,
                this.itemWhitelist, this.holdToClick, this.randomization, this.jitter, this);
        this.setClickEngine(clickEngine);
        this.cps.setMaximumFractionDigits(0);
    }
}

