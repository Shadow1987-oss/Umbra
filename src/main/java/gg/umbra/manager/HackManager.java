package gg.umbra.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.config.ConfigJsonUtils;
import gg.umbra.config.Profile;
import gg.umbra.event.Listen;
import gg.umbra.event.EventListener;
import gg.umbra.event.impl.EventModStateChange;
import gg.umbra.module.Category;
import gg.umbra.module.MinecraftVersionConstraint;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.hacks.exploits.AutoAnchor;
import gg.umbra.hacks.exploits.AntiBot;
import gg.umbra.hacks.exploits.AutoHeal;
import gg.umbra.hacks.exploits.HitRewind;
import gg.umbra.hacks.exploits.PacketHold;
import gg.umbra.hacks.exploits.BlockIn;
import gg.umbra.hacks.exploits.BridgeBuilder;
import gg.umbra.hacks.exploits.AutoLadder;
import gg.umbra.hacks.exploits.Fly;
import gg.umbra.hacks.exploits.HitBoxes;
import gg.umbra.hacks.exploits.InvWalk;
import gg.umbra.hacks.exploits.KeepSprint;
import gg.umbra.hacks.exploits.KillAura;
import gg.umbra.hacks.exploits.FallProtect;
import gg.umbra.hacks.exploits.NoSlowdown;
import gg.umbra.hacks.exploits.SafeWalk;
import gg.umbra.hacks.exploits.Speed;
import gg.umbra.hacks.exploits.Timer;
import gg.umbra.hacks.exploits.Phase;
import gg.umbra.hacks.exploits.Strafe;
import gg.umbra.hacks.exploits.Step;
import gg.umbra.hacks.exploits.LongJump;
import gg.umbra.hacks.exploits.AntiFall;
import gg.umbra.hacks.exploits.Regen;
import gg.umbra.hacks.exploits.PotionSaver;
import gg.umbra.hacks.exploits.OmniSprint;
import gg.umbra.hacks.exploits.AntiFML;
import gg.umbra.hacks.pvp.*;
import gg.umbra.hacks.pvp.AutoCity;
import gg.umbra.hacks.pvp.AutoTrap;
import gg.umbra.hacks.pvp.Surround;
import gg.umbra.tools.InventoryFill;
import gg.umbra.tools.PearlCatch;
import gg.umbra.hacks.pvp.PacketKnockback;
import gg.umbra.hacks.pvp.silentaura.SilentAuraClicker;
import gg.umbra.visual.BedPlates;
import gg.umbra.worldmods.MurderFinder;
import gg.umbra.settings.ClientSettings;
import gg.umbra.settings.MouseDelayFix;
import gg.umbra.visual.BlockFinder;
import gg.umbra.settings.TextGuiSettings;
import gg.umbra.worldmods.OreHighlight;
import gg.umbra.worldmods.AntiAFK;
import gg.umbra.visual.Animations;
import gg.umbra.visual.AntiDebuff;
import gg.umbra.visual.Arrows;
import gg.umbra.visual.Chams;
import gg.umbra.visual.ESP;
import gg.umbra.visual.PropHunt;
import gg.umbra.visual.GhostCamera;
import gg.umbra.visual.BrightMode;
import gg.umbra.visual.Health;
import gg.umbra.visual.HoleESP;
import gg.umbra.visual.Breadcrumbs;
import gg.umbra.visual.Radar2D;
import gg.umbra.visual.Indicators;
import gg.umbra.visual.LootHighlight;
import gg.umbra.visual.EntityLabels;
import gg.umbra.visual.Explosions;
import gg.umbra.visual.SpawnerFinder;
import gg.umbra.visual.ContainerHighlight;
import gg.umbra.visual.DirectionLines;
import gg.umbra.visual.Trajectories;
import gg.umbra.visual.Tracers;
import gg.umbra.visual.hud.ArmorStatusHudModule;
import gg.umbra.visual.hud.BlockOverlayHudModule;
import gg.umbra.visual.hud.BlockRenderColorOverrideHudModule;
import gg.umbra.visual.hud.BlockhitAnimationHudModule;
import gg.umbra.visual.hud.ClockHudModule;
import gg.umbra.visual.hud.CompassHudModule;
import gg.umbra.visual.hud.CoordinatesHudModule;
import gg.umbra.visual.hud.FpsDisplayHudModule;
import gg.umbra.visual.hud.FreeLookHudModule;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.visual.hud.InventoryBlurHudModule;
import gg.umbra.visual.hud.KeystrokesHudModule;
import gg.umbra.visual.hud.NoClickDelayHudModule;
import gg.umbra.visual.hud.NoFogHudModule;
import gg.umbra.visual.hud.NoHurtCameraHudModule;
import gg.umbra.visual.hud.NoHurtDelayHudModule;
import gg.umbra.visual.hud.PotionEffectsHudModule;
import gg.umbra.visual.hud.PingHudModule;
import gg.umbra.visual.hud.ReachDisplayHudModule;
import gg.umbra.visual.hud.ScoreboardHudModule;
import gg.umbra.visual.hud.TimeChangerHudModule;
import gg.umbra.visual.hud.WeatherChangerHudModule;
import gg.umbra.visual.proj.Projectiles;
import gg.umbra.tools.ArmorSwitch;
import gg.umbra.tools.AutoArmor;
import gg.umbra.tools.InvCleaner;
import gg.umbra.tools.MLG;
import gg.umbra.tools.AutoPearl;
import gg.umbra.tools.AutoTool;
import gg.umbra.tools.AutoTotem;
import gg.umbra.tools.Clutch;
import gg.umbra.tools.InventoryManager;
import gg.umbra.tools.AutoHotbar;
import gg.umbra.tools.AutoFish;
import gg.umbra.tools.AutoLog;
import gg.umbra.tools.AutoWalk;
import gg.umbra.tools.AutoGG;
import gg.umbra.tools.AutoTip;
import gg.umbra.tools.AutoPlay;
import gg.umbra.tools.AutoSneak;
import gg.umbra.tools.NameProtect;
import gg.umbra.tools.ChatFilter;
import gg.umbra.tools.AutoReply;
import gg.umbra.tools.AutoFood;
import gg.umbra.tools.Panic;
import gg.umbra.tools.Parkour;
import gg.umbra.tools.Refill;
import gg.umbra.tools.ThrowDebuff;
import gg.umbra.tools.Throwpot;
import gg.umbra.tools.WindCharge;
import gg.umbra.tools.inventory.InventoryActionModule;
import gg.umbra.worldmods.BedBreaker;
import gg.umbra.worldmods.AutoBuy;
import gg.umbra.worldmods.AutoOpen;
import gg.umbra.worldmods.LootStealer;
import gg.umbra.worldmods.BreakAll;
import gg.umbra.worldmods.FastPlace;
import gg.umbra.worldmods.FakeLag;
import gg.umbra.notification.NotificationType;
import gg.umbra.notification.ReusableTextNotification;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SubHackValue;
import gg.umbra.value.Value;
import gg.umbra.wrapper.impl.ForgeVersion;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class HackManager
implements EventListener {
    private static GuiComponent[] legacyCategoryComponents;
    private Map<Class<? extends HackModule>, HackModule> allModulesByType;
    private ArrayList<HackModule> activeModuleList;
    private HashSet<HackModule> enabledModules;
    private OreHighlight xrayModule;
    private ReusableTextNotification profileSwitchNotification;
    private Map<Class<? extends HackModule>, HackModule> activeModulesByType;
    private boolean suppressStateNotifications;

    public Collection<HackModule> getTopLevelModules() {
        ArrayList<HackModule> topLevelModules = new ArrayList<HackModule>();
        for (HackModule mod : this.getAllModules()) {
            if (mod instanceof SubHack) continue;
            topLevelModules.add(mod);
        }
        return topLevelModules;
    }

    public void init() {
        GuiComponent[] legacyComponentsSnapshot = Category.getLegacyComponents();
        HackModule[] coreModules = new HackModule[64];
        coreModules[0] = new ClientSettings();
        coreModules[1] = new ClickerLeft();
        coreModules[2] = new ClickerRight();
        coreModules[3] = new KnockbackReducer();
        coreModules[4] = new FallReset();
        coreModules[5] = new PacketKnockback();
        coreModules[6] = new Reach();
        coreModules[7] = new Throwpot();
        coreModules[8] = new Refill();
        coreModules[9] = new DirectionLines();
        coreModules[10] = new EntityLabels();
        coreModules[11] = new BlockFinder();
        coreModules[12] = new ESP();
        coreModules[13] = new LootStealer();
        coreModules[14] = new KeepSprint();
        coreModules[15] = new FastPlace();
        coreModules[16] = new HitBoxes();
        coreModules[17] = new SpawnerFinder();
        coreModules[18] = new ContainerHighlight();
        coreModules[19] = new BridgeBuilder();
        coreModules[20] = new BrightMode();
        coreModules[21] = new SprintReset();
        coreModules[22] = new AutoArmor();
        coreModules[23] = new InvCleaner();
        coreModules[24] = new ThrowDebuff();
        coreModules[25] = new AutoTool();
        coreModules[26] = new AutoAim();
        coreModules[27] = new Trajectories();
        coreModules[28] = new AntiDebuff();
        coreModules[29] = new SafeWalk();
        coreModules[30] = new Projectiles();
        coreModules[31] = new Fly();
        coreModules[32] = new KillAura();
        coreModules[33] = new Arrows();
        coreModules[34] = new PacketHold();
        coreModules[35] = new AutoPearl();
        coreModules[36] = new Panic();
        coreModules[37] = new AntiAFK();
        coreModules[38] = new ArmorSwitch();
        coreModules[39] = new LootHighlight();
        coreModules[40] = new MLG();
        coreModules[41] = new AutoHotbar();
        coreModules[42] = new AutoHeal();
        coreModules[43] = new PropHunt();
        coreModules[44] = new Parkour();
        coreModules[45] = new MurderFinder();
        coreModules[46] = new ProjectileAim();
        coreModules[47] = new Indicators();
        coreModules[48] = new Sprint();
        coreModules[49] = new Health();
        coreModules[50] = new TargetSelect();
        coreModules[51] = new Animations();
        SilentCombat silentAura = new SilentCombat();
        coreModules[52] = silentAura;
        coreModules[53] = new SilentAuraClicker(silentAura);
        coreModules[54] = new HitBypass();
        coreModules[55] = new Clutch();
        coreModules[56] = new InventoryManager();
        coreModules[57] = new BlockHit();
        coreModules[58] = new Timer();
        coreModules[59] = new InventoryFill();
        coreModules[60] = new BedPlates();
        coreModules[61] = new Criticals();
        coreModules[62] = new AutoWalk();
        coreModules[63] = new AutoLog();
        this.registerModules(Stream.of(coreModules));
        ModRegistrationBuilder.create().setModule(new Explosions()).addVersionConstraint(ForgeVersion.MC_1_16_5.b()).registerWith(this);
        HackModule[] versionConstrainedModules = new HackModule[2];
        versionConstrainedModules[0] = new Chams();
        OreHighlight xRay = new OreHighlight();
        HackManager modManager = this;
        modManager.xrayModule = xRay;
        versionConstrainedModules[1] = xRay;
        this.registerModules(Stream.of(versionConstrainedModules), HackManager::addMinecraft1165Constraint);
        GuiComponent[] preservedLegacyComponents = legacyComponentsSnapshot;
        ModRegistrationBuilder.create().setModule(new GhostCamera()).addVersionConstraint(ForgeVersion.MC_1_16_5.b()).addVersionConstraint(ForgeVersion.MC_1_21_11.n()).registerWith(this);
        this.registerModules(Stream.of(new InvWalk()), HackManager::addMinecraft189Constraint);
        this.registerModules(Stream.of(new HitRewind()), HackManager::addBacktrackVersionConstraints);
        this.registerModules(Stream.of(new AutoFish(), new BedBreaker(), new BlockIn(), new FakeLag()), HackManager::addMinecraft1710Constraint);
        this.registerModules(Stream.of(new AntiBot()));
        this.registerModules(Stream.of(new HoleESP(), new Breadcrumbs(), new Radar2D()));
        this.registerModules(Stream.of(new AutoLadder(), new KnockbackDelay(), new HitSelect()));
        this.registerModules(Stream.of(new PearlCatch(), new Tracers()));
        this.registerModules(Stream.of(new AutoGG(), new AutoTip()));
        this.registerModules(Stream.of(new AutoPlay(), new AutoSneak(), new AutoBuy()));
        this.registerModules(Stream.of(new NameProtect(), new ChatFilter(), new AutoReply(), new AutoFood()));
        this.registerModules(Stream.of(new BreakAll(), new AutoOpen(), new AutoCity(), new Surround(), new AutoTrap()));
        this.registerModules(Stream.of(new AutoHit(), new HotbarSwap(), new AutoAnchor(), new WindCharge(), new ObsidianCracker(), new AutoTotem(), new ShieldCracker(), new AutoMace(), new HitSwap()), HackManager::addMinecraft1214Constraint);
        this.registerModules(Stream.of(new FallProtect(), new NoSlowdown(), new Speed()), HackManager::addModernMinecraftConstraint);
        this.registerModules(Stream.of(new Phase(), new Strafe(), new Step(), new LongJump(), new AntiFall(), new Regen(), new PotionSaver(), new OmniSprint(), new AntiFML()));
        this.registerTextGuiSettings();
        this.registerHudModules();
        if (preservedLegacyComponents == null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
        }
    }

    public boolean getState(Class<? extends HackModule> clazz) {
        for (HackModule mod : this.collectMods()) {
            if (!mod.getClass().equals(clazz)) continue;
            return mod.isEnabled();
        }
        return false;
    }

    private static void addMinecraft1206Constraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_20_6.b());
    }

    public JsonObject getEnabledNonHudModuleStates() {
        JsonObject enabledStates = new JsonObject();
        for (HackModule mod : this.getAllModules()) {
            if (mod instanceof HudModule || !mod.isEnabled()) continue;
            enabledStates.addProperty(mod.getId(), Boolean.valueOf(mod.isEnabled()));
        }
        return enabledStates;
    }

    private static boolean hasModuleState(JsonObject states, HackModule mod) {
        return states.has(mod.getId()) || states.has(mod.getName());
    }

    private static boolean getModuleState(JsonObject states, HackModule mod) {
        return states.get(states.has(mod.getId()) ? mod.getId() : mod.getName()).getAsBoolean();
    }

    public void applyProfileModuleStates(Profile profile) {
        this.suppressStateNotifications = true;
        JsonObject enabledStates = profile.getEnabledModuleStates();
        int enabledCount = 0;
        for (HackModule mod : this.collectMods()) {
            if (mod instanceof HudModule || mod.getCategory().equals(Category.NONE)) continue;
            try {
                if (hasModuleState(enabledStates, mod)) {
                    if (!mod.isVisible()) continue;
                    try {
                        if (mod.isEnabled()) continue;
                        mod.setEnabled(getModuleState(enabledStates, mod));
                        ++enabledCount;
                    }
                    catch (Exception exception) {
                        Umbra.logThrowable(exception);
                    }
                    continue;
                }
                if (mod instanceof ClientSettings || mod.getGuiColor() == 0 || !mod.isEnabled()) continue;
                mod.toggle();
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
        this.suppressStateNotifications = false;
        if (Umbra.INSTANCE.getPublicProfileSettings().profileSwitchNotifications.getEffectiveValue().booleanValue()) {
            this.profileSwitchNotification.withTitle("Profile swap to " + gg.umbra.config.ClientSettings.FORMAT_CODE + "6" + profile.getName())
                    .withMessage(enabledCount + " modules enabled").reset();
            Umbra.INSTANCE.getNotificationManager().show(this.profileSwitchNotification);
        }
    }

    private static void addModernMinecraftConstraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_21_4.b());
    }

    public void initializeAllModules() {
        for (HackModule mod : this.allModulesByType.values()) {
            mod.I();
        }
    }

    private void registerModules(Stream<HackModule> modules, Consumer<ModRegistrationBuilder<?>> customizer) {
        modules.forEach(module -> this.registerModuleWithCustomizer(customizer, module));
    }

    public HashSet<HackModule> getMods() {
        return this.enabledModules;
    }

    public static void setLegacyCategoryComponents(GuiComponent[] components) {
        legacyCategoryComponents = components;
    }

    private void registerModules(Stream<HackModule> modules) {
        this.registerModules(modules, HackManager::noAdditionalConstraints);
    }

    void registerModule(HackModule module, List<List<MinecraftVersionConstraint>> constraintGroups, boolean enableImmediately) {
        this.allModulesByType.put(module.getClass(), module);
        if (!constraintGroups.isEmpty()) {
            boolean supported = false;
            for (List<MinecraftVersionConstraint> constraints : constraintGroups) {
                List<MinecraftVersionConstraint> activeConstraints = MinecraftVersionConstraint.o(constraints);
                if (!activeConstraints.isEmpty()) continue;
                supported = true;
            }
            if (!supported) {
                return;
            }
        }
        this.activeModulesByType.put(module.getClass(), module);
        this.activeModuleList.add(module);
        for (Value<?, ?> value : module.getValues()) {
            if (!(value instanceof OptionSetting)) continue;
            OptionSetting modeValue = (OptionSetting)value;
            for (ModeSelection modeSelection : modeValue.getModes()) {
                SubHackValue subModuleValue;
                if (!(modeSelection instanceof SubHackValue) || !((SubHack)(subModuleValue = (SubHackValue)modeSelection).getInstance()).isSubModuleEnabled()) continue;
                this.registerModule((HackModule)subModuleValue.getInstance(), constraintGroups, false);
                module.registerSubModule(new SubHack[]{subModuleValue.getInstance()});
            }
        }
        if (enableImmediately) {
            module.setEnabled(true);
        }
    }

    private void registerTextGuiSettings() {
        ModRegistrationBuilder.create().setModule(new TextGuiSettings()).registerWith(this);
    }

    private void registerModuleWithCustomizer(Consumer consumer, HackModule module) {
        ModRegistrationBuilder<HackModule> builder = ModRegistrationBuilder.create().setModule(module);
        consumer.accept(builder);
        builder.registerWith(this);
    }

    public void finishModuleInitialization() {
        for (HackModule mod : this.activeModulesByType.values()) {
            mod.onFinishModuleInitialization();
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            this.getMod(NoClickDelayHudModule.class).setEnabled(true);
            this.getMod(MouseDelayFix.class).setEnabled(true);
            if (!this.getMod(MouseDelayFix.class).isEnabled()) {
                this.getMod(MouseDelayFix.class).setEnabled(true);
            }
        }
    }

    public String buildTranslationTemplate() {
        StringBuilder translations = new StringBuilder();
        for (HackModule mod : this.collectMods()) {
            String moduleName = mod.getName();
            String moduleKey = moduleName.replace(" ", "_").toLowerCase();
            translations.append(moduleKey + "=" + moduleName);
            translations.append("\n");
            if (mod.getToolTip() != null && !mod.getToolTip().equals("")) {
                translations.append(moduleKey + ".tooltip=" + mod.getToolTip().replace("\n", " "));
                translations.append("\n");
            }
            for (Value<?, ?> value : mod.getValues()) {
                String valueName = value.getName();
                String valueKey = moduleKey + "." + value.getName().replace(" ", "_").toLowerCase();
                translations.append(valueKey + "=" + valueName);
                translations.append("\n");
                if (value.getDescription() == null || value.getDescription().isEmpty()) continue;
                String description = value.getDescription().replace("\n", " ");
                String tooltipKey = valueKey + ".tooltip";
                translations.append(tooltipKey + "=" + description);
                translations.append("\n");
            }
        }
        return translations.toString();
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    @Listen
    public void onModuleStateChanged(EventModStateChange eventModStateChange) {
        HackModule mod = eventModStateChange.getModule();
        if (mod.isEnabled()) {
            this.enabledModules.add(mod);
        } else {
            this.enabledModules.remove(mod);
        }
        for (HackModule mod2 : this.getMods()) {
            mod2.U(mod);
        }
        if (ClientSettings.INSTANCE.isInputEnabled() && mod.q$src$Z$12h8h4c() && Umbra.INSTANCE.getPublicProfileSettings().toggleAlerts.getEffectiveValue().booleanValue() && !this.suppressStateNotifications) {
            mod.showToggleNotification();
        }
    }

    @Listen
    public void onModuleEnabled(EventModStateChange eventModStateChange) {
        HackModule mod = eventModStateChange.getModule();
        if (eventModStateChange.isEnabled()) {
            mod.j();
        }
    }

    private static void addMinecraft1214Constraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_21_4.n());
    }

    static {
        HackManager.setLegacyCategoryComponents(new GuiComponent[4]);
    }

    private void registerHudModules() {
        ModRegistrationBuilder.create().setModule(new FreeLookHudModule()).registerWith(this);
        ModRegistrationBuilder.create().setModule(new NoClickDelayHudModule()).addVersionConstraint(ForgeVersion.MC_1_7_10.N()).registerWith(this);
        ModRegistrationBuilder.create().setModule(new MouseDelayFix()).addVersionConstraint(ForgeVersion.MC_1_8_9.S()).registerWith(this);
        this.registerModules(Stream.of(new KeystrokesHudModule(), new ClockHudModule(), new PotionEffectsHudModule()));
        ModRegistrationBuilder.create().setModule(new BlockhitAnimationHudModule()).addVersionConstraint(ForgeVersion.MC_1_8_9.S()).registerWith(this);
        this.registerModules(Stream.of(new NoHurtDelayHudModule(), new ArmorStatusHudModule(), new CompassHudModule(), new WeatherChangerHudModule(), new NoHurtCameraHudModule(), new TimeChangerHudModule(), new CoordinatesHudModule(), new FpsDisplayHudModule(), new PingHudModule(), new ReachDisplayHudModule(), new NoFogHudModule(), new BlockOverlayHudModule()));
        ModRegistrationBuilder.create().setModule(new BlockRenderColorOverrideHudModule()).addVersionConstraints(ForgeVersion.MC_1_7_10.N(), ForgeVersion.MC_1_16_5.b()).registerWith(this);
        this.registerModules(Stream.of(new ScoreboardHudModule(), new InventoryBlurHudModule()), HackManager::addMinecraft1206Constraint);
    }

    public int countEnabledModules(Category category) {
        int count = 0;
        for (HackModule mod : this.activeModulesByType.values()) {
            if (mod.getCategory() != category || !mod.isEnabled()) continue;
            ++count;
        }
        return count;
    }

    @Nullable
    public OreHighlight getXRayModule() {
        return this.xrayModule;
    }

    public JsonArray toJson(boolean includeDefaults) {
        JsonArray serializedModules = new JsonArray();
        for (HackModule mod : this.getTopLevelModules()) {
            JsonObject serializedModule = mod.toJson(includeDefaults);
            if (serializedModule == null) continue;
            serializedModules.add((JsonElement)serializedModule);
        }
        return serializedModules;
    }

    public JsonObject getEnabledHudModuleStates() {
        JsonObject enabledStates = new JsonObject();
        for (HackModule mod : this.getAllModules()) {
            if (!(mod instanceof HudModule)) continue;
            enabledStates.addProperty(mod.getId(), Boolean.valueOf(mod.isEnabled()));
        }
        return enabledStates;
    }

    public static GuiComponent[] getLegacyCategoryComponents() {
        return legacyCategoryComponents;
    }

    private static void addBacktrackVersionConstraints(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_8_9.H()).addVersionConstraint(ForgeVersion.MC_1_21_4.n());
    }

    public void applyHudModuleStates(JsonObject enabledStates) {
        for (HackModule mod : this.collectMods()) {
            if (!(mod instanceof HudModule) || !hasModuleState(enabledStates, mod)) continue;
            boolean enabled = getModuleState(enabledStates, mod);
            if (mod.isEnabled() == enabled) continue;
            mod.setEnabled(enabled);
        }
    }

    private static void addMinecraft189Constraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_8_9.H());
    }

    public <T extends HackModule> T getMod(Class<T> clazz) {
        return (T)((HackModule)this.activeModulesByType.get(clazz));
    }

    public boolean isOtherInventoryActionActive(Class<? extends InventoryActionModule> moduleType) {
        for (HackModule mod : this.activeModulesByType.values()) {
            if (mod.getClass() == moduleType || !(mod instanceof InventoryActionModule)) continue;
            InventoryActionModule inventoryActionModule = (InventoryActionModule)((Object)mod);
            if (!mod.isEnabled() || !inventoryActionModule.isPerformingInventoryAction()) continue;
            return true;
        }
        return false;
    }

    public void disableNonHudModules() {
        this.suppressStateNotifications = true;
        for (HackModule mod : this.collectMods()) {
            if (mod.getCategory() == Category.NONE || !mod.isEnabled() || mod instanceof HudModule) continue;
            mod.setEnabled(false);
        }
        this.suppressStateNotifications = false;
    }

    public HackModule getMod(String key) {
        for (Map.Entry<Class<? extends HackModule>, HackModule> entry : this.activeModulesByType.entrySet()) {
            if (!((HackModule)entry.getValue()).getId().equalsIgnoreCase(key)) continue;
            return (HackModule)entry.getValue();
        }
        // Legacy display-name fallback for pre-id configs.
        for (Map.Entry<Class<? extends HackModule>, HackModule> entry : this.activeModulesByType.entrySet()) {
            if (!((HackModule)entry.getValue()).getName().equals(key)) continue;
            return (HackModule)entry.getValue();
        }
        return null;
    }

    private static void addMinecraft1710Constraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_7_10.N());
    }

    public List<HackModule> getProfileModules(JsonObject enabledModuleStates) {
        ArrayList<HackModule> modules = new ArrayList<HackModule>();
        for (HackModule mod : this.collectMods()) {
            if (!hasModuleState(enabledModuleStates, mod) || !mod.isVisible() || mod.getCategory() == Category.NONE) continue;
            modules.add(mod);
        }
        return modules;
    }

    public Collection<HackModule> getAllModules() {
        return this.allModulesByType.values();
    }

    private static void addMinecraft1165Constraint(ModRegistrationBuilder modRegistrationBuilder) {
        modRegistrationBuilder.addVersionConstraint(ForgeVersion.MC_1_16_5.b());
    }

    private static void noAdditionalConstraints(ModRegistrationBuilder modRegistrationBuilder) {
    }

    public Collection<HackModule> collectMods() {
        return this.activeModulesByType.values();
    }

    public void loadJson(JsonArray serializedModules) {
        int errorCount = 0;
        for (int index = 0; index < serializedModules.size(); ++index) {
            JsonObject serializedModule;
            JsonElement element = serializedModules.get(index);
            if (!element.isJsonObject() || element.isJsonNull()) continue;
            serializedModule = element.getAsJsonObject();
            String moduleKey = ConfigJsonUtils.getString(serializedModule, "id");
            if (moduleKey == null) {
                if (serializedModule.get("name") == null || serializedModule.get("name").isJsonNull()) continue;
                moduleKey = serializedModule.get("name").getAsString();
            }
            for (HackModule mod : this.getTopLevelModules()) {
                try {
                    if (!mod.getId().equalsIgnoreCase(moduleKey) && !mod.getName().equalsIgnoreCase(moduleKey)) continue;
                    mod.loadJson(serializedModule);
                }
                catch (Exception exception) {
                    ++errorCount;
                    Umbra.debugLog(mod.getName());
                    Umbra.logThrowable(exception);
                }
            }
        }
        if (errorCount > 0) {
            Umbra.INSTANCE.getNotificationManager().show("Profile Load Error", errorCount + " module(s) failed to load settings.", NotificationType.WARNING, 5000L);
        }
    }

    public void disableHiddenModules() {
        int disabledCount = 0;
        for (HackModule mod : this.collectMods()) {
            if (mod.isVisible() || mod instanceof ClientSettings || mod.getGuiColor() == 0 || !mod.isEnabled()) continue;
            ++disabledCount;
            mod.toggle();
        }
        if (disabledCount > 0) {
            Umbra.INSTANCE.getNotificationManager().show("Hidden Disabled", disabledCount + " module(s) have been disabled!", NotificationType.WARNING, 2500L);
        }
    }

    public HackManager() {
        GuiComponent[] legacyComponentsSnapshot = Category.getLegacyComponents();
        Object[] discardedSlotStorage = new Object[877];
        Array.newInstance(Long.TYPE, 837);
        Array.newInstance(Byte.TYPE, 904);
        Array.newInstance(Float.TYPE, 627);
        Array.newInstance(Short.TYPE, 845);
        Array.newInstance(Object.class, 823);
        Array.newInstance(Character.TYPE, 598);
        Array.newInstance(Double.TYPE, 654);
        GuiComponent[] preservedLegacyComponents = legacyComponentsSnapshot;
        Array.newInstance(Integer.TYPE, 556);
        Array.newInstance(Boolean.TYPE, 506);
        this.allModulesByType = new LinkedHashMap();
        this.activeModulesByType = new LinkedHashMap();
        this.enabledModules = new HashSet();
        this.activeModuleList = new ArrayList();
        this.profileSwitchNotification = new ReusableTextNotification(NotificationType.INFO, "", "", 2000L);
        if (GuiComponent.getLegacyComponentState() == null) {
            Category.setLegacyComponents(new GuiComponent[1]);
        }
    }

    public ArrayList<HackModule> getActiveModuleList() {
        return this.activeModuleList;
    }
}
