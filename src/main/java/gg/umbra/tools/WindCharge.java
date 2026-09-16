package gg.umbra.tools;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.module.UtilityHack;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.FixedRotationController;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.InventoryPlayer;
import gg.umbra.wrapper.impl.Item;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;

public class WindCharge
extends UtilityHack {
    public final SliderSetting aimSpeed = SliderSetting.create(this, "Aim speed", "#.#", "", 1.0, 7.0, 10.0);
    private FixedRotationController rotationController;
    private int state;
    private static final long MAGIC_STATE = 1631323600877256706L;
    private final RotationControlClaim rotationClaim;
    private int savedSlot = -1;
    private final ToggleSetting silentAim = new ToggleSetting((Object)this, "Silent aim", true);

    private int findWindChargeSlot() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6();
        Item item = Item.L("minecraft:wind_charge");
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = inventory.c(i);
            if (itemStack.isNull() || itemStack.getItem().isNull()) continue;
            Item slotItem = itemStack.getItem();
            if (item == null || !item.isNotNull() || !slotItem.equals(item)) continue;
            return i;
        }
        return -1;
    }

    private boolean canClaimRotation() {
        return this.rotationClaim.isOwnedBy(this) || this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue());
    }

    @Override
    public void onDisable() {
        this.releaseRotation();
        if (this.savedSlot != -1) {
            Minecraft.gameSettings().O().e();
            Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(this.savedSlot);
        }
        this.savedSlot = -1;
        this.state = 0;
    }

    private void releaseRotation() {
        if (this.rotationController != null) {
            RotationManager.INSTANCE.releaseController(this.rotationController);
            this.rotationClaim.release(this);
        }
    }

    public WindCharge() {
        super("WindCharge", "Automatically uses a wind charge");
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.addValue(this.aimSpeed, this.silentAim);
        this.rotationClaim.setPriority(this, 6);
    }


    @Listen
    public void onTick(EventPreTick eventPreTick) {
        if (this.state == 0) {
            int slot = this.findWindChargeSlot();
            if (slot != -1 && eventPreTick.getThePlayer().b$src$Z$fqlxe4()) {
                Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(slot);
                if (!this.canClaimRotation()) {
                    return;
                }
                this.rotationController = this.silentAim.getEffectiveValue() != false ? new AdaptiveRotationController(-999.0f, 90.0f) : new FixedRotationController(-999.0f, 90.0f);
                this.rotationController.setTargetRotation(-999.0f, 90.0f);
                this.rotationController.setSpeed(((Double)this.aimSpeed.getValue()).intValue());
                this.rotationController.setScaleAxesProportionally(false);
                this.rotationController.setLinearAcceleration(true);
                this.rotationController.setTolerance(5.0f);
                RotationManager.INSTANCE.setController(this.rotationController);
                this.state = 1;
            }
        } else if (this.state == 1) {
            if (this.rotationController != null) {
                if (this.rotationController.isComplete() || RotationManager.INSTANCE.getManagedPitch() > 80.0f) {
                    KeyBinding keyBinding = Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362();
                    KeyBinding.setKeyBindState(keyBinding, true);
                    KeyBinding.onTick(keyBinding);
                    KeyBinding.setKeyBindState(keyBinding, false);
                    this.state = (int)MAGIC_STATE;
                }
            } else {
                this.state = -1;
            }
        } else if (this.state == 2) {
            Minecraft.gameSettings().O().I();
            this.state = -1;
        } else if (this.state == -1) {
            this.releaseRotation();
            if (this.savedSlot != -1) {
                Minecraft.gameSettings().O().e();
                Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().g(this.savedSlot);
            }
            this.setEnabled(false, true);
        }
    }

    @Override
    public void onEnable() {
        int slot = this.findWindChargeSlot();
        if (slot != -1 && Minecraft.thePlayer().b$src$Z$fqlxe4()) {
            this.savedSlot = Minecraft.thePlayer().V$src$Lgg_umbra_wrapper_impl_InventoryPlayer_$erqak6().v();
            this.state = 0;
        } else {
            this.setEnabled(false, true);
        }
    }
}
