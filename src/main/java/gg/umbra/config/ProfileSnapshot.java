package gg.umbra.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.config.ConfigJsonUtils;
import gg.umbra.config.Profile;
import gg.umbra.config.ProfileModuleSnapshot;
import gg.umbra.config.ProfileModuleSnapshotOrderComparator;
import gg.umbra.module.Category;
import gg.umbra.module.HackModule;
import gg.umbra.module.SubHack;
import gg.umbra.visual.hud.HudModule;
import gg.umbra.ui.click.frame.impl.profile.ProfileSnapshotGuiBuilder;
import gg.umbra.utils.NameComparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProfileSnapshot {
    private final List<ProfileModuleSnapshot> moduleSnapshots;
    private final ProfileSnapshotGuiBuilder guiBuilder;
    private Profile profile;

    public List<ProfileModuleSnapshot> getSortedModules(boolean includeDefaults) {
        List<ProfileModuleSnapshot> modules = this.getModules(includeDefaults);
        modules.sort(new ProfileModuleSnapshotOrderComparator());
        return modules;
    }

    public JsonObject serializeEnabledModules() {
        JsonObject enabledModules = new JsonObject();
        for (ProfileModuleSnapshot moduleSnapshot : this.getAllModules()) {
            if (moduleSnapshot.getModule() instanceof HudModule || !moduleSnapshot.isEnabled()) continue;
            enabledModules.addProperty(moduleSnapshot.getName(), Boolean.valueOf(moduleSnapshot.isEnabled()));
        }
        return enabledModules;
    }

    public ProfileSnapshotGuiBuilder getGuiBuilder() {
        return this.guiBuilder;
    }

    public void applyToProfile() {
        if (this.profile == null) {
            return;
        }
        JsonObject profileJson = this.profile.getData();
        profileJson.add("modules", this.serializeModules());
        profileJson.add("enabled", this.serializeEnabledModules());
        this.profile.updateData(profileJson);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public JsonArray serializeModules() {
        JsonArray modulesJson = new JsonArray();
        for (ProfileModuleSnapshot moduleSnapshot : this.moduleSnapshots) {
            JsonObject moduleJson = moduleSnapshot.toJson();
            if (moduleJson == null) continue;
            modulesJson.add(moduleJson);
        }
        return modulesJson;
    }

    public List<ProfileModuleSnapshot> getModules(boolean includeDefaults) {
        ArrayList<ProfileModuleSnapshot> modules = new ArrayList<>();
        for (ProfileModuleSnapshot moduleSnapshot : this.getAllModules()) {
            if (moduleSnapshot.getModule() instanceof SubHack || moduleSnapshot.getModule().getCategory() == Category.NONE || !moduleSnapshot.hasChanges() && !includeDefaults) continue;
            modules.add(moduleSnapshot);
        }
        return modules;
    }

    public ProfileSnapshot(Profile profile, JsonArray modulesJson) {
        this.profile = profile;
        this.moduleSnapshots = new ArrayList<>();
        LinkedHashMap<String, JsonObject> moduleJsonByName = new LinkedHashMap<>();
        if (modulesJson != null) {
            for (JsonElement moduleElement : modulesJson) {
                if (moduleElement.isJsonNull() || !moduleElement.isJsonObject()) continue;
                JsonObject moduleJson = moduleElement.getAsJsonObject();
                String moduleName = ConfigJsonUtils.getString(moduleJson, "name");
                if (moduleName == null) continue;
                moduleJsonByName.put(moduleName, moduleJson);
            }
        }
        for (HackModule module : Umbra.INSTANCE.getHackManager().getTopLevelModules()) {
            this.moduleSnapshots.add(new ProfileModuleSnapshot(this, module, moduleJsonByName.get(module.getName())));
        }
        this.moduleSnapshots.sort(new NameComparator());
        this.guiBuilder = new ProfileSnapshotGuiBuilder(this);
    }

    public List<ProfileModuleSnapshot> getAllModules() {
        return this.moduleSnapshots;
    }
}

