package gg.umbra.tools;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.inventory.InventoryClick;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.tools.inventory.InventoryActionGuard;
import gg.umbra.tools.inventory.InventoryActionModule;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoArmor
extends HackModule
implements InventoryActionModule {
    private final ToggleSetting inventoryOnly;
    private String[] bootsNames;
    private final TimerUtil openTimer;
    private final TimerUtil clickTimer;
    private boolean closingInventory;
    private final ToggleSetting dropEquipped;
    private final RandomRangeSetting delay;
    private Object lastScreen;
    private String[] helmetNames;
    private final ToggleSetting checkDurability;
    private String[] leggingsNames;
    private final ToggleSetting combatCheck;
    private static final long MAGIC_ID = -516952979702363148L;
    private final InventoryActionGuard combatGuard;
    private final ToggleSetting openInventory = ToggleSetting.create(this, "Open inventory", true, "Opens your inventory when you can equip armor");
    private boolean pressedInventoryKey;
    private final Queue<InventoryClick> clickQueue;
    private String[] chestplateNames;

    private void queueClick(int windowId, int slot, int mouseButton, int clickType) {
        this.clickQueue.add(new InventoryClick(windowId, slot, mouseButton, clickType));
    }

    @Listen
    public void onTick(EventPrePlayerTick event) {
        if (Umbra.INSTANCE.getHackManager().isOtherInventoryActionActive(AutoArmor.class) || Umbra.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.clickQueue.clear();
            return;
        }
        this.trackScreenChange();
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (!localPlayer.p$src$Lgg_umbra_wrapper_impl_Container_$1a6go00().isNull() && localPlayer.p$src$Lgg_umbra_wrapper_impl_Container_$1a6go00().getWindowId() != 0) {
            return;
        }
        if (this.openInventory.getEffectiveValue().booleanValue() && !Minecraft.currentScreen().isInstance(MappedClasses.YS) && !Minecraft.currentScreen().isInstance(MappedClasses.n)) {
            this.clickQueue.clear();
        }
        if (this.inventoryOnly.getEffectiveValue().booleanValue() && !Minecraft.currentScreen().isInstance(MappedClasses.YS) && !Minecraft.currentScreen().isInstance(MappedClasses.n)) {
            this.clickQueue.clear();
            return;
        }
        if (!this.openTimer.hasTimeElapsed(100L + (long)this.delay.getRandomRangeSetting())) {
            return;
        }
        if (!this.clickQueue.isEmpty()) {
            if (this.clickTimer.hasTimeElapsed((long)this.delay.getRandomRangeSetting())) {
                InventoryClick pendingClick = this.clickQueue.poll();
                pendingClick.execute();
                this.clickTimer.reset();
            }
            return;
        }
        if (this.closingInventory) {
            if (Minecraft.currentScreen().isNotNull()) {
                localPlayer.Z$src$V$1ie832h();
                this.pressedInventoryKey = false;
                this.closingInventory = false;
            }
            return;
        }
        if (this.combatCheck.getEffectiveValue().booleanValue()) {
            this.combatGuard.update(localPlayer);
            if (this.combatGuard.isBlocked()) {
                this.openTimer.reset();
                return;
            }
        }
        for (int armorSlot = 5; armorSlot < 9; ++armorSlot) {
            int candidateSlot = this.findBestArmorSlot(armorSlot, this.checkDurability.getEffectiveValue());
            if (candidateSlot == -1) continue;
            if (this.openInventory.getEffectiveValue().booleanValue() && !Minecraft.currentScreen().isInstance(MappedClasses.YS)) {
                if (this.openTimer.hasTimeElapsed(200L + (long)this.delay.getRandomRangeSetting())) {
                    KeyBinding keyBinding = Minecraft.gameSettings().j();
                    if (ForgeVersion.MC_1_16_5.d()) {
                        KeyBindingHelper.incrementPressTime(keyBinding);
                    } else {
                        KeyBindingHelper.setPressedAndTick(keyBinding, true);
                        KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
                    }
                    this.pressedInventoryKey = true;
                }
                return;
            }
            if (localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(armorSlot).getStack().isNotNull()) {
                if (this.dropEquipped.getEffectiveValue().booleanValue()) {
                    this.queueClick(localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getWindowId(), armorSlot, 0, 0);
                    this.queueClick(localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getWindowId(), -999, 0, 0);
                } else {
                    this.queueClick(localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getWindowId(), armorSlot, 0, 1);
                }
            }
            this.queueClick(localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getWindowId(), candidateSlot, 0, 1);
        }
        if (this.pressedInventoryKey && this.clickQueue.isEmpty()) {
            this.closingInventory = true;
            this.openTimer.reset();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.closingInventory = false;
        this.pressedInventoryKey = false;
    }

    private int findBestArmorSlot(int armorSlot, boolean compareDurability) {
        int bestSlot = -1;
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ItemStack equippedArmor = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(armorSlot).getStack();
        double equippedScore = 0.0;
        double equippedDamage = 999.0;
        if (equippedArmor.isNotNull()) {
            equippedScore = this.scoreItem(equippedArmor);
            equippedDamage = this.getItemDamage(armorSlot);
        }
        double bestScore = equippedScore;
        double bestDamage = equippedDamage;
        for (int inventorySlot = 9; inventorySlot < 45; ++inventorySlot) {
            ItemStack candidateArmor = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(inventorySlot).getStack();
            if (!candidateArmor.isNotNull() || this.armorSlotFor(candidateArmor) != armorSlot) continue;
            double candidateScore = this.scoreItem(candidateArmor);
            double candidateDamage = this.getItemDamage(inventorySlot);
            if (candidateScore > bestScore) {
                bestScore = candidateScore;
                bestSlot = inventorySlot;
                bestDamage = candidateDamage;
                continue;
            }
            if (!compareDurability || candidateScore != bestScore || !(candidateDamage < bestDamage)) continue;
            bestDamage = candidateDamage;
            bestSlot = inventorySlot;
        }
        return bestSlot;
    }

    private int getItemDamage(int slot) {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ItemStack itemStack = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack();
        return itemStack.isNotNull() ? itemStack.L() : 999;
    }


    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && this.clickQueue.size() > 0;
    }

    @Override
    public String getDetailedSuffix() {
        if (this.combatCheck.getEffectiveValue().booleanValue() && this.combatGuard.isBlocked()) {
            return ClientSettings.FORMAT_CODE + "c[In Combat]";
        }
        return super.getDetailedSuffix();
    }

    private void trackScreenChange() {
        Object currentScreen = Minecraft.currentScreen().getObject();
        if (currentScreen != this.lastScreen) {
            this.openTimer.reset();
        }
        this.lastScreen = currentScreen;
    }

    private int armorSlotFor(ItemStack itemStack) {
        ItemMappingEntry itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack);
        if (itemMappingEntry == null || itemMappingEntry.getResourceKey() == null) {
            return -1;
        }
        for (String string : this.bootsNames) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 8;
        }
        for (String string : this.leggingsNames) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 7;
        }
        for (String string : this.chestplateNames) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 6;
        }
        for (String string : this.helmetNames) {
            if (!itemMappingEntry.getResourceKey().toLowerCase().contains(string)) continue;
            return 5;
        }
        return -1;
    }

    public AutoArmor() {
        super("AutoArmor", (int)MAGIC_ID, Category.INVENTORY, "Automatically equips armor when needed.");
        this.inventoryOnly = ToggleSetting.create(this, "Inventory only", true, "Only equip armor when in inventory");
        this.checkDurability = ToggleSetting.create(this, "Check durability", true, "Always puts on the armor with the highest durability");
        this.dropEquipped = ToggleSetting.create(this, "Drop equipped", false, "Drops worse equipped armor for better armor when active");
        this.combatCheck = ToggleSetting.create(this, "Combat check", false, "Won't equip armor while in combat");
        this.delay = RandomRangeSetting.createWithIncrement(this, "Delay", "#", "", 1.0, 100.0, 120.0, 200.0, 1.0);
        this.clickTimer = new TimerUtil();
        this.openTimer = new TimerUtil();
        this.combatGuard = new InventoryActionGuard(20);
        this.clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
        this.addValue(this.openInventory, this.inventoryOnly, this.checkDurability, this.dropEquipped, this.combatCheck, this.delay);
        this.helmetNames = new String[]{"cap", "helmet"};
        this.chestplateNames = new String[]{"tunic", "chestplate"};
        this.leggingsNames = new String[]{"pants", "leggings"};
        this.bootsNames = new String[]{"boots"};
    }

    private double scoreItem(ItemStack itemStack) {
        double score = ItemStackScoreUtil.L(itemStack);
        if (!itemStack.isNotNull()) {
            return score;
        }
        Item item = itemStack.getItem();
        if (item.isNotNull() && ItemStackScoreUtil.R(item) && ItemStackScoreUtil.T$src$Z$2fnsig(itemStack) && ItemStackScoreUtil.t(itemStack) == 0) {
            score -= 0.01;
        }
        return score;
    }
}

