package gg.umbra.hacks.pvp;

import gg.umbra.Umbra;
import gg.umbra.config.ClientSettings;
import gg.umbra.event.Event;
import gg.umbra.event.Listen;
import gg.umbra.event.impl.EventKeyPress;
import gg.umbra.event.impl.EventMouseButton;
import gg.umbra.event.impl.EventPreTick;
import gg.umbra.input.AttackKeyController;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.rotation.RotationManager;
import gg.umbra.utils.AttackCooldownUtil;
import gg.umbra.utils.RotationUtil;
import gg.umbra.utils.TimerUtil;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.EntityTargetFilterValue;
import gg.umbra.value.ItemFilterList;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.RandomRangeSetting;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityOtherPlayerMP;
import gg.umbra.wrapper.impl.EnumHand;
import gg.umbra.wrapper.impl.ItemStack;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.RayTraceResult;
import gg.umbra.wrapper.impl.RayTraceResult_type;
import java.util.Collections;
import java.util.Random;

public class AutoHit
extends HackModule {
    public final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
    private final TimerUtil attackTimer;
    private boolean waitingForFirstHit = false;
    public final SliderSetting earlyHitChance;
    private boolean missAttackPending = false;
    private final ToggleSetting selectFirstHit;
    private int lastHurtTime = 0;
    public final ToggleSetting shieldCheck;
    private final TimerUtil selectTimer;
    public final ItemFilterList allowedItems;
    private boolean graceActive = false;
    private long mouseOverDelayMs = 0L;
    private boolean mouseDown = false;
    private int selectTargetId = -1;
    public final ToggleSetting requireMouseDown;
    private final TimerUtil mouseOverTimer;
    private final Random random;
    private boolean releasePending = false;
    private final RandomRangeSetting extraDelay = RandomRangeSetting.createWithDescription(this, "Extra delay", "#", "ticks", -20.0, 0.0, 0.0, 20.0, 0.1, "Extra delay after attack cooldown(in ticks)\nNegative values will attack before cooldown is complete");
    public final SliderSetting targetMissChance;
    public final ToggleSetting airCrits;
    private int mouseOverTargetId = -1;
    private final RandomRangeSetting mouseOverDelayValue = RandomRangeSetting.createWithDescription(this, "Mouse over delay", "#", "ms", 0.0, 0.0, 0.0, 200.0, 10.0, "Delay after your crosshair reaches a target before attacking");
    private int earlyHitTicks = 0;
    public final ToggleSetting ignoreActivationClick;
    public final ToggleSetting limitToItems;

    public AutoHit() {
        super("Triggerbot", 0, Category.COMBAT, "");
        this.selectFirstHit = ToggleSetting.create(this, "Select first hit", false, "Waits for the opponent to hit you first before attacking");
        this.requireMouseDown = ToggleSetting.create(this, "Require mouse down", false);
        this.ignoreActivationClick = ToggleSetting.create(this, "Ignore activation click", false, "Ignores first manual click\n(unless already hovering a valid target with attack ready)");
        this.airCrits = ToggleSetting.create(this, "Air crits", false, "Won't attack in air unless you will crit");
        this.shieldCheck = ToggleSetting.create(this, "Shield check", false, "Won't attack players blocking with shield\nUsing HotbarSwap module with axes will override this behavior");
        this.targetMissChance = SliderSetting.create(this, "Target miss chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack without hovering a valid target when attack is ready");
        this.earlyHitChance = SliderSetting.create(this, "Early hit chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack earlier than attack is ready");
        this.limitToItems = ToggleSetting.create(this, "Limit to items", false, "Trigger functions only while holding selected items");
        this.allowedItems = ItemFilterList.create(this, "trigger-alloweditems", "Allowed Items", ItemFilterList.ALLOW_LIST_COLOR, Collections.emptyList());
        this.attackTimer = new TimerUtil();
        this.mouseOverTimer = new TimerUtil();
        this.selectTimer = new TimerUtil();
        this.random = new Random();
        this.requireMouseDown.addDependentValues(this.ignoreActivationClick);
        this.limitToItems.addDependentValues(this.allowedItems);
        this.addValue(this.targetFilter, this.extraDelay, this.mouseOverDelayValue, this.requireMouseDown, this.ignoreActivationClick, this.airCrits, this.shieldCheck, this.selectFirstHit, this.targetMissChance, this.earlyHitChance, this.limitToItems, this.allowedItems);
    }

    @Override
    public String getId() {
        return "triggerbot";
    }

    @Listen
    public void onTick(EventPreTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        int hurtTime = event.getThePlayer().c$src$I$15a9iwo();
        boolean wasJustHurt = this.lastHurtTime <= 0 && hurtTime > 0;
        this.lastHurtTime = hurtTime;
        if (this.requireMouseDown.getEffectiveValue().booleanValue() && this.mouseDown && !ClientSettings.isKeyBindingDown(Minecraft.gameSettings().F())) {
            this.mouseDown = false;
            this.resetSelectState();
        }
        if (this.selectFirstHit.getEffectiveValue() && this.waitingForFirstHit
                && this.selectTargetId != -1 && wasJustHurt) {
            this.waitingForFirstHit = false;
        }
        if (!this.attackTimer.hasTimeElapsed(50L)) {
            return;
        }
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
            return;
        }
        if (!this.canAttack(true)) {
            return;
        }
        float cooldownOffset = (float) (-this.extraDelay.getRandomRangeSetting()) + this.earlyHitTicks;
        RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (crosshairHit.isNotNull() && crosshairHit.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            Entity entity = crosshairHit.getEntity();
            if (this.isValidTarget(entity)) {
                if (this.updateSelectFirstHit(entity, wasJustHurt)
                        && this.hasMouseOverDelayElapsed(entity) && this.isAttackReady(cooldownOffset)) {
                    AttackKeyController.releaseAttackKey();
                    this.releasePending = AttackKeyController.requestSyntheticAttack(this);
                }
            } else {
                this.resetMouseOverState();
                this.updateSelectGrace();
            }
        } else {
            this.resetMouseOverState();
            this.updateSelectGrace();
            if (this.missAttackPending && this.isSelectFirstHitSatisfied()
                    && this.isAttackReady(cooldownOffset)) {
                AttackKeyController.releaseAttackKey();
                this.releasePending = AttackKeyController.requestSyntheticAttack(this);
                this.missAttackPending = false;
            }
        }
    }

    private void resetMouseOverState() {
        this.mouseOverTargetId = -1;
        this.mouseOverDelayMs = 0L;
        this.mouseOverTimer.reset();
    }

    private boolean isValidTarget(Entity entity) {
        if (this.shieldCheck.getEffectiveValue()) {
            boolean enforceShieldCheck = true;
            HotbarSwap hitSwap = Umbra.INSTANCE.getHackManager().getMod(HotbarSwap.class);
            if (hitSwap != null && hitSwap.isEnabled() && hitSwap.hasAlternateAxe()) {
                enforceShieldCheck = false;
            }
            if (enforceShieldCheck && entity.isInstance(MappedClasses.lG)
                    && RotationUtil.n(new EntityOtherPlayerMP(entity.getObject()))) {
                return false;
            }
        }
        return this.targetFilter.isValidTarget(entity);
    }

    private boolean isAttackReady(float cooldownOffset) {
        if (this.canBypassCooldown()) {
            return true;
        }
        return AttackCooldownUtil.isAttackReady(cooldownOffset);
    }

    private boolean canAttack(boolean requireActivation) {
        if (Minecraft.currentScreen().isNotNull() || !InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            return false;
        }
        if (requireActivation && (!InputEventDispatcher.getInstance().getFocusState().isFocused()
                || this.requireMouseDown.getEffectiveValue() && !this.mouseDown)) {
            return false;
        }
        ItemStack heldItem = Minecraft.thePlayer().getHeldItemHand();
        if (this.limitToItems.getEffectiveValue() && !this.allowedItems.isValid(heldItem, false)) {
            return false;
        }
        Entity player = Minecraft.thePlayer();
        if (this.airCrits.getEffectiveValue() && !player.b$src$Z$fqlxe4()) {
            double verticalMovement = player.N() - player.W();
            if (verticalMovement >= 0.0) {
                return false;
            }
        }
        return true;
    }

    private boolean canBypassCooldown() {
        if (!RotationUtil.u(Minecraft.thePlayer())) {
            return false;
        }
        HotbarSwap hitSwap = Umbra.INSTANCE.getHackManager().getMod(HotbarSwap.class);
        if (!hitSwap.isEnabled()) {
            ItemStack mainHandItem = Minecraft.thePlayer().i(EnumHand.mainHand());
            return mainHandItem.isNotNull() && mainHandItem.getItem().isInstance(MappedClasses.zx)
                    && RotationUtil.u(Minecraft.thePlayer());
        }
        return hitSwap.hasValidWeaponSwap();
    }

    private boolean hasMouseOverDelayElapsed(Entity entity) {
        int entityId = entity.S();
        if (this.mouseOverTargetId != entityId) {
            this.mouseOverTargetId = entityId;
            this.mouseOverDelayMs = (long) this.mouseOverDelayValue.getRandomRangeSetting();
            this.mouseOverTimer.reset();
        }
        return this.mouseOverTimer.hasTimeElapsed(this.mouseOverDelayMs);
    }

    private void resetSelectState() {
        this.selectTargetId = -1;
        this.waitingForFirstHit = false;
        this.graceActive = false;
        this.selectTimer.reset();
    }

    private boolean updateSelectFirstHit(Entity entity, boolean wasJustHurt) {
        if (!this.selectFirstHit.getEffectiveValue().booleanValue()) {
            this.resetSelectState();
            return true;
        }
        int entityId = entity.S();
        if (this.selectTargetId != entityId) {
            this.selectTargetId = entityId;
            this.waitingForFirstHit = true;
        }
        this.graceActive = false;
        this.selectTimer.reset();
        if (this.waitingForFirstHit && wasJustHurt) {
            this.waitingForFirstHit = false;
        }
        return !this.waitingForFirstHit;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mouseDown = false;
        this.missAttackPending = false;
        this.earlyHitTicks = 0;
        this.resetMouseOverState();
        this.resetSelectState();
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
            return;
        }
    }

    private boolean isSelectFirstHitSatisfied() {
        return this.selectFirstHit.getEffectiveValue() == false || this.selectTargetId == -1 || !this.waitingForFirstHit;
    }

    private void updateSelectGrace() {
        if (!this.selectFirstHit.getEffectiveValue().booleanValue() || this.selectTargetId == -1) {
            return;
        }
        if (!this.graceActive) {
            this.graceActive = true;
            this.selectTimer.reset();
            return;
        }
        if (this.selectTimer.hasTimeElapsed(1000L)) {
            this.resetSelectState();
        }
    }

    @Listen
    public void onKeyPress(EventKeyPress event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F())) {
            return;
        }
        this.handleInput(event.isDown(), event);
    }

    @Listen
    public void onMouseButton(EventMouseButton event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F())) {
            return;
        }
        this.handleInput(event.getButtonState(), event);
    }

    private void handleInput(boolean pressed, Event event) {
        if (this.requireMouseDown.getEffectiveValue() && pressed && !this.mouseDown) {
            this.mouseDown = true;
            if (this.ignoreActivationClick.getEffectiveValue().booleanValue() && this.canAttack(false)) {
                boolean cancelActivationClick = false;
                if (!this.isAttackReady(-this.extraDelay.getMinimumInt())) {
                    cancelActivationClick = true;
                }
                RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
                if (crosshairHit.isNotNull()
                        && crosshairHit.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                    cancelActivationClick = true;
                }
                if (cancelActivationClick) {
                    event.setCancelled(true);
                }
            }
            return;
        }
        if (pressed) {
            this.attackTimer.reset();
            int earlyHitPercent = (int) ((Double) this.earlyHitChance.getValue()).doubleValue();
            this.earlyHitTicks = earlyHitPercent > 0 && this.random.nextInt(100) < earlyHitPercent
                    ? 2 + this.random.nextInt(2) : 0;
            RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
            boolean hoveringValidTarget = false;
            if (crosshairHit.isNotNull()
                    && crosshairHit.getTypeOfHit().equals(RayTraceResult_type.entity())) {
                Entity entity = crosshairHit.getEntity();
                hoveringValidTarget = entity.isNotNull() && this.isValidTarget(entity);
            }
            int missPercent = (int) ((Double) this.targetMissChance.getValue()).doubleValue();
            this.missAttackPending = hoveringValidTarget && missPercent > 0
                    && this.random.nextInt(100) < missPercent;
        }
    }
}
