package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.UtilityHack;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.datas.ItemStackData;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemSplashPotion;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class ThrowDebuff
extends UtilityHack {
    private TimerUtil scrollTimer;
    private final ToggleSetting scroll;
    private final SliderSetting scrollDelay;
    private boolean throwing = false;
    private final ToggleSetting[] debuffValues;
    private final RandomRangeSetting delay;
    int savedSlot = 0;
    private final Queue<ItemStackData> itemsToThrow = new ArrayDeque<ItemStackData>();
    private TimerUtil delayTimer;
    private final ModeOption oneOfEachOption;
    private final OptionSetting mode;
    private final ModeOption allOption = new ModeOption("All");
    private final ModeOption firstOption;

    @Override
    public void onDisable() {
        this.throwing = false;
        this.itemsToThrow.clear();
    }

    @Listen
    public void onTick(EventPreTick event) {
        KeyBinding useKey = Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
        if (this.throwing) {
            KeyBindingHelper.updateKeyBinding(useKey, false, false);
            this.throwing = false;
            return;
        }
        if (this.itemsToThrow.isEmpty()) {
            if (this.selectHotbarSlotIncrementally(this.savedSlot)) {
                this.setEnabled(false);
            }
            return;
        }
        ItemStackData nextItem = this.itemsToThrow.peek();
        if (this.delayTimer.hasTimeElapsed((long)this.delay.getRandomRangeSetting()) && this.selectHotbarSlotIncrementally(nextItem.getSlot())) {
            KeyBindingHelper.updateKeyBinding(useKey, true, true);
            this.throwing = true;
            this.delayTimer.reset();
            this.itemsToThrow.poll();
        }
    }

    private boolean collectDebuffs() {
        ArrayList<Integer> hotbarSlots = new ArrayList<Integer>();
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            hotbarSlots.add(hotbarSlot);
        }
        Object[] hotbarContents = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().M();
        ArrayList<ToggleSetting> queuedDebuffTypes = new ArrayList<ToggleSetting>();
        slotLoop: for (Integer hotbarSlot : hotbarSlots) {
            Item item;
            ItemStack itemStack = new ItemStack(hotbarContents[hotbarSlot]);
            if (itemStack.isNull() || (item = itemStack.getItem()).isNull() || !MappedClasses.Di.isInstance(item.getObject())) continue;
            ItemSplashPotion splashPotion = new ItemSplashPotion(item.getObject());
            for (ToggleSetting debuffValue : this.debuffValues) {
                if (((ModeSelection)this.mode.getValue()).equals(this.oneOfEachOption) && queuedDebuffTypes.contains(debuffValue)) continue;
                String potionName = splashPotion.getItemStackDisplayName(itemStack).toLowerCase();
                String debuffName = debuffValue.getName().toLowerCase();
                if (!debuffValue.getEffectiveValue().booleanValue() || !potionName.contains(debuffName)) continue;
                this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                queuedDebuffTypes.add(debuffValue);
                if (!((ModeSelection)this.mode.getValue()).equals(this.firstOption)) continue slotLoop;
                break slotLoop;
            }
        }
        return !this.itemsToThrow.isEmpty();
    }

    public ThrowDebuff() {
        super("ThrowDebuff", Category.INVENTORY, "");
        this.oneOfEachOption = new ModeOption("One of each");
        this.firstOption = new ModeOption("First");
        this.mode = OptionSetting.create((Object)this, "Mode", "All - Throws all debuffs on hotbar\nOne of each - Throws one of each debuff\nFirst - Throws only first debuff on hotbar", (ModeSelection)this.oneOfEachOption, this.allOption, this.oneOfEachOption, this.firstOption);
        this.debuffValues = new ToggleSetting[]{ToggleSetting.create(this, "Harming", true), ToggleSetting.create(this, "Weakness", true), ToggleSetting.create(this, "Poison", true), ToggleSetting.create(this, "Slowness", true)};
        this.delay = RandomRangeSetting.create(this, "Delay", "#.#", "", 0.0, 70.0, 120.0, 200.0);
        this.scroll = ToggleSetting.create(this, "Scroll", false);
        this.scrollDelay = SliderSetting.create(this, "Scroll delay", "#", "ms", 0.0, 100.0, 200.0);
        this.delayTimer = new TimerUtil();
        this.scrollTimer = new TimerUtil();
        this.setDefaultVisibility(false);
        this.addValue(this.mode);
        for (ToggleSetting booleanValue : this.debuffValues) {
            this.addValue(booleanValue);
        }
        this.addValue(this.delay);
        this.scroll.addDependentValues(this.scrollDelay);
        this.addValue(this.scroll);
        this.addValue(this.scrollDelay);
    }

    @Override
    public void onEnable() {
        if (this.collectDebuffs()) {
            this.savedSlot = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().v();
        } else {
            this.setEnabled(false);
        }
    }

    private boolean selectHotbarSlotIncrementally(int targetSlot) {
        if (!this.scroll.getEffectiveValue().booleanValue()) {
            Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(targetSlot);
            return true;
        }
        if (!this.scrollTimer.hasTimeElapsed(((Double)this.scrollDelay.getValue()).longValue())) {
            return false;
        }
        int currentSlot = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().v();
        if (targetSlot > currentSlot) {
            ++currentSlot;
        } else if (targetSlot < currentSlot) {
            --currentSlot;
        } else {
            this.scrollTimer.reset();
            return true;
        }
        Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(currentSlot);
        return false;
    }
}

