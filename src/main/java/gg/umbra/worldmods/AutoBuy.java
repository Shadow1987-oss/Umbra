package gg.umbra.worldmods;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.OptionalItemFilter;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.GuiChest;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.Arrays;

public class AutoBuy
extends HackModule {
    private final SliderSetting delay;
    private final ToggleSetting closeAfter;
    private final OptionalItemFilter buySlots;
    private final TimerUtil clickTimer = new TimerUtil();
    private final ArrayList<Integer> pendingSlots = new ArrayList();
    private boolean buying = false;

    public AutoBuy() {
        super("AutoBuy", -483965818, Category.WORLD, "Clicks your configured shop slots for you (BedWars/SkyWars). Slot numbers depend on the server's shop layout.");
        this.delay = SliderSetting.createWithDescription(this, "Click delay", "#", "ms", 25.0, 200.0, 1000.0, "Delay between each purchase click.");
        this.closeAfter = ToggleSetting.create(this, "Close after", true, "Close the shop after buying everything.");
        this.buySlots = OptionalItemFilter.createWithDescription(this, "autobuy-slots", "Buy Slots", "Slot numbers to click in the shop", OptionalItemFilter.NEUTRAL_LIST_COLOR, Arrays.asList("0", "1", "2"));
        this.addValue(this.delay, this.closeAfter, this.buySlots);
    }

    @Override
    public String getId() {
        return "autobuy";
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull() || Minecraft.currentScreen().isNull() || !Minecraft.currentScreen().isInstance(MappedClasses.qs)) {
            this.buying = false;
            this.pendingSlots.clear();
            return;
        }
        if (!this.buying) {
            this.buying = true;
            this.pendingSlots.clear();
            for (String slotString : this.buySlots.getEnabledValues()) {
                try {
                    this.pendingSlots.add(Integer.valueOf(Integer.parseInt(slotString.trim())));
                } catch (NumberFormatException numberFormatException) {
                    // ignore non-numeric entries
                }
            }
            this.clickTimer.reset();
            return;
        }
        if (!this.pendingSlots.isEmpty()) {
            if (!this.clickTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
                return;
            }
            GuiChest guiChest = new GuiChest(Minecraft.currentScreen().getObject());
            int slotIndex = this.pendingSlots.get(0);
            Minecraft.playerController().O(guiChest.getInventorySlots().getWindowId(), slotIndex, 0, 0, Minecraft.thePlayer());
            this.clickTimer.reset();
            this.pendingSlots.remove(0);
            return;
        }
        if (this.closeAfter.getEffectiveValue().booleanValue() && Minecraft.currentScreen().isNotNull()) {
            Minecraft.thePlayer().Z$src$V$1ie832h();
        }
        this.buying = false;
    }
}
