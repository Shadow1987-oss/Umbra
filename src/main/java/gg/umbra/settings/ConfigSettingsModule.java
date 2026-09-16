package gg.umbra.settings;

import com.google.gson.JsonObject;
import gg.umbra.module.HackModule;
import gg.umbra.value.Value;

public class ConfigSettingsModule
extends HackModule {
    public void loadMatchingValues(JsonObject jsonObject) {
        for (Value<?, ?> value : this.getAllValues()) {
            if (!value.matchesJsonId(jsonObject)) continue;
            value.loadJson(jsonObject);
        }
    }

    public ConfigSettingsModule(String name) {
        super(name);
    }

}

