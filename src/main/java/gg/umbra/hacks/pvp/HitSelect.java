package gg.umbra.hacks.pvp;

import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.hacks.pvp.wtap.WTapRightClickUseCancelMode;
import gg.umbra.hacks.pvp.wtap.WTapSprintResetMode;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.SubHackValue;

public class HitSelect
extends HackModule {
    private final SubHackValue<WTapRightClickUseCancelMode> rightClickUseCancel = new WTapRightClickUseCancelMode(this, "Pause").getSelectionValue();
    private final SliderSetting chance;
    private final SubHackValue<WTapSprintResetMode> sprintReset = new WTapSprintResetMode(this, "Active").getSelectionValue();
    private final OptionSetting mode = OptionSetting.create((Object)this, "Mode", this.rightClickUseCancel, this.rightClickUseCancel, this.sprintReset);

    @Override
    public String getDetailedSuffix() {
        return this.getSimpleSuffix() + " " + this.chance.getDisplayValue() + "%";
    }


    public HitSelect() {
        super("HitSelect", 82881230, Category.COMBAT, "");
        this.chance = SliderSetting.create(this, "Chance", "#", "%", 0.0, 90.0, 100.0);
        this.addValue(this.mode, this.chance);
        this.mode.setDescription("Mode");
        this.chance.setMaximumFractionDigits(0);
    }

    public boolean shouldTrigger() {
        return (Double)this.chance.java_lang_Object_K() >= Math.random() * 100.0;
    }

    public boolean isRightClickCancelActive() {
        if (!this.rightClickUseCancel.isSelected()) {
            return false;
        }
        return this.rightClickUseCancel.getInstance().isCancelActive();
    }

    @Override
    public String getSimpleSuffix() {
        return ((ModeSelection)this.mode.java_lang_Object_K()).getName();
    }
}