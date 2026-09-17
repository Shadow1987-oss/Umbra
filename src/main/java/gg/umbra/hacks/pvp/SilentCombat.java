package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Listen;
import gg.umbra.event.EventPriority;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.event.impl.EventRender3D;
import gg.umbra.input.AttackKeyController;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.input.KeyBindingHelper;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.hacks.pvp.silentaura.SilentAuraAimJitter;
import gg.umbra.hacks.pvp.silentaura.SilentAuraClicker;
import gg.umbra.hacks.pvp.silentaura.SilentAuraEntityIdComparator;
import gg.umbra.hacks.pvp.silentaura.SilentAuraRotationController;
import gg.umbra.system.SharedModuleControlClaims;
import gg.umbra.visual.GhostCamera;
import gg.umbra.rotation.AdaptiveRotationController;
import gg.umbra.rotation.MouseRotationController;
import gg.umbra.rotation.RotationAngles;
import gg.umbra.rotation.RotationControlClaim;
import gg.umbra.rotation.RotationManager;
import gg.umbra.unmap.ItemLimitData;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.utils.EntityAngleComparator;
import gg.umbra.utils.EntityArmorValueComparator;
import gg.umbra.utils.EntityDistanceComparator;
import gg.umbra.utils.EntityEquipmentValueComparator;
import gg.umbra.utils.EntityHealthComparator;
import gg.umbra.utils.MathUtil;
import gg.umbra.utils.RayTraceUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.Vec3d;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.RenderUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.ColorPicker;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomClickDelayValue;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.value.Value;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.GuiScreen;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.KeyBinding;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.TitledScreen;
import gg.umbra.wrapper.impl.Vec3;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SilentCombat
extends HackModule {
    private final ModeOption distanceMode;
    private final SilentAuraAimJitter xJitter;
    private long nextBreakBlockTime = 0L;
    private final SilentAuraAimJitter pitchJitter;
    public final ModeOption armorMode;
    public final ToggleSetting showTarget;
    private boolean perfectSwingAttackPending = false;
    private final RandomClickDelayValue attackRate;
    private float yawIntegralScale = 1.0f;
    public final ModeOption threatMode;
    public final SliderSetting maxAngle;
    private AdaptiveRotationController rotationController;
    public final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
    public final ModeOption closestMode;
    private final ToggleSetting switchTargets;
    private final ToggleSetting limitToItems;
    public final ModeOption yawMode;
    private final RotationControlClaim rotationClaim;
    private EntityLivingBase target = null;
    private static SilentAuraClicker clicker;
    private final RandomRangeSetting breakBlocksDelay;
    private boolean readyToAttack;
    public final ModeOption ringMode;
    private final ToggleSetting requireMouseDown;
    private final ItemFilterList allowedItems;
    private float yawIntegral = 0.0f;
    private float pitchIntegral = 0.0f;
    private final ToggleSetting perfectSwing;
    public final ModeOption centerMode;
    private float pitchProportionalScale = 1.0f;
    private final ColorPicker attackColor;
    private final SliderSetting aimSpeed = SliderSetting.createWithDescription(this, "Aim speed", "#.#", "", 1.0, 7.0, 10.0, "How fast aiming will be done silently");
    public final ModeOption healthMode;
    private boolean onTarget = false;
    private final ToggleSetting breakBlocksWhitelist;
    public final ToggleSetting disableOnDeath;
    private final SilentAuraAimJitter zJitter;
    private boolean breakingBlocks;
    private boolean toggledOff = false;
    public final ModeOption boxMode;
    public final SliderSetting extraSwingDistance = SliderSetting.createWithDescription(this, "Extra swing distance", "#.#", "", 0.0, 1.0, 3.0, "Extra distance past attack range at which aura will begin to engage, before attacking");
    private final ToggleSetting breakBlocks;
    private static GhostCamera freecam;
    private final ItemFilterList blockBreakItems;
    private final ColorPicker targetColor;
    private final Random random;
    private final TimerUtil breakBlocksTimer;
    public OptionSetting renderType;
    private float yawProportionalScale = 1.0f;
    private float pitchIntegralScale = 1.0f;
    public OptionSetting targetArea;
    public OptionSetting targetMode;
    private boolean deathHandled = false;

    public EntityLivingBase getTarget() {
        return this.target;
    }

    public RotationControlClaim getRotationClaim() {
        return this.rotationClaim;
    }

    public SliderSetting getAimSpeed() {
        return this.aimSpeed;
    }

    public RandomClickDelayValue getAttackRate() {
        return this.attackRate;
    }

    public ToggleSetting getLimitToItems() {
        return this.limitToItems;
    }

    public ItemFilterList getAllowedItems() {
        return this.allowedItems;
    }

    public ToggleSetting getRequireMouseDown() {
        return this.requireMouseDown;
    }

    @Listen
    public void onRender3D(EventRender3D eventRender3D) {
        if (this.showTarget.getEffectiveValue().booleanValue() && this.target != null && Minecraft.currentScreen().isNull()) {
            float ringRadius = this.target.isInstance(MappedClasses.Yl) || this.target.isInstance(MappedClasses.lG)
                    ? 0.7f : this.target.f$src$F$fst3ac();
            if (this.ringMode.isSelected()) {
                GuiRenderPrimitives.R(this.target.c(), this.target.A(), this.target.Z(), 50.0f, ringRadius, this.target.Y(), this.isLookingAtTarget() ? this.attackColor.getMutableColor() : this.targetColor.getMutableColor());
            } else {
                RenderUtil.k(this.target, 1.0, null, this.isLookingAtTarget() ? this.attackColor.getMutableColor() : this.targetColor.getMutableColor(), eventRender3D.getTicks());
            }
        }
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

    @Override
    public String getDetailedSuffix() {
        if (ForgeVersion.MC_1_12_2.d() && this.perfectSwing.getEffectiveValue().booleanValue()) {
            float attackStrength = Minecraft.thePlayer().getCooledAttackStrength(0.0f);
            if (attackStrength == 1.0f) {
                return "\u00a76Ready";
            }
            return String.format("%.1f", Float.valueOf(attackStrength));
        }
        return this.attackRate.getDisplayValue() + "cps";
    }

    public boolean isValidTarget(EntityLivingBase candidate) {
        if (candidate.isNull()) {
            return false;
        }
        if (candidate.equals(Minecraft.thePlayer())) {
            return false;
        }
        if (candidate.w$src$F$15l9epb() <= 0.0f || candidate.M$src$Z$ff28xj()) {
            return false;
        }
        if (!this.isInRange(candidate)) {
            return false;
        }
        if (RotationUtil.a(Minecraft.thePlayer(), candidate) > ((Double)this.maxAngle.getValue()).intValue() / 2) {
            return false;
        }
        if (Umbra.INSTANCE.getFriendManager().isFriend(candidate)) {
            return false;
        }
        if (candidate.equals(Minecraft.thePlayer().S$src$Lgg_umbra_wrapper_impl_Entity_$dgzs12())) {
            return false;
        }
        return this.passesItemFilter(candidate);
    }

    private boolean isInRange(EntityLivingBase target) {
        double[] aimCoordinates = this.computeAimCoords(target);
        double distance = Minecraft.thePlayer().i(aimCoordinates[0], aimCoordinates[1], aimCoordinates[2]);
        return distance <= this.getAttackRange();
    }

    private void randomizeGainFactors() {
        this.pitchProportionalScale = 0.85f + this.random.nextFloat() * 0.3f;
        this.pitchIntegralScale = 0.85f + this.random.nextFloat() * 0.3f;
        this.yawProportionalScale = 0.8f + this.random.nextFloat() * 0.4f;
        this.yawIntegralScale = 0.85f + this.random.nextFloat() * 0.3f;
    }

    public boolean isAttackCooldownReady() {
        if (ForgeVersion.MC_1_12_2.d() && this.perfectSwing.getEffectiveValue().booleanValue()) {
            float attackStrength = Minecraft.thePlayer().getCooledAttackStrength(0.0f);
            return attackStrength == 1.0f;
        }
        return this.attackRate.hasClickDelayElapsed();
    }

    private boolean handleBreakBlocks() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return false;
        }
        if (this.breakBlocks.getEffectiveValue().booleanValue() && this.breakBlocksTimer.hasTimeElapsed(this.nextBreakBlockTime)) {
            if (this.breakBlocksWhitelist.getEffectiveValue().booleanValue() && !this.blockBreakItems.matches(player.getHeldItemHand())) {
                return false;
            }
            if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
                return false;
            }
            RayTraceResult rayTraceResult = RayTraceUtil.o();
            if (rayTraceResult.isNotNull() && rayTraceResult.isBlockHit() && ClientSettings.isAttackButtonDown()) {
                KeyBinding keyBinding = Minecraft.gameSettings().F();
                KeyBindingHelper.updateKeyBinding(keyBinding, true, false);
                return true;
            }
            this.nextBreakBlockTime = (long)this.breakBlocksDelay.getRandomRangeSetting();
            this.breakBlocksTimer.reset();
        }
        return false;
    }

    @Override
    public String getId() {
        return "silentaura";
    }

    public SilentCombat() {
        super("SilentAura", -2420426, Category.COMBAT, "Simulates feel of Killaura\nAttacks and aims safely using built in AutoClicker to click, and Silent Aim system to aim");
        this.requireMouseDown = ToggleSetting.create(this, "Require mouse down", false);
        this.disableOnDeath = ToggleSetting.create(this, "Disable on death", false);
        this.showTarget = ToggleSetting.create(this, "Show target", false);
        this.switchTargets = ToggleSetting.create(this, "Switch", false, "Attacks other targets while current target is in hit cooldown");
        this.limitToItems = ToggleSetting.create(this, "Limit to items", false, "Aura functions only while holding selected items");
        this.breakBlocks = ToggleSetting.create(this, "Break blocks", false, "Prevents from aiming while attempting to break blocks");
        this.breakBlocksDelay = RandomRangeSetting.createWithDescription(this, "Break blocks delay", "#", "", 0.0, 0.0, 10.0, 2000.0, 1.0, "Delay in milliseconds before breaking blocks");
        this.breakBlocksWhitelist = ToggleSetting.create(this, "Break blocks whitelist", false);
        this.blockBreakItems = ItemFilterList.create(this, "SilentBlockBreakingItems", "Block breaking items", ItemFilterList.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("pickaxes"), new ItemLimitData("shovels")));
        this.attackRate = RandomClickDelayValue.create(this, "Attacks per Second", "#.#", "", 1.0, 6.0, 13.0, 20.0);
        this.maxAngle = SliderSetting.create(this, "Max angle", "#", "", 1.0, 120.0, 360.0, 5.0, "Angle at which targets will be acquired and aimed at\n(From your cursor)");
        this.targetColor = ColorPicker.createWithAlpha(this, "Target color", new Color(255, 200, 112), 50);
        this.attackColor = ColorPicker.create(this, "Attack color", new Color(255, 0, 0, 100));
        this.distanceMode = new ModeOption("Distance");
        this.yawMode = new ModeOption("Yaw");
        this.armorMode = new ModeOption("Armor");
        this.threatMode = new ModeOption("Threat");
        this.healthMode = new ModeOption("Health");
        this.targetMode = OptionSetting.create((Object)this, "Target mode", "How Aura should prioritize targets\nArmor/Threat will default to Distance for non player targets", (ModeSelection)this.distanceMode, this.distanceMode, this.yawMode, this.armorMode, this.threatMode, this.healthMode);
        this.allowedItems = ItemFilterList.create(this, "silentaura-alloweditems", "Allowed Items", ItemFilterList.ALLOW_LIST_COLOR, Collections.emptyList());
        this.centerMode = new ModeOption("Center");
        this.closestMode = new ModeOption("Closest");
        this.targetArea = OptionSetting.create((Object)this, "Target area", "Where Aura will aim towards\nCenter: Center of entity\nClosest: Closest position on entity hitbox", (ModeSelection)this.centerMode, this.centerMode, this.closestMode);
        this.ringMode = new ModeOption("Ring");
        this.boxMode = new ModeOption("Box");
        this.renderType = OptionSetting.create((Object)this, "Render type", this.ringMode, this.ringMode, this.boxMode);
        this.perfectSwing = ToggleSetting.create(this, "Perfect swing", false, "Only attacks when there is no attack cooldown\nAdditionally, only swings when hovering(trigger)");
        this.breakBlocksTimer = new TimerUtil();
        this.rotationClaim = SharedModuleControlClaims.rotation;
        this.random = new Random();
        this.pitchJitter = new SilentAuraAimJitter(-0.3, 0.25);
        this.xJitter = new SilentAuraAimJitter(-0.15, 0.15);
        this.zJitter = new SilentAuraAimJitter(-0.15, 0.15);
        this.perfectSwing.whenEqualTo(false).applyTo(this.attackRate);
        this.addValue(this.targetFilter, this.aimSpeed, this.attackRate, this.extraSwingDistance, this.maxAngle, this.targetMode, this.targetArea);
        this.showTarget.addDependentValues(this.targetColor, this.attackColor, this.renderType);
        this.breakBlocks.addDependentValues(this.breakBlocksDelay, this.breakBlocksWhitelist);
        this.breakBlocksWhitelist.addDependentValues(this.blockBreakItems);
        this.U(this.perfectSwing, ForgeVersion.MC_1_8_9.N());
        this.U(this.switchTargets, ForgeVersion.MC_1_8_9.H());
        this.addValue(new Value[]{this.disableOnDeath, this.breakBlocks, this.breakBlocksDelay, this.breakBlocksWhitelist, this.blockBreakItems, this.requireMouseDown, this.showTarget, this.targetColor, this.attackColor, this.renderType, this.limitToItems.addDependentValues(this.allowedItems), this.allowedItems});
        this.limitToItems.setCompactListValue(this.allowedItems);
        this.rotationClaim.setPriority(this, 5);
        this.attackRate.setMaximumFractionDigits(0);
    }


    public boolean canAttack() {
        if (Minecraft.theWorld().isNull()) {
            return false;
        }
        if (!this.isAttackCooldownReady()) {
            return false;
        }
        if (!this.readyToAttack) {
            return false;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        return !this.breakingBlocks;
    }

    @Listen
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
    }


    public boolean canClickAttack() {
        if (ForgeVersion.MC_1_12_2.d() && this.perfectSwing.getEffectiveValue().booleanValue()) {
            return false;
        }
        return this.canAttack();
    }

    private void updateRotationColors() {
        if (this.target != null && this.isControllingRotation()) {
            this.onTarget = this.isLookingAtTarget();
            this.rotationController.setSecondaryColor(this.attackColor.getMutableColor());
            this.rotationController.setPrimaryColor(this.targetColor.getMutableColor());
            this.rotationController.setUseSecondaryColor(this.onTarget);
        }
    }

    @Listen(priority=EventPriority.LOWEST)
    public void onTick(EventPreTick eventPreTick) {
        this.breakingBlocks = this.handleBreakBlocks();
        if (this.perfectSwingAttackPending) {
            this.perfectSwingAttackPending = false;
            AttackKeyController.releaseAttackKey();
        }
        if (Minecraft.thePlayer().isNull()) {
            this.resetTargeting();
            return;
        }
        if (this.toggledOff || !this.isEnabled() || this.breakingBlocks) {
            this.resetTargeting();
            return;
        }
        if (clicker == null) {
            clicker = Umbra.INSTANCE.getHackManager().getMod(SilentAuraClicker.class);
        }
        if (!clicker.isEnabled()) {
            clicker.setEnabled(true);
        }
        this.updateRotationColors();
        if (this.disableOnDeath.getEffectiveValue().booleanValue()) {
            if (Minecraft.thePlayer().M$src$Z$ff28xj() || Minecraft.thePlayer().w$src$F$15l9epb() <= 0.0f) {
                this.toggle();
                return;
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                GuiScreen guiScreen = Minecraft.currentScreen();
                if (guiScreen.isNotNull()) {
                    if (!this.deathHandled && guiScreen.isInstance(MappedClasses.D2)) {
                        this.deathHandled = true;
                        this.toggle();
                        return;
                    }
                    this.deathHandled = false;
                }
            } else {
                TitledScreen titledScreen = Minecraft.k();
                if (titledScreen.isNotNull()) {
                    String screenTitle = titledScreen.getDisplayedTitle();
                    if (!this.deathHandled && screenTitle != null && (screenTitle.toLowerCase().contains("died") || screenTitle.toLowerCase().contains("dead"))) {
                        this.deathHandled = true;
                        this.toggle();
                        return;
                    }
                    if (screenTitle == null || screenTitle.equals("")) {
                        this.deathHandled = false;
                    }
                }
            }
        }
        if (!InputEventDispatcher.getInstance().getFocusState().isFocused() || this.requireMouseDown.getEffectiveValue().booleanValue() && !ClientSettings.isAttackButtonDown()) {
            this.resetTargeting();
            return;
        }
        this.updateTarget();
        this.updateAim();
        if (ForgeVersion.MC_1_12_2.d() && this.perfectSwing.getEffectiveValue().booleanValue() && this.canAttack()) {
            AttackKeyController.releaseAttackKey();
            this.perfectSwingAttackPending = AttackKeyController.requestSyntheticAttack(this);
        }
    }

    private boolean isControllingRotation() {
        MouseRotationController mouseRotationController = RotationManager.INSTANCE.getActiveController();
        return mouseRotationController != null && mouseRotationController.equals(this.rotationController);
    }

    private void updateTarget() {
        ArrayList<EntityLivingBase> candidates = new ArrayList<EntityLivingBase>();
        if (this.shouldSkip()) {
            return;
        }
        ArrayList<?> loadedEntities = new ArrayList<Object>(Minecraft.theWorld().z());
        for (Object entityObject : loadedEntities) {
            Entity entity = new Entity(entityObject);
            EntityLivingBase candidate;
            if (ClientSettings.IS_LEGACY_1_7 && entity.isInstance(MappedClasses.FT)
                    || !entity.isInstance(MappedClasses.zm)
                    || !this.isValidTarget(candidate = new EntityLivingBase(entityObject))) {
                continue;
            }
            candidates.add(candidate);
        }
        if (this.targetMode.getValue() == this.yawMode) {
            candidates.sort(new EntityAngleComparator());
        } else if (this.targetMode.getValue() == this.distanceMode) {
            candidates.sort(new EntityDistanceComparator());
        } else if (this.targetMode.getValue() == this.threatMode) {
            candidates.sort(new EntityArmorValueComparator());
        } else if (this.targetMode.getValue() == this.armorMode) {
            candidates.sort(new EntityEquipmentValueComparator());
        } else if (this.targetMode.getValue() == this.healthMode) {
            candidates.sort(new EntityHealthComparator());
        }
        if (this.switchTargets.getEffectiveValue().booleanValue()) {
            candidates.sort(new SilentAuraEntityIdComparator());
        }
        if (!candidates.isEmpty()) {
            EntityLivingBase selectedTarget = candidates.get(0);
            boolean targetChanged = this.target != null && !this.target.equals(selectedTarget);
            if (this.target == null || targetChanged) {
                AttackKeyController.releaseAttackKey();
                this.randomizeGainFactors();
            }
            this.target = selectedTarget;
            if (!this.rotationClaim.isOwnedBy(this)) {
                this.rotationClaim.acquire(this, true);
            }
            this.readyToAttack = false;
            if (this.rotationController != null) {
                if (ForgeVersion.MC_1_12_2.d() && this.perfectSwing.getEffectiveValue().booleanValue()) {
                    RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
                    if (rayTrace.isNotNull() && rayTrace.getEntity().isNotNull() && rayTrace.getEntity().equals(this.target)) {
                        this.readyToAttack = true;
                    }
                } else {
                    double angularDistance = RotationUtil.L(this.target);
                    if (angularDistance < 3.0 && this.isInRange(this.target)) {
                        this.readyToAttack = true;
                    }
                }
            }
        } else {
            this.resetTargeting();
        }
    }

    private double[] computeAimCoords(EntityLivingBase target) {
        if (this.targetArea.getValue() == this.closestMode) {
            Vec3d closestPoint = RotationUtil.T(Minecraft.thePlayer(), target.R$src$Lgg_umbra_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0);
            double motionX = target.z() - target.f();
            double motionZ = target.h() - target.R();
            double currentX = closestPoint.getX();
            double currentY = target.N();
            double currentZ = closestPoint.getZ();
            double previousX = currentX - motionX;
            double previousY = target.H();
            double previousZ = currentZ - motionZ;
            return new double[]{currentX, currentY, currentZ, previousX, previousY, previousZ};
        }
        return new double[]{target.z(), target.N(), target.h(), target.f(), target.H(), target.R()};
    }

    private void updateAim() {
        if (Minecraft.theWorld().isNull()) {
            this.resetTargeting();
            return;
        }
        boolean hasValidTarget = this.target != null && this.isValidTarget(this.target);
        boolean ownsRotation = this.rotationClaim.isOwnedBy(this);
        if (hasValidTarget && ownsRotation) {
            this.resetJitter();
            EntityPlayerSP player = Minecraft.thePlayer();
            double[] aimCoordinates = this.computeAimCoords(this.target);
            double targetX = aimCoordinates[0];
            double targetY = aimCoordinates[1];
            double targetZ = aimCoordinates[2];
            double previousTargetX = aimCoordinates[3];
            double previousTargetZ = aimCoordinates[5];
            double distanceToTarget = player.i(targetX, targetY, targetZ);
            double targetMotionX = targetX - previousTargetX;
            double targetMotionZ = targetZ - previousTargetZ;
            double horizontalTargetMotion = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
            double jitteredTargetX = targetX + this.xJitter.getCurrentOffset() * (1.0 + horizontalTargetMotion);
            double jitteredTargetZ = targetZ + this.zJitter.getCurrentOffset() * (1.0 + horizontalTargetMotion);
            double targetHeight = this.target.Y();
            double playerEyeY = player.N() + 1.62;
            double jitteredTargetY = playerEyeY < targetY
                    ? targetY + this.pitchJitter.getCurrentOffset() * 0.5
                    : Math.min(playerEyeY, targetY + targetHeight) - 0.275 + this.pitchJitter.getCurrentOffset();
            if (this.rotationController == null) {
                this.rotationController = new SilentAuraRotationController(this);
                this.rotationController.setRelativeMode(false);
                this.rotationController.setRetainAfterCompletion(true);
                this.rotationController.setClampStepToRemaining(true);
                this.rotationController.setTolerance(0.0f);
                this.rotationController.setScaleAxesProportionally(false);
                this.rotationController.setLinearAcceleration(false);
                RotationManager.INSTANCE.setController(this.rotationController);
            } else {
                this.rotationController.setRelativeMode(false);
                this.rotationController.setScaleAxesProportionally(false);
                this.rotationController.setLinearAcceleration(false);
                RotationAngles desiredRotation = this.rotationController.calculateRotation(Vec3.create(jitteredTargetX, jitteredTargetY, jitteredTargetZ));
                float targetPitch = (float)RotationUtil.h(player, targetX, jitteredTargetY, targetZ);
                float managedYaw = RotationManager.INSTANCE.getManagedYaw();
                float managedPitch = RotationManager.INSTANCE.getManagedPitch();
                float yawError = MathUtil.wrapAngleTo180(desiredRotation.getYaw() - managedYaw);
                float pitchError = MathUtil.wrapAngleTo180(targetPitch - managedPitch);
                float previousPitchStep = managedPitch - RotationManager.INSTANCE.getPreviousManagedPitch();
                float previousYawStep = managedYaw - RotationManager.INSTANCE.getPreviousManagedYaw();
                float integrationStep = 0.05f;
                boolean yawContinuingInSameDirection = Math.signum(yawError) == Math.signum(previousYawStep);
                double playerHorizontalSpeed = Math.sqrt(player.t() * player.t() + player.T() * player.T());
                float pitchProportionalGain = 0.45f * this.pitchProportionalScale;
                float pitchIntegralGain = 0.91f * this.pitchIntegralScale;
                float yawProportionalGain = (this.onTarget ? 0.05f : 0.1f) * this.yawProportionalScale;
                float yawIntegralGain = 0.33f * this.yawIntegralScale;
                double playerVerticalMotion = player.q();
                if (Math.abs(playerVerticalMotion) > 0.1) {
                    pitchError *= (float)(1.0 + Math.random() * 0.32);
                }
                if (yawContinuingInSameDirection && Math.abs(yawError) < 20.0f) {
                    yawProportionalGain *= 2.5f;
                    previousYawStep *= (float)(1.0 + Math.min(horizontalTargetMotion + playerHorizontalSpeed, 0.25));
                }
                if (distanceToTarget < 0.8) {
                    double distanceScale = distanceToTarget / 0.8;
                    pitchError *= (float)(distanceScale * distanceScale);
                    yawError *= (float)distanceScale;
                }
                float pitchControlError = pitchError - previousPitchStep
                        + previousYawStep * integrationStep * (float)(Math.random() >= 0.5 ? -1 : 1);
                float yawControlError = yawError - previousYawStep;
                this.pitchIntegral += pitchControlError * integrationStep;
                this.yawIntegral += yawControlError * integrationStep;
                float pitchAdjustment = pitchProportionalGain * pitchControlError + pitchIntegralGain * this.pitchIntegral;
                float yawAdjustment = yawProportionalGain * yawControlError + yawIntegralGain * this.yawIntegral;
                if (Math.abs(yawError) > 120.0f) {
                    this.yawIntegral = 0.0f;
                    yawAdjustment = 0.0f;
                }
                if (Minecraft.currentScreen().isNotNull()) {
                    this.yawIntegral = 0.0f;
                    this.pitchIntegral = 0.0f;
                }
                this.rotationController.setTargetRotation(managedYaw + yawError + yawAdjustment / 3.0f, managedPitch + pitchAdjustment);
            }
            if (RotationManager.INSTANCE.getActiveController() == null || !this.isControllingRotation() && RotationManager.INSTANCE.hasAdaptiveController()) {
                RotationManager.INSTANCE.setController(this.rotationController);
            }
        } else {
            this.resetTargeting();
        }
    }

    @Override
    public void setEnabled(boolean enabledState, boolean bypassVisibilityCheck) {
        this.pitchIntegral = 0.0f;
        this.yawIntegral = 0.0f;
        if (!enabledState && this.isControllingRotation()) {
            this.toggledOff = !this.toggledOff;
        } else {
            this.toggledOff = false;
            super.setEnabled(enabledState, bypassVisibilityCheck);
        }
    }

    private boolean isLookingAtTarget() {
        RayTraceResult rayTraceResult;
        return this.target != null
                && this.isControllingRotation()
                && this.isInRange(this.target)
                && (rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace()) != null
                && rayTraceResult.isNotNull()
                && this.target.equals(rayTraceResult.getEntity());
    }

    public void resetTargeting() {
        this.pitchIntegral = 0.0f;
        this.yawIntegral = 0.0f;
        this.target = null;
        this.readyToAttack = false;
        if (this.rotationController != null && this.isControllingRotation()) {
            this.rotationController.setScaleAxesProportionally(true);
            this.rotationController.setLinearAcceleration(true);
            RotationManager.INSTANCE.releaseController(this.rotationController);
        }
        if (RotationManager.INSTANCE.getActiveController() == null || RotationManager.INSTANCE.getActiveController() != this.rotationController || this.rotationController != null && this.rotationController.isRelativeMode() && this.rotationController.isComplete()) {
            this.rotationController = null;
            this.rotationClaim.release(this);
            if (this.toggledOff) {
                this.toggledOff = false;
                super.setEnabled(false, true);
            }
        }
    }

    private double getAttackRange() {
        return 3.0 + (Double)this.extraSwingDistance.getValue();
    }

    private void resetJitter() {
        this.pitchJitter.update();
        this.xJitter.update();
        this.zJitter.update();
    }

    private boolean shouldSkip() {
        if (freecam == null) {
            freecam = Umbra.INSTANCE.getHackManager().getMod(GhostCamera.class);
        }
        return this.toggledOff || freecam != null && freecam.isEnabled() || this.breakingBlocks || this.rotationClaim.isBlockedFor(this) && !this.rotationClaim.acquire(this, true);
    }

    @Override
    public void onDisable() {
        if (this.rotationController != null) {
            this.rotationController = null;
        }
        if (this.perfectSwingAttackPending) {
            AttackKeyController.releaseAttackKey();
            this.perfectSwingAttackPending = false;
        }
        this.rotationClaim.release(this);
    }
}
