package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.hacks.pvp.aimassist.AimAssistRotationSubModule;
import gg.umbra.hacks.pvp.aimassist.AimAssistTargetingSubModule;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.EntityAngleComparator;
import gg.umbra.utils.EntityArmorValueComparator;
import gg.umbra.utils.EntityDistanceComparator;
import gg.umbra.utils.EntityEquipmentValueComparator;
import gg.umbra.utils.EntityHealthComparator;
import gg.umbra.utils.RayTraceUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PotionRegistry;
import gg.umbra.wrapper.impl.PlayerControllerMP;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import gg.umbra.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.Arrays;
import org.jetbrains.annotations.Nullable;

public class AutoAim
extends HackModule {
    private int blockBreakCooldown = 0;
    private final ToggleSetting strafeIncrease;
    public OptionSetting targetArea;
    protected final OptionSetting mode;
    public final ModeOption closestAreaMode;
    public final ModeOption centerMode;
    private final AimAssistTargetingSubModule adaptiveTargeting;
    public final ModeOption yawMode;
    public final ModeOption threatMode;
    private final SliderSetting horizontalSpeed;
    private final AimAssistRotationSubModule simpleRotation = new AimAssistRotationSubModule(this, "Simple");
    private final ItemFilterList allowedItems;
    private final SliderSetting maxAngle;
    private final ToggleSetting limitToItems;
    private final ToggleSetting ignoreInvisibles;
    public OptionSetting targetMode;
    private final ItemFilterList blockBreakItems;
    private final ToggleSetting aimVertically;
    public final ModeOption healthMode;
    private final ToggleSetting breakBlocksWhitelist;
    private final ToggleSetting checkBlockBreak;
    private final ModeOption distanceMode;
    private final EntityTargetFilterValue targetFilter;
    private final SliderSetting verticalSpeed;
    private final ToggleSetting requireMouseDown;
    public final ModeOption armorMode;
    private final SliderSetting distance;

    @Nullable
    public EntityLivingBase findBestTarget() {
        WorldClient worldClient = Minecraft.theWorld();
        if (worldClient.isNull()) {
            return null;
        }
        ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        ArrayList<?> loadedEntities = new ArrayList<Object>(worldClient.z());
        for (Object entityObject : loadedEntities) {
            Entity entity = new Entity(entityObject);
            EntityLivingBase livingEntity;
            if (ClientSettings.IS_LEGACY_1_7 && entity.isInstance(MappedClasses.FT)
                    || !entity.isInstance(MappedClasses.zm)
                    || !this.isValidTarget(livingEntity = new EntityLivingBase(entityObject))) {
                continue;
            }
            targets.add(livingEntity);
        }
        if (this.targetMode.getValue() == this.yawMode) {
            targets.sort(new EntityAngleComparator());
        } else if (this.targetMode.getValue() == this.distanceMode) {
            targets.sort(new EntityDistanceComparator());
        } else if (this.targetMode.getValue() == this.threatMode) {
            targets.sort(new EntityArmorValueComparator());
        } else if (this.targetMode.getValue() == this.armorMode) {
            targets.sort(new EntityEquipmentValueComparator());
        } else if (this.targetMode.getValue() == this.healthMode) {
            targets.sort(new EntityHealthComparator());
        }
        if (!targets.isEmpty()) {
            return targets.get(0);
        }
        return null;
    }

    private boolean passesItemFilter(EntityLivingBase target) {
        if (this.limitToItems.getEffectiveValue().booleanValue()) {
            ItemStack itemStack = Minecraft.thePlayer().getHeldItemHand();
            if (!this.allowedItems.isValid(itemStack, false)) {
                return false;
            }
            return this.targetFilter.isValidTarget(target);
        }
        return this.targetFilter.isValidTarget(target);
    }

    public ToggleSetting getStrafeIncrease() {
        return this.strafeIncrease;
    }

    public ToggleSetting getRequireMouseDown() {
        return this.requireMouseDown;
    }

    public SliderSetting getVerticalSpeed() {
        return this.verticalSpeed;
    }

    public boolean isValidTarget(EntityLivingBase target) {
        if (target.isNull()) {
            return false;
        }
        if (target.equals(Minecraft.thePlayer())) {
            return false;
        }
        if (target.w$src$F$15l9epb() <= 0.0f || target.M$src$Z$ff28xj()) {
            return false;
        }
        if (Minecraft.thePlayer().getDistanceToEntity(target) >= (float)((Double)this.distance.getValue()).intValue()) {
            return false;
        }
        if (RotationUtil.a(Minecraft.thePlayer(), target) > ((Double)this.maxAngle.getValue()).intValue() / 2) {
            return false;
        }
        if (Umbra.INSTANCE.getFriendManager().isFriend(target)) {
            return false;
        }
        if (this.ignoreInvisibles.getEffectiveValue() && target.i(PotionRegistry.R)) {
            return false;
        }
        if (target.equals(Minecraft.thePlayer().S$src$Lgg_umbra_wrapper_impl_Entity_$dgzs12())) {
            return false;
        }
        return this.passesItemFilter(target);
    }


    public SliderSetting getHorizontalSpeed() {
        return this.horizontalSpeed;
    }

    private boolean hasRequiredItem() {
        if (!this.limitToItems.getEffectiveValue().booleanValue()) {
            return true;
        }
        ItemStack itemStack = Minecraft.thePlayer().getHeldItemHand();
        return this.allowedItems.isValid(itemStack, false);
    }

    public AutoAim() {
        super("AimAssist", -327674, Category.COMBAT, "Smoothly aims to closest valid target");
        this.adaptiveTargeting = new AimAssistTargetingSubModule(this, "Adaptive");
        this.mode = OptionSetting.create((Object)this, "Mode", "Simple - Lightweight smooth aiming\nAdaptive - Advanced tracking with adaptive behavior", (ModeSelection)this.simpleRotation.getSelectionValue(), this.simpleRotation.getSelectionValue(), this.adaptiveTargeting.getSelectionValue());
        this.targetFilter = EntityTargetFilterValue.createForModule(this);
        this.requireMouseDown = ToggleSetting.create(this, "Require mouse down", true, "Only aim while mouse is down");
        this.aimVertically = ToggleSetting.create(this, "Aim vertically", false, "Aims up and down as well");
        this.strafeIncrease = ToggleSetting.create(this, "Strafe increase", false, "Increase speed while strafing away from target");
        this.checkBlockBreak = ToggleSetting.create(this, "Check block break", false, "Prevents from aiming while breaking blocks");
        this.breakBlocksWhitelist = ToggleSetting.create(this, "Break blocks whitelist", false);
        this.blockBreakItems = ItemFilterList.create(this, "aimassist-blockbreak-items", "Items", ItemFilterList.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("pickaxes"), new ItemLimitData("shovels")));
        this.limitToItems = ToggleSetting.create(this, "Limit to items", false, "AimAssist functions only while holding selected items");
        this.ignoreInvisibles = ToggleSetting.create(this, "Ignore invisible", false, "Does not aim at players with the invisibility effect");
        this.allowedItems = ItemFilterList.create(this, "aimassist-alloweditems", "Allowed Items", ItemFilterList.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
        this.verticalSpeed = SliderSetting.create(this, "Vertical speed", "#.#", "", 1.0, 5.0, 10.0);
        this.horizontalSpeed = SliderSetting.create(this, "Horizontal speed", "#.#", "", 1.0, 5.0, 10.0);
        this.maxAngle = SliderSetting.create(this, "Max angle", "#", "", 1.0, 180.0, 360.0, 1.0, "Maximum allowed angle to still aim at target");
        this.distance = SliderSetting.create(this, "Distance", "#.#", "", 1.0, 5.0, 8.0, 0.1, "Maximum distance allowed to still aim at target");
        this.distanceMode = new ModeOption("Distance");
        this.yawMode = new ModeOption("Yaw");
        this.armorMode = new ModeOption("Armor");
        this.threatMode = new ModeOption("Threat");
        this.healthMode = new ModeOption("Health");
        this.targetMode = OptionSetting.create((Object)this, "Target mode", "How Aimassist should prioritize targets\nArmor/Threat will default to Distance for non player targets", (ModeSelection)this.yawMode, this.yawMode, this.distanceMode, this.armorMode, this.threatMode, this.healthMode);
        this.centerMode = new ModeOption("Center");
        this.closestAreaMode = new ModeOption("Closest");
        this.targetArea = OptionSetting.create((Object)this, "Target area", "Where Aimassist will aim towards\nCenter: Center of entity\nClosest: Closest position on entity hitbox", (ModeSelection)this.centerMode, this.centerMode, this.closestAreaMode);
        this.aimVertically.addDependentValues(this.verticalSpeed);
        this.limitToItems.addDependentValues(this.allowedItems);
        this.limitToItems.setCompactListValue(this.allowedItems);
        this.breakBlocksWhitelist.setCompactListValue(this.blockBreakItems);
        this.breakBlocksWhitelist.addDependentValues(this.blockBreakItems);
        this.checkBlockBreak.addDependentValues(this.breakBlocksWhitelist);
        this.addValue(this.mode, this.targetFilter, this.requireMouseDown, this.strafeIncrease, this.ignoreInvisibles, this.checkBlockBreak, this.breakBlocksWhitelist, this.blockBreakItems, this.aimVertically, this.verticalSpeed, this.horizontalSpeed, this.maxAngle, this.distance, this.limitToItems, this.allowedItems, this.targetArea, this.targetMode);
        this.horizontalSpeed.setMaximumFractionDigits(0);
    }

    public ToggleSetting getAimVertically() {
        return this.aimVertically;
    }

    @Override
    public String getDetailedSuffix() {
        return this.horizontalSpeed.getDisplayValue();
    }

    @Nullable
    public EntityLivingBase getCurrentTarget() {
        if (this.simpleRotation.isSelectedSubModule()) {
            return this.simpleRotation.getTarget();
        }
        if (this.adaptiveTargeting.isSelectedSubModule()) {
            return this.adaptiveTargeting.getTarget();
        }
        return null;
    }

    public SliderSetting getMaxAngle() {
        return this.maxAngle;
    }

    public SliderSetting getDistance() {
        return this.distance;
    }

    public boolean canAim() {
        EntityPlayerSP player = Minecraft.thePlayer();
        PlayerControllerMP playerController = Minecraft.playerController();
        if (player.isNull() || playerController.isNull()) {
            return false;
        }
        if (SharedModuleControlClaims.movementInput.isLocked()) {
            return false;
        }
        boolean checkCurrentBlock = this.checkBlockBreak.getEffectiveValue();
        if (checkCurrentBlock && this.breakBlocksWhitelist.getEffectiveValue().booleanValue()) {
            checkCurrentBlock = this.blockBreakItems.matches(player.getHeldItemHand());
        }
        if (checkCurrentBlock) {
            RayTraceResult mouseOver = RayTraceUtil.o();
            boolean aimingAtBlock = mouseOver.isNotNull() && mouseOver.getTypeOfHit().equals(RayTraceResult_type.block());
            if (aimingAtBlock) {
                this.blockBreakCooldown = 250;
                return false;
            }
            if (this.blockBreakCooldown > 0) {
                --this.blockBreakCooldown;
            }
            if (this.blockBreakCooldown > 0) {
                return false;
            }
        }
        return this.hasRequiredItem();
    }

}

