package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.UtilityHack;
import gg.umbra.tools.invcleaner.ItemDataComparator;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.datas.ItemStackData;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.GameSettings;
import gg.umbra.wrapper.impl.InventoryPlayer;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemSplashPotion;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PotionEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Throwpot
extends UtilityHack {
    private final CopyOnWriteArrayList<ItemStackData> itemsToThrow;
    private final SliderSetting scrollDelay;
    private final OptionSetting modeValue;
    private int savedSlot;
    private final OptionSetting typeValue;
    private final ModeOption singleOption;
    private final ModeOption potsOption;
    private final ToggleSetting scrollValue;
    private final ModeOption bothOption = new ModeOption("Both");
    private final ModeOption dynamicOption;
    private final ToggleSetting throwBowls;
    private boolean active;
    private final ToggleSetting randomValue;
    private final RandomRangeSetting delayValue;
    private final ModeOption soupOption;

    @Override
    public void onEnable() {
        if (this.active) {
            this.setEnabled(false);
            return;
        }
        if (Minecraft.thePlayer().isNull() || Minecraft.currentScreen().isNotNull()) {
            this.setEnabled(false);
            return;
        }
        if (!this.active && this.collectHealingItems()) {
            InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
            this.savedSlot = inventory.v();
            this.active = true;
            this.v(0L, false);
        } else {
            this.itemsToThrow.clear();
            this.setEnabled(false);
        }
    }

    private boolean collectHealingItems() {
        ArrayList<Integer> hotbarSlots = new ArrayList<Integer>();
        for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
            hotbarSlots.add(hotbarSlot);
        }
        if (this.randomValue.getEffectiveValue().booleanValue()) {
            Collections.shuffle(hotbarSlots);
        }
        Object[] hotbarContents = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().M();
        int queuedHealing = 0;
        for (Integer hotbarSlot : hotbarSlots) {
            Item item;
            ItemStack itemStack = new ItemStack(hotbarContents[hotbarSlot]);
            if (itemStack.isNull() || (item = itemStack.getItem()).isNull()) continue;
            boolean soupEnabled = ((ModeSelection)this.typeValue.getValue()).equals(this.soupOption) || ((ModeSelection)this.typeValue.getValue()).equals(this.bothOption);
            boolean potsEnabled = ((ModeSelection)this.typeValue.getValue()).equals(this.potsOption) || ((ModeSelection)this.typeValue.getValue()).equals(this.bothOption);
            if (ItemStackScoreUtil.v(item) && soupEnabled) {
                if (((ModeSelection)this.modeValue.getValue()).equals(this.singleOption)) {
                    this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                    break;
                }
                int soupHealing = 8;
                if ((double)(queuedHealing + soupHealing) + Math.floor(Minecraft.thePlayer().w$src$F$15l9epb()) > (double)Minecraft.thePlayer().I$src$F$14vyvep()) continue;
                queuedHealing += soupHealing;
                this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
            }
            if (!MappedClasses.Di.isInstance(item.getObject()) || !potsEnabled || !ItemSplashPotion.isSplashPotion(itemStack)) continue;
            if (((ModeSelection)this.modeValue.getValue()).equals(this.singleOption) && ItemStackScoreUtil.i(itemStack)) {
                this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                break;
            }
            ItemSplashPotion splashPotion = new ItemSplashPotion(item.getObject());
            PotionEffect potionEffect = new PotionEffect(splashPotion.getRawPotionEffects(itemStack).get(0));
            int potionHealing = 4 * (potionEffect.L() + 1);
            if (!ItemStackScoreUtil.i(itemStack) || (double)(queuedHealing + potionHealing) + Math.floor(Minecraft.thePlayer().w$src$F$15l9epb()) > (double)Minecraft.thePlayer().I$src$F$14vyvep()) continue;
            queuedHealing += potionHealing;
            this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
        }
        return !this.itemsToThrow.isEmpty();
    }

    @Override
    public void onScheduledAction() {
        if (!this.active) {
            return;
        }
        try {
            GameSettings gameSettings = Minecraft.gameSettings();
            KeyBinding useKey = gameSettings.b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
            KeyBinding dropKey = gameSettings.v$src$Lgg_umbra_wrapper_impl_KeyBinding_$11ijh0e();
            this.itemsToThrow.sort(new ItemDataComparator(this.savedSlot));
            boolean restoreUseKey = false;
            for (ItemStackData itemToThrow : this.itemsToThrow) {
                this.selectHotbarSlot(itemToThrow.getSlot());
                if (useKey.isKeyDown() && ItemStackScoreUtil.v(itemToThrow.getItemStack().getItem())) {
                    KeyBindingHelper.updateKeyBinding(useKey, false, false);
                    Thread.sleep(51L);
                    restoreUseKey = true;
                }
                KeyBindingHelper.setPressedAndTick(useKey, true);
                Thread.sleep(51L);
                KeyBindingHelper.updateKeyBinding(useKey, false, false);
                if (this.throwBowls.getEffectiveValue().booleanValue() && ItemStackScoreUtil.v(itemToThrow.getItemStack().getItem())) {
                    KeyBindingHelper.setPressedAndTick(dropKey, true);
                    Thread.sleep(51L);
                    KeyBindingHelper.updateKeyBinding(dropKey, false, false);
                }
                Thread.sleep((long)this.delayValue.getRandomRangeSetting());
            }
            this.selectHotbarSlot(this.savedSlot);
            if (restoreUseKey) {
                KeyBindingHelper.setPressedAndTick(useKey, true);
            }
        }
        catch (Exception exception) {
            this.active = false;
            exception.printStackTrace();
        }
        this.active = false;
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (!this.active && this.isEnabled()) {
            this.setEnabled(false);
        }
    }

    private void selectHotbarSlot(int targetSlot) {
        if (!this.scrollValue.getEffectiveValue().booleanValue()) {
            Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(targetSlot);
            return;
        }
        int currentSlot = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().v();
        while (true) {
            Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(currentSlot);
            try {
                Thread.sleep(((Double)this.scrollDelay.getValue()).longValue());
            }
            catch (InterruptedException ignored) {
            }
            if (targetSlot > currentSlot) {
                ++currentSlot;
                continue;
            }
            if (targetSlot >= currentSlot) break;
            --currentSlot;
        }
    }

    @Override
    public String getId() {
        return "throwpot";
    }

    public Throwpot() {
        super("Throwpot", Category.INVENTORY, "Throws or consumes healing items automatically or upon pressing keybind");
        this.potsOption = new ModeOption("Pots");
        this.soupOption = new ModeOption("Soup");
        this.typeValue = OptionSetting.create((Object)this, "Type", this.bothOption, this.bothOption, this.potsOption, this.soupOption);
        this.dynamicOption = new ModeOption("Dynamic");
        this.singleOption = new ModeOption("Single");
        this.modeValue = OptionSetting.create((Object)this, "Mode", "Dynamic - uses only as many items as needed to heal as much as possible without over-healing\nSingle - Always uses one item, regardless of health", (ModeSelection)this.dynamicOption, this.dynamicOption, this.singleOption);
        this.scrollDelay = SliderSetting.create(this, "Scroll delay", "#", "ms", 0.0, 100.0, 200.0);
        this.delayValue = RandomRangeSetting.createWithIncrement(this, "Delay", "#", "ms", 0.0, 80.0, 115.0, 200.0, 1.0);
        this.scrollValue = ToggleSetting.create(this, "Scroll", false);
        this.randomValue = ToggleSetting.create(this, "Random", false);
        this.throwBowls = ToggleSetting.create(this, "Throw bowls", true, "Throws soup bowls after consuming");
        this.itemsToThrow = new CopyOnWriteArrayList<ItemStackData>();
        this.scrollValue.addDependentValues(this.scrollDelay);
        this.addValue(this.typeValue, this.modeValue, this.delayValue, this.scrollValue, this.scrollDelay, this.randomValue, this.throwBowls);
    }

    @Override
    public void onDisable() {
        this.itemsToThrow.clear();
    }
}

