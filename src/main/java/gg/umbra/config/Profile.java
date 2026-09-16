package gg.umbra.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.module.HackModule;
import gg.umbra.unmap.Bendable;
import gg.umbra.utils.Base64Util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Profile
extends Bendable
implements Comparable<Profile> {
    private String clientVersion;
    private static boolean runtimeState;
    private JsonObject data;
    private JsonObject enabledModuleStates;
    private JsonObject publishedData;
    private int useCount;
    private UUID localId = UUID.randomUUID();
    @Nullable
    private Integer sortOrder;
    @Nullable
    private UUID onlineId;
    public static final int SERIALIZATION_MARKER;
    private boolean saveQueued;
    private JsonObject legitEnabledModuleStates;
    private boolean draft;
    @Nullable
    private long updatedAt;
    private boolean visible = true;
    private String name;
    private boolean dirty;
    private boolean publicProfileFlag;
    private String originalUuid;

    public boolean isDraft() {
        return this.draft;
    }

    public void captureCurrentState() {
        this.clientVersion = Umbra.VERSION;
        this.updateTimestamp();
        this.data = this.serializeCurrentData(false);
        this.publishedData = this.serializeCurrentData(true);
        this.captureEnabledModuleStates();
        this.data.add("enabled", (JsonElement)this.enabledModuleStates);
        this.publishedData.add("enabled", (JsonElement)this.enabledModuleStates);
        this.captureLegitEnabledModuleStates();
        this.data.add("legit_enabled", (JsonElement)this.legitEnabledModuleStates);
        this.publishedData.add("legit_enabled", (JsonElement)this.legitEnabledModuleStates);
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public JsonObject serializeCurrentData(boolean forPublication) {
        JsonObject serializedData = new JsonObject();
        serializedData.add("modules", (JsonElement)Umbra.INSTANCE.getHackManager().toJson(forPublication));
        serializedData.add("favorites", (JsonElement)Umbra.INSTANCE.getModuleProfileMetadataCodec().toJson());
        serializedData.add("values", (JsonElement)Umbra.INSTANCE.getValueManager().toJson());
        serializedData.add("macros", (JsonElement)Umbra.INSTANCE.getMacrosManager().toJson());
        serializedData.add("search", (JsonElement)Umbra.INSTANCE.getSearch().toJson());
        serializedData.add("frames", (JsonElement)gg.umbra.settings.ClientSettings.INSTANCE.serializeFrameStates());
        return serializedData;
    }

    public JsonObject copyPublishedData() {
        JsonObject publishedDataCopy = new JsonObject();
        if (this.publishedData != null) {
            JsonObject sourceCopy = this.publishedData.deepCopy();
            sourceCopy.remove("sortOrder");
            return sourceCopy;
        }
        return publishedDataCopy;
    }

    public Profile(String name, String clientVersion, boolean saveQueued) {
        this.enabledModuleStates = new JsonObject();
        this.legitEnabledModuleStates = new JsonObject();
        this.name = name;
        this.clientVersion = clientVersion;
        this.saveQueued = saveQueued;
        this.updateTimestamp();
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public void setPublicProfileFlag(boolean publicProfileFlag) {
        this.publicProfileFlag = publicProfileFlag;
    }

    public boolean isPublicProfileFlag() {
        return this.publicProfileFlag;
    }

    public void setOnlineId(@Nullable UUID onlineId) {
        this.onlineId = onlineId;
    }

    public static void setRuntimeState(boolean runtimeState) {
        Profile.runtimeState = runtimeState;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    public JsonObject getPublishedData() {
        return this.publishedData;
    }

    public JsonObject toJson(boolean useRemoteFormat) {
        JsonObject object = new JsonObject();
        object.addProperty("uuid", this.localId.toString());
        if (this.onlineId != null) {
            object.addProperty("profileId", this.onlineId.toString());
        }
        if (useRemoteFormat && (this.clientVersion == null || this.clientVersion.isEmpty())) {
            this.clientVersion = Umbra.VERSION;
        }
        object.addProperty("name", this.name.length() > 48 ? this.name.substring(0, 47) : this.name);
        object.addProperty(useRemoteFormat ? "umbraVersion" : "version", this.clientVersion);
        this.data.add("keybinds", (JsonElement)this.serializeBoundInputs());
        this.data.addProperty("sortOrder", (Number)this.getCurrentSortIndex());
        object.add("data", (JsonElement)this.data);
        object.addProperty("updated", (Number)this.updatedAt);
        if (this.originalUuid != null) {
            object.addProperty("original_uuid", this.originalUuid);
        }
        return object;
    }

    public List<HackModule> getEnabledModules() {
        return Umbra.INSTANCE.getHackManager().getProfileModules(this.enabledModuleStates);
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    @Nullable
    public UUID getOnlineId() {
        return this.onlineId;
    }

    @Override
    public boolean isActive() {
        return Umbra.INSTANCE.getProfilesManager().getActiveProfile().equals(this);
    }

    private void updateTimestamp() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void setPublishedData(JsonObject publishedData) {
        this.publishedData = publishedData;
    }

    @Override
    public String getDisplayText() {
        return String.format(" %s7[%sr%s%s7]%sr %s", ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.getBindText(), ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.getName());
    }

    public void applyEnabledModuleStates() {
        Umbra.INSTANCE.getHackManager().applyProfileModuleStates(this);
    }

    public long getUpdatedAt() {
        return this.updatedAt;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public String getName() {
        return this.name;
    }

    private static final java.util.Map<String, String> MODULE_NAME_MIGRATION = new java.util.HashMap<>();
    static {
        MODULE_NAME_MIGRATION.put("AutoAim", "AimAssist");
        MODULE_NAME_MIGRATION.put("PacketHold", "Blink");
        MODULE_NAME_MIGRATION.put("LootStealer", "ChestSteal");
        MODULE_NAME_MIGRATION.put("GhostCamera", "Freecam");
        MODULE_NAME_MIGRATION.put("BrightMode", "Fullbright");
        MODULE_NAME_MIGRATION.put("EntityLabels", "NameTags");
        MODULE_NAME_MIGRATION.put("FallProtect", "NoFall");
        MODULE_NAME_MIGRATION.put("ClickerRight", "RightClicker");
        MODULE_NAME_MIGRATION.put("ContainerHighlight", "StorageESP");
        MODULE_NAME_MIGRATION.put("KnockbackReducer", "Velocity");
        MODULE_NAME_MIGRATION.put("SprintReset", "WTap");
        MODULE_NAME_MIGRATION.put("BlockFinder", "Search");
        MODULE_NAME_MIGRATION.put("LootHighlight", "ItemESP");
        MODULE_NAME_MIGRATION.put("AutoHit", "Triggerbot");
        MODULE_NAME_MIGRATION.put("FallReset", "JumpReset");
        MODULE_NAME_MIGRATION.put("SilentCombat", "SilentAura");
        MODULE_NAME_MIGRATION.put("ObsidianCracker", "CrystalAura");
        MODULE_NAME_MIGRATION.put("ShieldCracker", "ShieldBreaker");
        MODULE_NAME_MIGRATION.put("HitBypass", "HitFlick");
    }

    private void migrateOldModuleNames(JsonObject data) {
        if (data == null) return;
        JsonObject enabled = ConfigJsonUtils.getJsonObject(data, "enabled");
        if (enabled != null) {
            for (java.util.Map.Entry<String, JsonElement> entry : enabled.entrySet()) {
                String newName = MODULE_NAME_MIGRATION.get(entry.getKey());
                if (newName != null) {
                    enabled.add(newName, entry.getValue());
                    enabled.remove(entry.getKey());
                }
            }
        }
    }

    public void loadData(boolean applyModuleStates) {
        JsonArray array;
        this.migrateOldModuleNames(this.data);
        if (applyModuleStates && Umbra.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue().booleanValue()) {
            Umbra.INSTANCE.getHackManager().disableNonHudModules();
        }
        if (this.data.get("values") != null && !this.data.get("values").isJsonNull()) {
            Umbra.INSTANCE.getValueManager().loadJson(this.data.get("values").getAsJsonArray());
        }
        if (this.data.get("modules") != null && !this.data.get("modules").isJsonNull()) {
            Umbra.INSTANCE.getHackManager().loadJson(this.data.get("modules").getAsJsonArray());
        }
        if (this.data.get("favorites") != null && !this.data.get("favorites").isJsonNull()) {
            Umbra.INSTANCE.getModuleProfileMetadataCodec().loadJson(this.data.get("favorites").getAsJsonObject());
        }
        if (this.data.get("macros") != null && !this.data.get("macros").isJsonNull()) {
            Umbra.INSTANCE.getMacrosManager().loadJson(this.data.get("macros").getAsJsonArray());
        }
        if (this.data.get("search") != null && !this.data.get("search").isJsonNull()) {
            array = this.data.get("search").getAsJsonArray();
            Umbra.INSTANCE.getSearch().loadJson(array);
        }
        if (applyModuleStates && Umbra.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue().booleanValue()) {
            this.applyEnabledModuleStates();
        }
        for (HackModule mod : Umbra.INSTANCE.getHackManager().collectMods()) {
            if (!mod.isEnabled()) continue;
            mod.syncSubModuleStates(true, true);
        }
        Umbra.INSTANCE.getHackManager().disableHiddenModules();
        gg.umbra.settings.ClientSettings.refreshModuleCategoryHeaders();
        gg.umbra.settings.ClientSettings.closeListDropdowns();
        if (this.data.get("frames") != null && !this.data.get("frames").isJsonNull() && Umbra.INSTANCE.getPublicProfileSettings().framePositionsPerProfile.getEffectiveValue().booleanValue()) {
            array = this.data.get("frames").getAsJsonArray();
            JsonArray frameGroups = new JsonArray();
            frameGroups.add((JsonElement)array);
            gg.umbra.settings.ClientSettings.INSTANCE.loadFrameStates(frameGroups);
        }
        if (this.data.get("original_uuid") != null && !this.data.get("original_uuid").isJsonNull()) {
            this.originalUuid = this.data.get("original_uuid").getAsString();
        }
    }

    public void updateData(JsonObject data) {
        this.updateTimestamp();
        this.parseData(data);
    }

    public String getFormattedUpdateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDateTime updatedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault());
        return formatter.format(updatedDateTime);
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Nullable
    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    @Override
    public void onBindActivated() {
        Umbra.INSTANCE.getProfilesManager().switchProfile(this);
    }

    public int compareSortOrder(@NotNull Profile profile) {
        return Integer.compare(this.getCurrentSortIndex(), profile.getCurrentSortIndex());
    }

    @Override
    public int compareTo(Profile profile) {
        return this.compareSortOrder(profile);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public static boolean isRuntimeCheckEnabled() {
        boolean currentState = Profile.getRuntimeState();
        return true;
    }

    public void apply() {
        this.loadData(true);
    }

    public static boolean getRuntimeState() {
        return runtimeState;
    }

    public int getCurrentSortIndex() {
        return Umbra.INSTANCE.getProfilesManager().getProfiles().indexOf(this);
    }

    public JsonObject getEnabledModuleStates() {
        return this.enabledModuleStates;
    }

    public void applyLegitEnabledModuleStates() {
        Umbra.INSTANCE.getHackManager().applyHudModuleStates(this.legitEnabledModuleStates);
    }

    private void parseData(JsonObject data) {
        Integer parsedSortOrder;
        JsonObject parsedLegitEnabledStates;
        JsonObject parsedEnabledStates;
        JsonArray keybinds;
        this.data = data;
        JsonArray values = ConfigJsonUtils.getJsonArray(data, "values");
        if (values != null) {
            this.data.add("values", (JsonElement)values);
        }
        if ((keybinds = ConfigJsonUtils.getJsonArray(data, "keybinds")) != null) {
            this.loadBoundInputs(keybinds, false);
        }
        if ((parsedEnabledStates = ConfigJsonUtils.getJsonObject(data, "enabled")) != null) {
            this.enabledModuleStates = parsedEnabledStates;
        }
        if ((parsedLegitEnabledStates = ConfigJsonUtils.getJsonObject(data, "legit_enabled")) != null) {
            this.legitEnabledModuleStates = parsedLegitEnabledStates;
        }
        if ((parsedSortOrder = ConfigJsonUtils.getInteger(data, "sortOrder")) != null) {
            this.sortOrder = parsedSortOrder;
        }
    }

    public void setSaveQueued(boolean saveQueued) {
        this.saveQueued = saveQueued;
    }

    public void setLocalId(UUID localId) {
        this.localId = localId;
    }

    private void captureLegitEnabledModuleStates() {
        this.legitEnabledModuleStates = Umbra.INSTANCE.getHackManager().getEnabledHudModuleStates();
    }

    public UUID getLocalId() {
        return this.localId;
    }

    public void captureEnabledModuleStates() {
        this.enabledModuleStates = Umbra.INSTANCE.getHackManager().getEnabledNonHudModuleStates();
    }

    public Profile(String name, String clientVersion) {
        this(name, clientVersion, false);
    }

    static {
        Profile.setRuntimeState(false);
        long marker = 2432912498588909616L;
        SERIALIZATION_MARKER = (int)marker;
    }

    @Override
    public void setBoundInputs(List<Integer> inputCodes) {
        ArrayList<Integer> validInputCodes = new ArrayList<Integer>();
        for (Integer inputCode : inputCodes) {
            int mouseButton;
            if (inputCode < 0 && (mouseButton = inputCode + 100) <= 1) continue;
            validInputCodes.add(inputCode);
        }
        super.setBoundInputs(validInputCodes);
    }

    public int getUseCount() {
        return this.useCount;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Nullable
    public ProfileSnapshot createSnapshot(boolean usePublishedData) {
        JsonObject snapshotData = usePublishedData && this.publishedData != null ? this.getPublishedData() : this.getData();
        if (snapshotData.get("modules") != null && !snapshotData.get("modules").isJsonNull()) {
            return new ProfileSnapshot(this, snapshotData.get("modules").getAsJsonArray());
        }
        return null;
    }

    public boolean isSaveQueued() {
        return this.saveQueued;
    }

    public void setSortOrder(@NotNull Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public JsonObject getData() {
        return this.data;
    }

    public Profile loadJson(JsonObject object) {
        String parsedOriginalUuid;
        Long parsedUpdatedAt;
        JsonObject parsedData;
        Boolean legacyPublicFlag;
        String umbraVersion;
        String profileId;
        String parsedName;
        String uuid = ConfigJsonUtils.getString(object, "uuid");
        if (uuid != null) {
            this.localId = UUID.fromString(uuid);
        }
        if ((parsedName = ConfigJsonUtils.getString(object, "name")) != null) {
            this.name = parsedName;
            if (this.name.startsWith("b64:")) {
                this.name = Base64Util.decodeUtf8Base64(this.name.split(":")[1]);
            }
        } else {
            this.name = "Unknown";
        }
        if ((profileId = ConfigJsonUtils.getString(object, "profileId")) != null) {
            this.onlineId = UUID.fromString(profileId);
        } else {
            this.dirty = true;
        }
        String version = ConfigJsonUtils.getString(object, "version");
        if (version != null) {
            this.clientVersion = version;
        }
        if ((umbraVersion = ConfigJsonUtils.getString(object, "umbraVersion")) != null) {
            this.clientVersion = umbraVersion;
        }
        if ((parsedData = ConfigJsonUtils.getJsonObject(object, "data")) != null) {
            this.parseData(parsedData);
        }
        if ((parsedUpdatedAt = ConfigJsonUtils.getLong(object, "updated")) != null) {
            this.setUpdatedAt(parsedUpdatedAt);
        }
        if ((parsedOriginalUuid = ConfigJsonUtils.getString(object, "original_uuid")) != null) {
            this.originalUuid = parsedOriginalUuid;
        }
        return this;
    }

}
