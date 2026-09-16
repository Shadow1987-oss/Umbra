package gg.umbra.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.umbra.Umbra;
import gg.umbra.manager.HackManager;
import gg.umbra.module.HackModule;
import gg.umbra.hacks.pvp.AutoAim;
import gg.umbra.hacks.pvp.ClickerLeft;
import gg.umbra.hacks.pvp.Reach;
import gg.umbra.hacks.pvp.KnockbackReducer;
import gg.umbra.ui.click.frame.impl.VisibleModuleListFrame;
import java.util.ArrayList;
import java.util.List;

public class ModuleProfileMetadataCodec {
    private final List<HackModule> selectedModules = new ArrayList<HackModule>();

    public void addModule(HackModule module) {
        if (this.selectedModules.contains(module)) {
            return;
        }
        this.selectedModules.add(module);
        module.setFavorite(true);
        VisibleModuleListFrame.e();
        Umbra.INSTANCE.saveAndStop();
    }

    public int getVisibleModuleCount() {
        int visibleCount = 0;
        for (HackModule module : this.selectedModules) {
            if (!module.isEnabled()) continue;
            ++visibleCount;
        }
        return visibleCount;
    }

    public List<HackModule> getSelectedModules() {
        return this.selectedModules;
    }


    public void removeModule(HackModule module) {
        if (!this.selectedModules.contains(module)) {
            return;
        }
        this.selectedModules.remove(module);
        module.setFavorite(false);
        VisibleModuleListFrame.e();
        Umbra.INSTANCE.saveAndStop();
    }

    public void loadJson(JsonObject object) {
        if (object.has("modules")) {
            this.selectedModules.clear();
            JsonArray modulesJson = object.get("modules").getAsJsonArray();
            for (JsonElement moduleElement : modulesJson) {
                HackModule module = Umbra.INSTANCE.getHackManager().getMod(moduleElement.getAsString());
                if (module == null) continue;
                this.addModuleWithoutSaving(module);
            }
            VisibleModuleListFrame.e();
        }
    }

    private void addModuleWithoutSaving(HackModule module) {
        if (this.selectedModules.contains(module)) {
            return;
        }
        this.selectedModules.add(module);
        module.setFavorite(true);
    }

    public ModuleProfileMetadataCodec() {
        HackManager modManager = Umbra.INSTANCE.getHackManager();
        this.addModuleWithoutSaving(modManager.getMod(ClickerLeft.class));
        this.addModuleWithoutSaving(modManager.getMod(AutoAim.class));
        this.addModuleWithoutSaving(modManager.getMod(Reach.class));
        this.addModuleWithoutSaving(modManager.getMod(KnockbackReducer.class));
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        JsonArray modulesJson = new JsonArray();
        for (HackModule module : this.selectedModules) {
            modulesJson.add((JsonElement)new JsonPrimitive(module.getId()));
        }
        object.add("modules", (JsonElement)modulesJson);
        return object;
    }
}

