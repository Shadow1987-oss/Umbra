package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Minecraft;

public class AutoTip
extends HackModule {
    private final SliderSetting interval;
    private final TimerUtil tipTimer = new TimerUtil();

    public AutoTip() {
        super("AutoTip", 0, Category.UTILITY, "Tips everyone with /tip all on a timer.");
        this.interval = SliderSetting.createWithDescription(this, "Interval", "#", "min", 1.0, 10.0, 60.0, "Minutes between tips.");
        this.addValue(this.interval);
    }

    @Override
    public String getId() {
        return "autotip";
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            return;
        }
        long intervalMillis = (long) (((Double) this.interval.getValue()).doubleValue() * 60000.0);
        if (!this.tipTimer.hasTimeElapsed(intervalMillis)) {
            return;
        }
        this.tipTimer.reset();
        Minecraft.thePlayer().sendChatMessage("/tip all");
    }
}
