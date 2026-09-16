package gg.umbra.hacks.pvp;

import gg.umbra.event.Event;
import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.SyntheticAttackRequestEvent;
import gg.umbra.input.AttackKeyController;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.rotation.RotationManager;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityOtherPlayerMP;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.InventoryPlayer;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;

public class ShieldCracker extends HackModule {
    private final SliderSetting swapDelay;
    private final ToggleSetting autoSwitchBack;
    private final SliderSetting swapBackDelay;
    private SwapState state = SwapState.IDLE;
    private int originalSlot = -1;
    private int ticksRemaining;
    private boolean attackReleasePending;

    public ShieldCracker() {
        super("ShieldBreaker", 0, Category.COMBAT,
                "Swaps to an axe when attacking a player with a raised shield");
        this.swapDelay = SliderSetting.create(
                this, "Swap delay", "#", "tick", 0.0, 5.0, 20.0, 1.0);
        this.autoSwitchBack = ToggleSetting.create(this, "Auto swap back", true, "Sweeping back to the original slot");
        this.swapBackDelay = SliderSetting.create(
                this, "Swap back delay", "#", "tick", 0.0, 5.0, 20.0, 1.0,
                "Delay between attacking and sweeping back to the original slot");
        this.swapDelay.setMaximumFractionDigits(0);
        this.swapBackDelay.setMaximumFractionDigits(0);
        this.autoSwitchBack.addDependentValues(this.swapBackDelay);
        this.addValue(this.swapDelay, this.autoSwitchBack, this.swapBackDelay);
    }

    @Override
    public String getId() {
        return "shieldbreaker";
    }

    @Listen(priority = EventPriority.HIGH, skipCanceled = true)
    public void onMouseButton(EventMouseButton event) {
        if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
            this.handleAttack(event);
        }
    }

    @Listen(priority = EventPriority.HIGH, skipCanceled = true)
    public void onKeyPress(EventKeyPress event) {
        if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
            this.handleAttack(event);
        }
    }

    @Listen(priority = EventPriority.HIGH, skipCanceled = true)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (event.getSource() != this) {
            this.handleAttack(event);
        }
    }

    @Listen(priority = EventPriority.HIGH)
    public void onTick(EventPreTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull() || Minecraft.currentScreen().isNotNull()) {
            this.resetState(player, true);
            return;
        }

        if (this.attackReleasePending) {
            AttackKeyController.releaseAttackKey();
            this.attackReleasePending = false;
        }

        if (this.state == SwapState.IDLE) {
            return;
        }

        if (this.state == SwapState.WAITING_TO_SWAP_BACK
                && !this.autoSwitchBack.getEffectiveValue()) {
            this.resetState(player, false);
            return;
        }

        if (this.ticksRemaining > 0) {
            --this.ticksRemaining;
            if (this.ticksRemaining > 0) {
                return;
            }
        }

        if (this.state == SwapState.WAITING_TO_ATTACK) {
            this.attackWithAxe(player);
        } else if (this.state == SwapState.WAITING_TO_SWAP_BACK) {
            this.resetState(player, true);
        }
    }

    @Override
    public void onDisable() {
        this.resetState(Minecraft.thePlayer(), true);
    }

    private void handleAttack(Event event) {
        if (this.state != SwapState.IDLE || Minecraft.currentScreen().isNotNull()) {
            return;
        }

        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull() || !this.isAttackingRaisedShield()) {
            return;
        }

        InventoryPlayer inventory = player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
        int selectedSlot = inventory.v();
        if (this.isAxe(inventory.c(selectedSlot))) {
            return;
        }

        int axeSlot = this.findAxeSlot(inventory);
        if (axeSlot == -1) {
            return;
        }

        this.originalSlot = selectedSlot;
        inventory.g(axeSlot);
        this.state = SwapState.WAITING_TO_ATTACK;
        this.ticksRemaining = this.getTickValue(this.swapDelay);
        event.setCancelled(true);

        if (this.ticksRemaining == 0) {
            this.attackWithAxe(player);
        }
    }

    private boolean isAttackingRaisedShield() {
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTrace == null || !rayTrace.isEntityHit()) {
            return false;
        }

        Entity target = rayTrace.getEntity();
        if (target == null || target.isNull() || !target.isInstance(MappedClasses.lG)) {
            return false;
        }

        return RotationUtil.n(new EntityOtherPlayerMP(target.getObject()));
    }

    private void attackWithAxe(EntityPlayerSP player) {
        InventoryPlayer inventory = player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
        if (!this.isAxe(inventory.c(inventory.v()))) {
            int axeSlot = this.findAxeSlot(inventory);
            if (axeSlot == -1) {
                this.resetState(player, true);
                return;
            }
            inventory.g(axeSlot);
        }

        AttackKeyController.releaseAttackKey();
        this.attackReleasePending = AttackKeyController.requestSyntheticAttack(this);
        if (this.autoSwitchBack.getEffectiveValue()) {
            this.state = SwapState.WAITING_TO_SWAP_BACK;
            this.ticksRemaining = this.getTickValue(this.swapBackDelay);
        } else {
            this.originalSlot = -1;
            this.ticksRemaining = 0;
            this.state = SwapState.IDLE;
        }
    }

    private int findAxeSlot(InventoryPlayer inventory) {
        for (int slot = 0; slot < 9; ++slot) {
            if (this.isAxe(inventory.c(slot))) {
                return slot;
            }
        }
        return -1;
    }

    private boolean isAxe(ItemStack stack) {
        return stack != null && stack.isNotNull() && stack.getItem().isNotNull()
                && ItemStackScoreUtil.T(stack.getItem());
    }

    private int getTickValue(SliderSetting value) {
        return Math.max(0, value.getValue().intValue());
    }

    private void resetState(EntityPlayerSP player, boolean restoreSlot) {
        if (this.attackReleasePending) {
            AttackKeyController.releaseAttackKey();
        }
        if (restoreSlot && this.originalSlot != -1 && player != null && player.isNotNull()) {
            player.V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(this.originalSlot);
        }
        this.attackReleasePending = false;
        this.originalSlot = -1;
        this.ticksRemaining = 0;
        this.state = SwapState.IDLE;
    }

    private enum SwapState {
        IDLE,
        WAITING_TO_ATTACK,
        WAITING_TO_SWAP_BACK
    }
}
