package gg.umbra.worldmods;

import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.worldmods.fakelag.FakeLagDelayedPacketMode;
import gg.umbra.worldmods.fakelag.FakeLagPacketDelaySubModule;
import gg.umbra.worldmods.fakelag.LegacyFakeLagCombatPacketQueueMode;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.SubHackValue;
import gg.umbra.wrapper.impl.ForgeVersion;

public class FakeLag
extends HackModule {
    private final SubHackValue repelMode;
    private final OptionSetting modeValue;
    public final SliderSetting delay;
    private final SubHackValue dynamicMode;
    private final SubHackValue latencyMode = new FakeLagDelayedPacketMode(this, "Latency").getSelectionValue();

    public FakeLag() {
        super("FakeLag", 0, Category.UTILITY, "Holds your packets back to look laggy.");
        this.dynamicMode = new FakeLagPacketDelaySubModule(this, "Dynamic").getSelectionValue();
        this.repelMode = new LegacyFakeLagCombatPacketQueueMode(this, "Repel").getSelectionValue();
        this.delay = SliderSetting.create((Object) this, "Delay", "#", "ms", 1.0, 100.0, 1000.0, 10.0);
        this.modeValue = ForgeVersion.MC_1_7_10.Y() ? OptionSetting.create((Object) this, "Mode", this.latencyMode, this.latencyMode, this.dynamicMode, this.repelMode) : OptionSetting.create((Object) this, "Mode", this.latencyMode, this.latencyMode, this.repelMode);
        this.addValue(this.modeValue, this.delay);
        this.delay.setMaximumFractionDigits(0);
    }

    @Override
    public String getId() {
        return "fakelag";
    }

    @Override
    public String getSimpleSuffix() {
        return this.modeValue.getDisplayValue();
    }

    @Override
    public String getDetailedSuffix() {
        SubHackValue subModuleValue = (SubHackValue) this.modeValue.getValue();
        return ((HackModule) subModuleValue.getInstance()).getDetailedSuffix();
    }
}
