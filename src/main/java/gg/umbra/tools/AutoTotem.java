package gg.umbra.tools;

import gg.umbra.Umbra;
import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventClickMouse;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventRightClickMouse;
import gg.umbra.event.impl.EventSendClickBlockToController;
import gg.umbra.event.impl.EventThreadBoundPostTick;
import gg.umbra.event.impl.EventThreadBoundPreTick;
import gg.umbra.event.impl.EventWindowClick;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.inventory.InventoryClick;
import gg.umbra.mapping.ItemMappingEntry;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.module.HackDisplayInfo;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.GhostCamera;
import gg.umbra.tools.inventory.InventoryActionModule;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.ui.click.frame.impl.hud.ActiveModuleStackFrame;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GameSettings;
import gg.umbra.wrapper.impl.GuiScreen;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoTotem
extends HackModule
implements InventoryActionModule {
    private final Random random;
    private final TimerUtil actionTimer;
    private final TimerUtil clickTimer = new TimerUtil();
    private Object lastScreen;
    private final RotationControlClaim rotationClaim;
    private static GhostCamera freecam;
    private boolean clickingSlot;
    private long delayDuration = -1L;
    private final RandomRangeSetting silentMoveDelay;
    private final RandomRangeSetting delay;
    private boolean closePending;
    private boolean inventoryOpen;
    private AdaptiveRotationController rotationController;
    private final ToggleSetting extraRandomization;
    private long clickDelay = -1L;
    private final ToggleSetting showTotemCount;
    private final Queue<InventoryClick> clickQueue;
    private final TimerUtil delayTimer = new TimerUtil();
    private final ToggleSetting closeInventory;
    private final ToggleSetting inventoryOnly;
    private boolean suppressInput;
    private final ToggleSetting silentOpen;
    private boolean silentActive;
    private final RotationManager rotationManager;
    private final ToggleSetting openInventory;
    private final ToggleSetting randomSlot;
    private final SliderSetting healthThreshold;

    private double computeDelay() {
        if (!this.extraRandomization.getEffectiveValue().booleanValue()) {
            return this.delay.getRandomRangeSetting();
        }
        double min = this.delay.getMinimumValue();
        double max = this.delay.getMaximumValue();
        double range = Math.max(1.0, max - min);
        double center = min + range * 0.5;
        double stdDev = Math.max(1.0, range / 4.0);
        double result = center + this.random.nextGaussian() * stdDev;
        result = Math.max(min, Math.min(max, result));
        if (this.random.nextDouble() < 0.18) {
            double extraMin = Math.max(120.0, range * 1.25);
            double extraMax = Math.max(350.0, range * 3.25);
            result += extraMin + (extraMax - extraMin) * this.random.nextDouble();
        }
        return result;
    }

    private int countTotems(EntityPlayerSP localPlayer) {
        int count = 0;
        for (int slot = 9; slot <= 45; ++slot) {
            ItemMappingEntry itemMappingEntry;
            ItemStack itemStack = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack();
            if (itemStack.isNull() || (itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack)) == null || !itemMappingEntry.getResourceKey().toLowerCase().contains("totem_of_undying")) continue;
            count += itemStack.t();
        }
        return count;
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onSendClickBlock(EventSendClickBlockToController event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    private void closeInventory() {
        GuiScreen guiScreen = Minecraft.currentScreen();
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (this.inventoryOpen) {
            if (this.silentActive) {
                this.silentActive = false;
                if (this.rotationController != null) {
                    this.rotationController.setSpeed(3.0f);
                    this.rotationController.setRandomizeMovement(true);
                    this.rotationController.setLinearAcceleration(true);
                    this.rotationManager.releaseController(this.rotationController);
                    this.rotationController = null;
                }
                this.rotationClaim.release(this);
                localPlayer.Z$src$V$1ie832h();
            } else if (this.closeInventory.getEffectiveValue().booleanValue() && guiScreen.isNotNull()) {
                localPlayer.Z$src$V$1ie832h();
            }
        }
        this.inventoryOpen = false;
        this.closePending = false;
    }

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && !this.clickQueue.isEmpty();
    }

    private boolean isDelayElapsed() {
        if (this.delayDuration <= 0L) {
            AutoTotem autoTotem = this;
            this.delayDuration = Math.max(1L, (long)autoTotem.computeDelay());
        }
        return this.delayTimer.hasTimeElapsed(this.delayDuration);
    }

    private void queueClick(int windowId, int slot, int mouseButton, int clickType) {
        this.clickQueue.add(new InventoryClick(windowId, slot, mouseButton, clickType));
    }

    private void openInventory() {
        if (this.silentOpen.getEffectiveValue().booleanValue()) {
            if (this.isSilentOpenBlocked()) {
                return;
            }
            this.silentActive = true;
            this.suppressInput = true;
            this.actionTimer.reset();
            this.rotationController = new AdaptiveRotationController();
            this.rotationController.setSpeed(0.0f);
            this.rotationManager.setController(this.rotationController);
        } else {
            KeyBinding keyBinding = Minecraft.gameSettings().j();
            if (ForgeVersion.MC_1_16_5.d()) {
                KeyBindingHelper.incrementPressTime(keyBinding);
            } else {
                KeyBindingHelper.setPressedAndTick(keyBinding, true);
                KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
            }
        }
        this.inventoryOpen = true;
        this.resetDelayTimer();
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onClickMouse(EventClickMouse event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    private void trackScreenChange() {
        Object currentScreen = Minecraft.currentScreen().getObject();
        if (currentScreen != this.lastScreen) {
            this.resetDelayTimer();
        }
        this.lastScreen = currentScreen;
    }

    private void releaseMovementKeys(GameSettings gameSettings) {
        if (this.suppressInput) {
            KeyBindingHelper.updateKeyBinding(gameSettings.Y(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.Y()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.s(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.s()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.x$src$Lgg_umbra_wrapper_impl_KeyBinding_$1cf7isg(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.x$src$Lgg_umbra_wrapper_impl_KeyBinding_$1cf7isg()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.g$src$Lgg_umbra_wrapper_impl_KeyBinding_$qqn5n3(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.g$src$Lgg_umbra_wrapper_impl_KeyBinding_$qqn5n3()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.O(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.O()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.r(), gg.umbra.config.ClientSettings.isPhysicalKeyDown(gameSettings.r()), true);
        }
    }

    @Override
    public HackDisplayInfo getModuleDisplayInfo() {
        if (!this.showTotemCount.getEffectiveValue().booleanValue()) {
            return null;
        }
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (localPlayer.isNull()) {
            return null;
        }
        int totemCount = this.countTotems(localPlayer);
        Color color = new Color(255, 20, 20);
        if (totemCount >= 4) {
            color = new Color(2, 190, 58);
        } else if (totemCount >= 2) {
            color = new Color(255, 249, 18);
        }
        return new HackDisplayInfo(String.valueOf(totemCount), color);
    }

    @Override
    public void onDisable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).removeModule(this);
        if (this.inventoryOpen && this.silentActive) {
            this.closeInventory();
        }
        this.closePending = false;
        this.inventoryOpen = false;
        this.delayDuration = -1L;
        this.clickDelay = -1L;
    }

    @Listen
    public void onWindowClick(EventWindowClick eventWindowClick) {
        boolean shouldCancel;
        GuiScreen guiScreen = eventWindowClick.getCurrentScreen();
        boolean inTotemScreen = shouldCancel = this.inventoryOpen && this.silentActive || guiScreen.isNotNull() && guiScreen.isInstance(MappedClasses.YS);
        if (shouldCancel && !this.clickingSlot && this.inventoryOpen) {
            eventWindowClick.setCancelled(true);
        }
    }


    @Listen
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        boolean inInventoryScreen;
        boolean silentEquipping;
        if (this.suppressInput) {
            if (this.actionTimer.hasTimeElapsed((long)this.silentMoveDelay.getRandomRangeSetting())) {
                this.releaseMovementKeys(eventPrePlayerTick.getGameSettings());
                this.suppressInput = false;
            } else {
                this.blockMovementKeys(eventPrePlayerTick.getGameSettings());
            }
        }
        if (Umbra.INSTANCE.getHackManager().isOtherInventoryActionActive(AutoTotem.class) || Umbra.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.clickQueue.clear();
            return;
        }
        this.trackScreenChange();
        EntityPlayerSP localPlayer = eventPrePlayerTick.getThePlayer();
        if (localPlayer.isNull() || localPlayer.M$src$Z$ff28xj()) {
            if (this.inventoryOpen && this.silentActive) {
                this.closeInventory();
            }
            this.clickingSlot = false;
            return;
        }
        if (!localPlayer.p$src$Lgg_umbra_wrapper_impl_Container_$1a6go00().isNull() && localPlayer.p$src$Lgg_umbra_wrapper_impl_Container_$1a6go00().getWindowId() != 0) {
            return;
        }
        GuiScreen guiScreen = Minecraft.currentScreen();
        silentEquipping = this.inventoryOpen && this.silentActive;
        if (silentEquipping) {
            this.actionTimer.reset();
        }
        inInventoryScreen = silentEquipping || guiScreen.isInstance(MappedClasses.YS) || guiScreen.isInstance(MappedClasses.n);
        if (this.openInventory.getEffectiveValue().booleanValue() && !inInventoryScreen) {
            this.clickQueue.clear();
        }
        if (this.inventoryOnly.getEffectiveValue().booleanValue() && !inInventoryScreen) {
            this.clickQueue.clear();
            return;
        }
        if (!this.isDelayElapsed()) {
            return;
        }
        if (!this.clickQueue.isEmpty()) {
            if (this.isClickDelayElapsed()) {
                InventoryClick inventoryClick = this.clickQueue.poll();
                if (inventoryClick != null) {
                    this.clickingSlot = true;
                    inventoryClick.execute();
                    this.clickingSlot = false;
                }
                this.actionTimer.reset();
                this.resetClickTimer();
            }
            return;
        }
        if (this.closePending) {
            this.closeInventory();
            return;
        }
        ItemStack itemStack = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(45).getStack();
        ItemMappingEntry itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack);
        if (itemMappingEntry != null && itemMappingEntry.getResourceKey().toLowerCase().contains("totem_of_undying")) {
            if (this.inventoryOpen && this.clickQueue.isEmpty()) {
                this.closePending = true;
                this.resetDelayTimer();
            }
            return;
        }
        int totemSlot = this.findTotemSlot();
        if (totemSlot != -1) {
            double thresholdPercent = (Double)this.healthThreshold.getValue();
            if (thresholdPercent < 100.0) {
                double thresholdPoints = thresholdPercent / 100.0 * 20.0;
                if ((double)localPlayer.w$src$F$15l9epb() > thresholdPoints) {
                    return;
                }
            }
            if (this.openInventory.getEffectiveValue().booleanValue() && !guiScreen.isInstance(MappedClasses.YS) && !silentEquipping) {
                if (this.isDelayElapsed()) {
                    this.openInventory();
                }
                return;
            }
            if (inInventoryScreen && !this.inventoryOpen) {
                this.inventoryOpen = true;
            }
            this.queueClick(localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getWindowId(), totemSlot, 40, 2);
            this.resetClickTimer();
        }
        if (this.inventoryOpen && this.clickQueue.isEmpty()) {
            this.closePending = true;
            this.resetDelayTimer();
        }
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onRightClickMouse(EventRightClickMouse eventRightClickMouse) {
        if (this.suppressInput) {
            eventRightClickMouse.setCancelled(true);
        }
    }

    @Override
    public String getId() {
        return "autototem";
    }

    public AutoTotem() {
        super("AutoTotem", -43691, Category.INVENTORY, "Automatically equips totems to your offhand");
        this.actionTimer = new TimerUtil();
        this.clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
        this.openInventory = ToggleSetting.create(this, "Open inventory", true, "Opens your inventory to equip a totem");
        this.silentOpen = ToggleSetting.create(this, "Silent open", false, "Silently opens your inventory to equip a totem");
        this.silentMoveDelay = RandomRangeSetting.createWithDescription(this, "Silent move delay", "#", "ms", 50.0, 100.0, 120.0, 200.0, 1.0, "Delay before preventing movement keys after silently opening the inventory");
        this.closeInventory = ToggleSetting.create(this, "Close inventory", true, "Closes your inventory after equipping a totem");
        this.inventoryOnly = ToggleSetting.create(this, "Inventory only", false, "Only equips a totem when in your inventory");
        this.randomSlot = ToggleSetting.create(this, "Random slot", true, "Chooses a random totem slot from your inventory");
        this.healthThreshold = SliderSetting.create(this, "Health threshold", "#", "%", 1.0, 100.0, 100.0, 1.0, "Only equips a totem when your health drops below this percentage (100 = always)");
        this.delay = RandomRangeSetting.createWithDescription(this, "Delay", "#", "ms", 50.0, 100.0, 120.0, 200.0, 1.0, "How long to wait before equipping a totem");
        this.extraRandomization = ToggleSetting.create(this, "Extra randomization", true, "Adds human-like timing variance while equipping totems");
        this.showTotemCount = ToggleSetting.create(this, "Show totem count", false, "Renders your totem count on the center of your screen");
        this.random = new Random();
        this.rotationManager = RotationManager.INSTANCE;
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.openInventory.addDependentValues(this.silentOpen, this.silentMoveDelay, this.closeInventory);
        this.silentOpen.addDependentValues(this.silentMoveDelay);
        this.silentOpen.getDisabledCondition().applyTo(this.closeInventory);
        this.addValue(this.openInventory, this.silentOpen, this.silentMoveDelay, this.closeInventory, this.inventoryOnly, this.randomSlot, this.healthThreshold, this.delay, this.extraRandomization, this.showTotemCount);
        this.rotationClaim.setPriority(this, 99);
    }

    private void resetClickTimer() {
        this.clickTimer.reset();
        AutoTotem autoTotem = this;
        this.clickDelay = Math.max(1L, (long)autoTotem.computeDelay());
    }

    private int findTotemSlot() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        ArrayList<Integer> totemSlots = new ArrayList<Integer>();
        for (int slot = 9; slot < 45; ++slot) {
            ItemMappingEntry itemMappingEntry;
            ItemStack itemStack = localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack();
            if (itemStack.isNull() || (itemMappingEntry = Umbra.INSTANCE.getItemStackResolver().resolve(itemStack)) == null || !itemMappingEntry.getResourceKey().toLowerCase().contains("totem_of_undying")) continue;
            if (!this.randomSlot.getEffectiveValue().booleanValue()) {
                return slot;
            }
            totemSlots.add(slot);
        }
        if (totemSlots.isEmpty()) {
            return -1;
        }
        return (Integer)totemSlots.get(this.random.nextInt(totemSlots.size()));
    }

    private int findEmptySlot() {
        int slot;
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        for (slot = 36; slot < 45; ++slot) {
            if (localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack().isNotNull()) continue;
            return slot;
        }
        for (slot = 9; slot < 36; ++slot) {
            if (localPlayer.F$src$Lgg_umbra_wrapper_impl_Container_$152y6lm().getSlot(slot).getStack().isNotNull()) continue;
            return slot;
        }
        return -1;
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onThreadBoundPostTick(EventThreadBoundPostTick eventThreadBoundPostTick) {
        if (this.suppressInput) {
            eventThreadBoundPostTick.setCancelled(true);
        }
    }

    private boolean isClickDelayElapsed() {
        if (this.clickDelay <= 0L) {
            AutoTotem autoTotem = this;
            this.clickDelay = Math.max(1L, (long)autoTotem.computeDelay());
        }
        return this.clickTimer.hasTimeElapsed(this.clickDelay);
    }

    private boolean isSilentOpenBlocked() {
        if (freecam == null) {
            freecam = Umbra.INSTANCE.getHackManager().getMod(GhostCamera.class);
        }
        return freecam != null && freecam.isEnabled() || this.rotationClaim.isBlockedFor(this) && !this.rotationClaim.acquire(this, true);
    }

    @Listen(priority=EventPriority.HIGHEST)
    public void onThreadBoundPreTick(EventThreadBoundPreTick eventThreadBoundPreTick) {
        if (this.suppressInput) {
            eventThreadBoundPreTick.setCancelled(true);
        }
    }

    @Override
    public void onEnable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).addModule(this);
        this.resetDelayTimer();
        this.resetClickTimer();
    }

    private void resetDelayTimer() {
        this.delayTimer.reset();
        AutoTotem autoTotem = this;
        this.delayDuration = Math.max(1L, (long)autoTotem.computeDelay());
    }

    private void blockMovementKeys(GameSettings gameSettings) {
        if (this.suppressInput) {
            KeyBindingHelper.setPressedAndTick(gameSettings.Y(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.s(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.x$src$Lgg_umbra_wrapper_impl_KeyBinding_$1cf7isg(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.g$src$Lgg_umbra_wrapper_impl_KeyBinding_$qqn5n3(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.O(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.r(), false);
        }
    }
}
