package gg.umbra;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.umbra.asm.helper.DescUtils;
import gg.umbra.combat.AttackStrengthTracker;
import gg.umbra.config.ConfigJsonUtils;
import gg.umbra.config.LocalConfigStore;
import gg.umbra.config.ModuleProfileMetadataCodec;
import gg.umbra.config.Profile;
import gg.umbra.config.PublicProfileSettings;
import gg.umbra.event.ClientListenerBootstrap;
import gg.umbra.event.EventDispatcher;
import gg.umbra.event.EventNameFormatRewriteService;
import gg.umbra.event.IEvent;
import gg.umbra.event.impl.EventRenderWorldPassExecutorDrain;
import gg.umbra.event.listener.ClientSettingsEventForwarder;
import gg.umbra.event.listener.EventTimingOverlayListener;
import gg.umbra.event.listener.UmbraClientEventListener;
import gg.umbra.event.listener.UmbraLifecycleEventListener;
import gg.umbra.event.listener.UmbraShutdownEventListener;
import gg.umbra.event.listener.WorldChangeEventDispatcher;
import gg.umbra.friend.FriendAliasEventListener;
import gg.umbra.input.InputEventDispatcher;
import gg.umbra.lifecycle.ClientDirectoryCleanupCallback;
import gg.umbra.manager.MacroManager;
import gg.umbra.manager.HackManager;
import gg.umbra.manager.SearchManager;
import gg.umbra.manager.ValueManager;
import gg.umbra.manager.client.EnemyManager;
import gg.umbra.manager.client.FriendManager;
import gg.umbra.manager.client.IndependentSettingsManager;
import gg.umbra.manager.client.ProfilesManager;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapper;
import gg.umbra.mapping.MappingProfileSnapshotRegistry;
import gg.umbra.mapping.PrimaryMappingTaskSet;
import gg.umbra.mapping.runtime.RuntimeNameMappingRegistry;
import gg.umbra.hacks.exploits.AntiBot;
import gg.umbra.combat.AttackPacketTimingTracker;
import gg.umbra.macros.MacroEventListener;
import gg.umbra.settings.ClientSettings;
import gg.umbra.visual.nametags.NameTagsRenderStateTracker;
import gg.umbra.visual.entity.RenderEntityContextCacheListener;
import gg.umbra.tools.inventory.ItemStackSemanticResolver;
import gg.umbra.tools.inventory.cleaner.InventoryFilterPresetRegistry;
import gg.umbra.tools.inventory.cleaner.ui.InventoryCleanerProfileValueRefreshListener;
import gg.umbra.movement.PlayerMovementTaskManager;
import gg.umbra.notification.NotificationManager;
import gg.umbra.notification.NotificationSoundPlayer;
import gg.umbra.notification.WelcomeOverlay;
import gg.umbra.rotation.RotationManager;
import gg.umbra.runtime.NativeBridge;
import gg.umbra.status.NativePresenceUpdater;
import gg.umbra.tutorial.TutorialManager;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.value.FloatingValueDropdownLayer;
import gg.umbra.ui.click.frame.Frame;
import gg.umbra.ui.font.FontManager;
import gg.umbra.ui.font.FontOption;
import gg.umbra.ui.font.FontSelector;
import gg.umbra.unmap.BendableInputDispatcher;
import gg.umbra.unmap.GLUtils;
import gg.umbra.unmap.ItemHelper;
import gg.umbra.utils.AttackCooldownUtil;
import gg.umbra.utils.Base64Util;
import gg.umbra.utils.TimerUtil;
import gg.umbra.utils.network.PacketDispatchGuard;
import gg.umbra.utils.render.GuiRenderPrimitives;
import gg.umbra.utils.render.ImageRenderer;
import gg.umbra.utils.render.OpenGlDeviceInfo;
import gg.umbra.utils.render.RenderBatchManager;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.PotionRegistry;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import gg.umbra.Umbra;

public class Umbra {
    private static int opaqueState; // obfuscation opaque-predicate counter (get=getOpaqueState(), set=setOpaqueState(int)); do not treat as meaningful state
    public static Umbra INSTANCE;
    public static boolean mappingsLoaded;
    public boolean enabled;
    public static boolean renderReady;
    public static final boolean DEBUG = false; // always false; likely dead compile-time debug toggle
    public static final boolean DEV = false; // always false; likely dead compile-time toggle
    public static final String VERSION;
    static TimerUtil loadTimer;
    private boolean forgeAbsent;
    private boolean mappingsRemapped;
    private boolean vanillaMinecraftPresentCache;
    private boolean renderInitialized;
    private boolean vanillaMinecraftChecked;
    private boolean pendingTickAction; // one-shot: set via setPendingTickAction(true), consumed+cleared next client tick in EventTickBase.fire()
    private boolean unclassifiedFlag463; // purpose unconfirmed: setUnclassifiedFlag463(boolean)/isUnclassifiedFlag463Set have no discoverable callers
    private FriendManager friendManager;
    private FontManager fontManager;
    private ItemHelper itemHelper;
    private ProfilesManager profilesManager;
    private ClientListenerBootstrap clientListenerBootstrap;
    private PublicProfileSettings publicProfileSettings;
    private InventoryFilterPresetRegistry inventoryFilterPresetRegistry;
    private NotificationSoundPlayer notificationSoundPlayer;
    private GLUtils glUtils;
    private SearchManager searchManager;
    private EnemyManager enemyManager;
    private MacroManager macroManager;
    private String cachedAllData;
    private HackManager modManager;
    private IndependentSettingsManager independentSettingsManager;
    private Boolean isLabyModCache;
    private PrimaryMappingTaskSet primaryMappingTaskSet;
    private ValueManager valueManager;
    private FontSelector fontSelector;
    private Mapper mapper;
    private NotificationManager notificationManager;
    private ItemStackSemanticResolver itemStackSemanticResolver;
    private gg.umbra.config.ClientSettings clientSettings;
    private NativePresenceUpdater nativePresenceUpdater;
    private Boolean isFabricPresentCache;
    private ModuleProfileMetadataCodec moduleProfileMetadataCodec;
    private Object directoryCleanupCallback;
    //used for time bomb
    private static Date buildDate;
    private static int[] supportedVersionIds;

    public boolean isMappingsRemapped() {
        return this.mappingsRemapped;
    }

    public void loadConfigData(JsonObject configRoot, boolean useNewOtherDataKey) {
        JsonObject profilesData;
        JsonObject profilesElement;
        JsonArray friendsArray = ConfigJsonUtils.getJsonArray(configRoot, "friends");
        if (friendsArray != null) {
            this.friendManager.loadFriends(friendsArray);
        }
        if ((profilesElement = ConfigJsonUtils.getJsonObject(configRoot, "profiles")) != null) {
            profilesData = configRoot.get("profiles").getAsJsonObject();
            this.profilesManager.loadJson(profilesData);
        } else {
            Umbra.debugLog("profilesData is NULL!");
        }
        JsonArray otherData = ConfigJsonUtils.getJsonArray(configRoot, useNewOtherDataKey ? "otherData" : "otherdata");
        if (otherData != null && otherData.size() > 0) {
            this.independentSettingsManager.loadIndependentSettings(otherData);
        } else {
            Umbra.debugLog("otherData is NULL!");
        }
    }

    public MacroManager getMacrosManager() {
        return this.macroManager;
    }

    public void loadMappings() {
        this.traceStep(18);
        int opaqueSeed = Umbra.getOpaqueState();
        MappedClasses.p();
        mappingsLoaded = true;
        int opaqueBranch = opaqueSeed;
        if (opaqueBranch != 0) {
            this.traceStep(19);
            if (this.forgeAbsent && ForgeVersion.MC_26_1.v()) {
                NativeBridge.fs();
                MappedClasses.p();
                NativeBridge.rsc();
            }
            MappingProfileSnapshotRegistry.X();
            RuntimeNameMappingRegistry.initializeRegistry();
            this.traceStep(20);
            this.mapper = new Mapper();
            this.mapper.loadMappings();
            this.traceStep(21);
            MappingProfileSnapshotRegistry.y();
            if (this.isForgeRemapActive()) {
                NativeBridge.fs();
                Mapper.RF.clear();
                this.mappingsRemapped = true;
                this.mapper = new Mapper();
                this.mapper.loadMappings();
            } else {
                this.mappingsRemapped = false;
            }
            MappingProfileSnapshotRegistry.h();
            this.traceStep(22);
            NativeBridge.su(Minecraft.getSessionUsername());
            if (GuiComponent.getLegacyComponentState() == null) {
                Umbra.setOpaqueState(++opaqueBranch);
            }
            return;
        }
        this.traceStep(19);
        this.mapper = new Mapper();
        this.mapper.loadMappings();
        this.traceStep(21);
        MappingProfileSnapshotRegistry.y();
        this.mappingsRemapped = false;
        MappingProfileSnapshotRegistry.h();
        this.traceStep(22);
        NativeBridge.su(Minecraft.getSessionUsername());
        if (GuiComponent.getLegacyComponentState() == null) {
            Umbra.setOpaqueState(++opaqueBranch);
        }
    }

    public static String formatThrowable(Throwable error) {
        StringWriter stackTraceWriter = new StringWriter();
        PrintWriter stackTracePrinter = new PrintWriter(stackTraceWriter);
        error.printStackTrace(stackTracePrinter);
        return "\nException " + error.getClass().getCanonicalName() + " " + stackTraceWriter.toString();
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public InventoryFilterPresetRegistry getInventoryFilterPresetRegistry() {
        return this.inventoryFilterPresetRegistry;
    }

    private void logInitError(String phase, Throwable error) {
        StringBuilder message = new StringBuilder();
        message.append(RenderBatchManager.buildInitializationDiagnostics(phase, error));
        message.append(Umbra.formatThrowable(error));
        Umbra.logError(message.toString());
        Umbra.logThrowable(error);
    }

    public static void setOpaqueState(int value) {
        opaqueState = value;
    }

    public void setPendingTickAction(boolean pending) {
        this.pendingTickAction = pending;
    }

    public Mapper getMappings() {
        return this.mapper;
    }

    public static void notifyNativeStackTrace() {
        DescUtils.traceStack();
        NativeBridge.mb(101);
    }

    private static boolean lambda$registerListeners$0(IEvent event) {
        return AttackStrengthTracker.INSTANCE.isHealthPredictionEnabled();
    }

    public SearchManager getSearch() {
        return this.searchManager;
    }

    public EnemyManager getEnemyManager() {
        return this.enemyManager;
    }

    public boolean isForgeRemapInactive() {
        return !this.isForgeRemapActive();
    }

    public NotificationSoundPlayer getNotificationSoundPlayer() {
        return this.notificationSoundPlayer;
    }

    public FontSelector getFontSelector() {
        return this.fontSelector;
    }

    public void setUnclassifiedFlag463(boolean value) {
        this.unclassifiedFlag463 = value;
    }

    public boolean isFabricMinecraftPresent() {
        if (this.isFabricPresentCache == null) {
            String fabricMinecraftClass = "net/minecraft/class_310";
            this.isFabricPresentCache = NativeBridge.gc(fabricMinecraftClass) != null;
        }
        return this.isFabricPresentCache;
    }

    public ItemHelper getItemHelper() {
        return this.itemHelper;
    }

    public boolean isForgeRemapActive() {
        if (this.isLabyModPresent()) {
            return false;
        }
        return ForgeVersion.MC_26_1.v();
    }

    public static byte[] readResource(String resourceName) {
        String resourcePath = "resources/" + resourceName;
        return NativeBridge.gfb(resourcePath);
    }

    public boolean initAccountInfo() {
        return true;
    }

    public boolean isFeatureDisabled() {
        return false;
    }

    public HackManager getHackManager() {
        return this.modManager;
    }

    public gg.umbra.config.ClientSettings getClientSettings() {
        return this.clientSettings;
    }

    public void initializeManagers() {
        this.glUtils = new GLUtils();
        this.glUtils.initializeVertexBuffer(24, 1);
        this.itemHelper = new ItemHelper();
        Umbra.opaquePredicate();
        this.fontSelector = new FontSelector();
        this.valueManager = new ValueManager();
        this.independentSettingsManager = new IndependentSettingsManager();
        this.friendManager = new FriendManager();
        this.enemyManager = new EnemyManager();
        this.macroManager = new MacroManager();
        this.searchManager = new SearchManager();
        this.clientSettings = new gg.umbra.config.ClientSettings();
        this.publicProfileSettings = new PublicProfileSettings();
        this.modManager = new HackManager();
        this.modManager.init();
        this.clientSettings.antiBot = this.modManager.getMod(AntiBot.class);
        this.moduleProfileMetadataCodec = new ModuleProfileMetadataCodec();
        this.traceStep(23);
        this.initPrimaryMappingTasks();
        this.traceStep(24);
        this.itemStackSemanticResolver = new ItemStackSemanticResolver();
        this.itemStackSemanticResolver.loadMappings();
        try {
            this.itemStackSemanticResolver.reportMissingMappings();
        }
        catch (Throwable ignored) {
                Umbra.logThrowable(ignored);
            }
        PotionRegistry.d();
        this.inventoryFilterPresetRegistry = new InventoryFilterPresetRegistry();
        this.profilesManager = new ProfilesManager();
        this.modManager.initializeAllModules();
        this.notificationManager = new NotificationManager();
        this.traceStep(25);
        this.modManager.finishModuleInitialization();
        LocalConfigStore.load();
        if (this.publicProfileSettings.getSelectedProfile() != null) {
            this.getProfilesManager().setActiveProfile(this.publicProfileSettings.getSelectedProfile());
        } else {
            this.getProfilesManager().getActiveProfile();
        }
        if (this.profilesManager.getActiveProfile() != null) {
            if (Umbra.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue().booleanValue()) {
                this.profilesManager.getActiveProfile().applyEnabledModuleStates();
            }
            this.profilesManager.getActiveProfile().applyLegitEnabledModuleStates();
        }
        this.traceStep(26);
        this.traceStep(27);
        this.notificationSoundPlayer = new NotificationSoundPlayer();
        InputEventDispatcher.getInstance().registerHandlers();
        this.initClientListeners();
        this.showLoadCompleteNotification();
        this.getFontSelector().N((FontOption)Umbra.INSTANCE.getPublicProfileSettings().language.getValue());
        this.traceStep(28);
        NativeBridge.dc();
        this.registerEventListeners();
        LocalConfigStore.startPeriodicSaver();
        Runtime.getRuntime().addShutdownHook(new Thread(LocalConfigStore::save, "Umbra Auto Save"));
    }

    public ItemStackSemanticResolver getItemStackResolver() {
        return this.itemStackSemanticResolver;
    }

    public boolean isVanillaMinecraftPresent() {
        if (this.vanillaMinecraftChecked) {
            return this.vanillaMinecraftPresentCache;
        }
        this.vanillaMinecraftChecked = true;
        if (!this.forgeAbsent) {
            return false;
        }
        String vanillaMinecraftClass = "net/minecraft/client/Minecraft";
        this.vanillaMinecraftPresentCache = NativeBridge.gvc(vanillaMinecraftClass) != null;
        return this.vanillaMinecraftPresentCache;
    }

    public static int opaquePredicate() {
        int state = Umbra.getOpaqueState();
        if (state == 0) {
            return 20;
        }
        return 0;
    }

    public boolean isUnclassifiedFlag463Set() {
        return this.unclassifiedFlag463;
    }

    public ModuleProfileMetadataCodec getModuleProfileMetadataCodec() {
        return this.moduleProfileMetadataCodec;
    }

    public boolean isLabyModPresent() {
        if (this.isLabyModCache != null) {
            return this.isLabyModCache;
        }
        if (!ForgeVersion.MC_1_8_9.L()) {
            this.isLabyModCache = false;
            return false;
        }
        try {
            Class.forName("net.laby.launcher.classloading.LabyClassLoader", false, Umbra.class.getClassLoader());
            this.isLabyModCache = true;
        }
        catch (Throwable ignored) {
            this.isLabyModCache = false;
        }
        return this.isLabyModCache;
    }

    public int getAccountTier() {
        return 0;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public TutorialManager getTutorialManager() {
        return null;
    }

    public PrimaryMappingTaskSet getPrimaryMappingTaskSet() {
        return this.primaryMappingTaskSet;
    }

    public ValueManager getValueManager() {
        return this.valueManager;
    }

    public NativePresenceUpdater getNativePresenceUpdater() {
        return this.nativePresenceUpdater;
    }

    public boolean isTickActionPending() {
        return this.pendingTickAction;
    }

    public void saveAndStop() {
        Profile activeProfile;
        if (this.profilesManager != null && (activeProfile = this.profilesManager.getActiveProfileOrNull()) != null) {
            activeProfile.setDirty(true);
        }
    }

    public void exportFramesConfig(String filePath) {
        try {
            String output = this.modManager.buildTranslationTemplate();
            for (Frame frame : ClientSettings.getAllFrames()) {
                if (frame instanceof FloatingValueDropdownLayer || !frame.J$src$Z$1eqdghz()) continue;
                String frameLine = "frame." + frame.getName().toLowerCase().replace(" ", "_") + "=" + frame.getName();
                output = output + frameLine + "\n";
            }
            FileOutputStream fileOut = new FileOutputStream(filePath);
            fileOut.write(output.getBytes());
        }
        catch (Exception exception) {
            Umbra.logThrowable(exception);
            exception.printStackTrace();
        }
    }

    public FontManager getFontManager() {
        return this.fontManager;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static boolean isSupportedVersion() {
        for (int versionId : supportedVersionIds) {
            if (versionId != ForgeVersion.c()) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$registerListeners$1(IEvent event) {
        return EventDispatcher.timingEnabled;
    }

    public void initClientListeners() {
        this.primaryMappingTaskSet.X();
        this.primaryMappingTaskSet.d();
        this.clientListenerBootstrap = new ClientListenerBootstrap();
        this.clientListenerBootstrap.registerListeners();
    }

    private void registerEventListeners() {
        EventDispatcher.getInstance().registerListener(PacketDispatchGuard.b, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new BendableInputDispatcher(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(AttackPacketTimingTracker.INSTANCE, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(RotationManager.INSTANCE, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(PlayerMovementTaskManager.INSTANCE, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(AttackStrengthTracker.INSTANCE, Umbra::lambda$registerListeners$0);
        EventDispatcher.getInstance().registerListener(new WorldChangeEventDispatcher(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new RenderEntityContextCacheListener(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new MacroEventListener(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new InventoryCleanerProfileValueRefreshListener(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(EventTimingOverlayListener.INSTANCE, Umbra::lambda$registerListeners$1);
        EventDispatcher.getInstance().registerListener(new UmbraShutdownEventListener(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(NameTagsRenderStateTracker.INSTANCE, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(this.notificationManager, new Predicate[0]);
        EventDispatcher.getInstance().registerListener(WelcomeOverlay.getInstance(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new ClientSettingsEventForwarder(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(new AttackCooldownUtil(), new Predicate[0]);
        EventDispatcher.getInstance().registerListener(this.modManager, new Predicate[0]);
        if (ForgeVersion.MC_1_8_9.L()) {
            EventDispatcher.getInstance().registerListener(new UmbraLifecycleEventListener(), new Predicate[0]);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            EventDispatcher.getInstance().registerListener(new EventNameFormatRewriteService(), new Predicate[0]);
        } else {
            EventDispatcher.getInstance().registerListener(new FriendAliasEventListener(), new Predicate[0]);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            EventDispatcher.getInstance().registerListener(new UmbraClientEventListener(), new Predicate[0]);
        }
    }

    private void showLoadCompleteNotification() {
        ClientSettings clientSettingsModule = INSTANCE.getHackManager().getMod(ClientSettings.class);
        if (clientSettingsModule.guiBindIndicator.getEffectiveValue().booleanValue()) {
            WelcomeOverlay.getInstance().show("Umbra loaded", "Press " + clientSettingsModule.getBind().getBindText() + " to open GUI");
        }
    }

    public boolean isForgeAbsent() {
        return this.forgeAbsent;
    }

    public ProfilesManager getProfilesManager() {
        return this.profilesManager;
    }

    public void initializeRender() {
        if (this.renderInitialized) {
            return;
        }
        try {
            OpenGlDeviceInfo.collectDeviceInfo();
            if (GuiRenderPrimitives.d()) {
                try {
                    RenderBatchManager.getInstance();
                }
                catch (Throwable error) {
                    this.logInitError("RenderEngine initialization", error);
                    return;
                }
            }
            try {
                this.fontManager = new FontManager();
                this.fontManager.e();
            }
            catch (Throwable error) {
                this.logInitError("FontManager initialization", error);
                return;
            }
            try {
                ImageRenderer.preloadResources();
            }
            catch (Throwable error) {
                this.logInitError("DrawTexture pre-cache", error);
                return;
            }
            try {
                GuiRenderPrimitives.B();
            }
            catch (Throwable error) {
                this.logInitError("UmbraRender initialization", error);
                return;
            }
            this.renderInitialized = true;
        }
        catch (Throwable error) {
            Umbra.logThrowable(error);
        }
    }

    public void traceStep(int step) {
        NativeBridge.trs(step);
    }

    public static void logThrowable(Throwable error) {
        Umbra.debugLog(Umbra.formatThrowable(error));
    }

    public static void logError(String message) {
        NativeBridge.sce(message);
    }

    public GLUtils getGlUtils() {
        return this.glUtils;
    }

    public static void logTimed(String message) {
        NativeBridge.printLog(String.format("[%02d] %s", System.currentTimeMillis(), message + " " + loadTimer.getLastMS()));
        loadTimer.reset();
    }

    static {
        Umbra.setOpaqueState(66);
        VERSION = "1.0.0";
        supportedVersionIds = new int[]{13, 15, 23, 35, 36, 50, 61, 100, 110};
        buildDate = new Date(1710640988922L);
        renderReady = false;
        mappingsLoaded = false;
        loadTimer = new TimerUtil();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initPrimaryMappingTasks() {
        int opaqueSeed = Umbra.opaquePredicate();
        this.primaryMappingTaskSet = new PrimaryMappingTaskSet();
        if (NativeBridge.isBadlion189Runtime()) {
            FutureTask<Void> initializationTask = new FutureTask<Void>(() -> {
                ClientSettings.initializeFrames();
                return null;
            });
            Minecraft.v(initializationTask);
            try {
                initializationTask.get(30L, TimeUnit.SECONDS);
            }
            catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                initializationTask.cancel(false);
                throw new CompletionException(interrupted);
            }
            catch (ExecutionException execution) {
                Throwable cause = execution.getCause();
                throw new CompletionException(
                        cause == null ? execution : cause);
            }
            catch (TimeoutException timeout) {
                initializationTask.cancel(false);
                throw new CompletionException(timeout);
            }
            return;
        }
        this.primaryMappingTaskSet.L();
        this.primaryMappingTaskSet.d();
        int opaqueBranch = opaqueSeed;
        EventRenderWorldPassExecutorDrain.EXECUTOR.execute(ClientSettings::initializeFrames);
        try {
            while (!ClientSettings.framesInitialized) {
                try {
                    Thread.sleep(10L);
                    if (opaqueBranch != 0) return;
                }
                catch (InterruptedException interrupted) {
                    Umbra.logThrowable(interrupted);
                    if (opaqueBranch == 0) continue;
                    break;
                }
            }
        }
        catch (CompletionException completionException) {
            try {
                throw Umbra.rethrow(completionException);
            }
            catch (CompletionException retriedCompletionException) {
                throw Umbra.rethrow(retriedCompletionException);
            }
        }
        this.primaryMappingTaskSet.C();
    }

    public IndependentSettingsManager getSettingsManager() {
        return this.independentSettingsManager;
    }

    public static void debugLog(String message) {
        String normalizedMessage = message == null ? "<null>" : message;
        try {
            NativeBridge.sce("DEBUG " + normalizedMessage);
        }
        catch (Throwable nativeLoggingFailure) {
            System.err.println("[Umbra] " + normalizedMessage);
        }
    }

    private static <T extends Throwable> T rethrow(T error) {
        return error;
    }

    public PublicProfileSettings getPublicProfileSettings() {
        return this.publicProfileSettings;
    }

    public static int getOpaqueState() {
        return opaqueState;
    }

    public Umbra() {
        int opaqueBranch = Umbra.opaquePredicate();
        if (opaqueBranch != 0) {
            this.nativePresenceUpdater = new NativePresenceUpdater();
            this.vanillaMinecraftChecked = false;
            this.vanillaMinecraftPresentCache = false;
            this.isFabricPresentCache = null;
            this.renderInitialized = false;
            this.isLabyModCache = null;
            this.traceStep(17);
            INSTANCE = this;
            this.forgeAbsent = NativeBridge.isForgeAbsent();
            this.directoryCleanupCallback = new ClientDirectoryCleanupCallback();
            GuiComponent.setLegacyComponentState(new GuiComponent[4]);
            return;
        }
        this.nativePresenceUpdater = new NativePresenceUpdater();
        this.vanillaMinecraftChecked = false;
        this.vanillaMinecraftPresentCache = false;
        this.isFabricPresentCache = null;
        this.renderInitialized = false;
        this.isLabyModCache = null;
        this.traceStep(17);
        INSTANCE = this;
        this.forgeAbsent = NativeBridge.isForgeAbsent();
        this.directoryCleanupCallback = new ClientDirectoryCleanupCallback();
    }

    public synchronized String getDecodedAllData(boolean refresh) {
        block6: {
            block5: {
                try {
                    if (!refresh || this.cachedAllData != null) break block5;
                }
                catch (CompletionException completionException) {
                    throw Umbra.rethrow(completionException);
                }
                String encoded = NativeBridge.gp("all");
                byte[] decoded = Base64Util.decodeBase64(encoded);
                this.cachedAllData = new String(decoded);
                break block6;
            }
            try {
                if (this.cachedAllData == null) {
                    return "";
                }
            }
            catch (CompletionException completionException) {
                throw Umbra.rethrow(completionException);
            }
        }
        return this.cachedAllData;
    }

    public Mapper getMappingsCompat() {
        return this.getMappings();
    }

    public Mapper getMappingsMapperCompat() {
        return this.getMappings();
    }
}
