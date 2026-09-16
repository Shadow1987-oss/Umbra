package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.CPacketHeldItemChange;
import gg.umbra.wrapper.impl.DataComponentMap;
import gg.umbra.wrapper.impl.DataComponents;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;

public class AutoFood
extends HackModule {
    private final SliderSetting foodLevel;
    private final SliderSetting delay;
    private final ToggleSetting silent;
    private final TimerUtil eatTimer = new TimerUtil();

    public AutoFood() {
        super("AutoFood", 0, Category.UTILITY, "Eats from your hotbar when hunger runs low.");
        this.foodLevel = SliderSetting.create(this, "Food level", "#", "", 1.0, 10.0, 19.0, 1.0);
        this.delay = SliderSetting.createWithDescription(this, "Delay", "#", "ms", 500.0, 2000.0, 10000.0, "Cooldown between eating attempts.");
        this.silent = ToggleSetting.create(this, "Silent", true, "Uses packet switching so the server sees the food without a visible slot swap.");
        this.addValue(this.foodLevel, this.delay, this.silent);
    }

    @Override
    public String getId() {
        return "autofood";
    }

    private boolean isFood(ItemStack stack) {
        if (stack.isNull() || stack.getItem().isNull()) {
            return false;
        }
        Item item = stack.getItem();
        if (ForgeVersion.MC_1_20_6.d()) {
            DataComponentMap dataComponentMap = item.g();
            return dataComponentMap.V(DataComponents.d());
        }
        return item.isInstance(MappedClasses.ITEM_FOOD);
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull() || event.getWorld().isNull() || Minecraft.currentScreen().isNotNull()) {
            return;
        }
        if (player.h$src$Z$ftwoya() || player.l$src$Z$1io4duf()) {
            return;
        }
        if (player.Y$src$Lgg_umbra_wrapper_impl_FoodStats_$fakh1z().getFoodLevel() > ((Double) this.foodLevel.getValue()).intValue()) {
            return;
        }
        if (!this.eatTimer.hasTimeElapsed(((Double) this.delay.getValue()).longValue())) {
            return;
        }
        int foodSlot = -1;
        for (int slot = 36; slot < 45; ++slot) {
            ItemStack stack = player.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack();
            if (stack.isNull() || !this.isFood(stack)) {
                continue;
            }
            foodSlot = slot;
            break;
        }
        if (foodSlot == -1) {
            return;
        }
        this.eatTimer.reset();
        int previousSlot = player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().v();
        int targetHotbarSlot = foodSlot - 36;
        ItemStack food = player.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(foodSlot).getStack();
        if (this.silent.getEffectiveValue().booleanValue()) {
            player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(targetHotbarSlot));
        } else {
            player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(targetHotbarSlot);
        }
        Minecraft.playerController().sendUseItem(player, player.getWorld(), food);
        if (this.silent.getEffectiveValue().booleanValue()) {
            player.sendQueue().addToSendQueue(CPacketHeldItemChange.create(previousSlot));
        } else {
            player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(previousSlot);
        }
    }
}
