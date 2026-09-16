package gg.umbra.worldmods;

import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.ItemStackScoreUtil;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.EnumHand;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;

public class FastPlace
extends HackModule {
    private final ModeOption projectilesOption;
    private final OptionSetting heldItemMode;
    private final ModeOption allOption;
    private static final long MODULE_COLOR = 506362964711178016L;
    private final SliderSetting delayValue = SliderSetting.create((Object)this, "Delay", "#", "", 0.0, 1.0, 4.0, 1.0);
    private final ModeOption blocksOption;

    public FastPlace() {
        super("FastPlace", (int)MODULE_COLOR, Category.WORLD, "Changes the block place delay.");
        this.allOption = new ModeOption("All");
        this.blocksOption = new ModeOption("Blocks");
        this.projectilesOption = new ModeOption("Projectiles");
        this.heldItemMode = OptionSetting.create((Object)this, "Held Item", "What kind of items should FastPlace function with?\nAll - All items/blocks\nBlocks - All blocks\nProjectiles - Snowballs & Eggs", (ModeSelection)this.allOption, this.allOption, this.blocksOption, this.projectilesOption);
        this.addValue(this.heldItemMode, this.delayValue);
    }

    @Listen
    public void onTick(EventPreTick eventPreTick) {
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        if (entityPlayerSP.isNull()) {
            return;
        }
        if (SharedModuleControlClaims.rightClickUse.isClaimed()) {
            return;
        }
        ItemStack mainHandStack = entityPlayerSP.getHeldItemHand();
        ItemStack offHandStack = ForgeVersion.MC_1_12_2.d() ? entityPlayerSP.i(EnumHand.offHand()) : new ItemStack(null);
        if (this.heldItemMode.getValue() == this.blocksOption && !this.isBlock(mainHandStack) && !this.isBlock(offHandStack)) {
            return;
        }
        if (this.heldItemMode.getValue() == this.projectilesOption && !this.isProjectile(mainHandStack) && !this.isProjectile(offHandStack)) {
            return;
        }
        if ((double)Minecraft.w() > (Double)this.delayValue.getValue()) {
            Minecraft.E(((Double)this.delayValue.getValue()).intValue());
        }
    }

    private boolean isProjectile(ItemStack itemStack) {
        return itemStack.isNotNull() && itemStack.getItem().isNotNull() && ItemStackScoreUtil.Z(itemStack.getItem());
    }

    private boolean isBlock(ItemStack itemStack) {
        return itemStack.isNotNull() && itemStack.getItem().isNotNull() && itemStack.getItem().isInstance(MappedClasses.Vw);
    }

}

