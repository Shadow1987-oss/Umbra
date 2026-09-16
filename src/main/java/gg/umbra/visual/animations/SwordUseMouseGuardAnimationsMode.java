package gg.umbra.visual.animations;

import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.module.HackModule;
import gg.umbra.visual.Animations;
import gg.umbra.visual.animations.AnimationsMode;
import gg.umbra.rotation.RotationManager;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;

public class SwordUseMouseGuardAnimationsMode
extends AnimationsMode {
    private int targetStreak = 1;

    public SwordUseMouseGuardAnimationsMode(HackModule parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean isBlocking() {
        return this.shouldBlock();
    }

    @Listen
    public void onMouseButton(EventMouseButton event) {
        if (!((Animations)this.getParent()).isHoldingSword()) {
            return;
        }
        int buttonBinding = -100 + event.getButton();
        if (event.getButtonState() && buttonBinding == Minecraft.gameSettings().b$src$Lgg_umbra_wrapper_impl_KeyBinding_$1yi3362().getKeyCode() && ((Animations)this.getParent()).requiresMouseDown() && ClientSettings.isAttackButtonDown()) {
            event.setCancelled(true);
            return;
        }
    }


    private boolean shouldBlockSwordUse() {
        if (((Animations)this.getParent()).requiresMouseDown() && !ClientSettings.isUseItemButtonDown()) {
            return false;
        }
        boolean shouldBlock = true;
        EntityPlayerSP player = Minecraft.thePlayer();
        ItemStack itemStack = player.B$src$Lgg_umbra_wrapper_impl_ItemStack_$impdvt();
        if (itemStack.isNull() || itemStack.getItem().isNull() || !ItemStackScoreUtil.h(itemStack.getItem())) {
            return false;
        }
        boolean targetingEntity = false;
        RayTraceResult rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTraceResult.isNotNull() && rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            targetingEntity = true;
        }
        if (this.targetStreak != 1 || !targetingEntity) {
            shouldBlock = false;
            int maxTargetStreak = 3;
            if (this.targetStreak >= maxTargetStreak) {
                this.targetStreak = 0;
            }
        }
        this.targetStreak = targetingEntity ? ++this.targetStreak : 1;
        return shouldBlock;
    }

    @Override
    public boolean shouldBlock() {
        return this.shouldBlockSwordUse();
    }
}
