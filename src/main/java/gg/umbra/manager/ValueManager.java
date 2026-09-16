package gg.umbra.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.umbra.Umbra;
import gg.umbra.module.Macro;
import gg.umbra.module.HackModule;
import gg.umbra.module.UtilityHack;
import gg.umbra.settings.ClientSettings;
import gg.umbra.settings.ConfigSettingsModule;
import gg.umbra.value.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueManager {
    private static final List<Class<?>> NON_SERIALIZED_OWNER_TYPES = Arrays.asList(HackModule.class, UtilityHack.class, Macro.class);
    private final List<Value<?, ?>> registeredValues = new ArrayList();
    private static final List<Class<?>> CONFIG_SETTINGS_OWNER_TYPES = Arrays.asList(ClientSettings.class);

    public List<Value<?, ?>> getValues() {
        return this.registeredValues;
    }

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (Value<?, ?> value : this.getValues()) {
            JsonObject serializedValue;
            if (NON_SERIALIZED_OWNER_TYPES.contains(value.getOwner().getClass()) || !value.isSerializable() || value.isDefault() || (serializedValue = value.toJson(false)).entrySet().size() <= 1) continue;
            result.add((JsonElement)serializedValue);
        }
        return result;
    }


    private void loadConfigSettings(JsonObject serializedValue) {
        for (HackModule mod : Umbra.INSTANCE.getHackManager().getAllModules()) {
            if (!(mod instanceof ConfigSettingsModule)) continue;
            ((ConfigSettingsModule)mod).loadMatchingValues(serializedValue);
        }
    }

    public void registerValue(Value<?, ?> value) {
        this.registeredValues.add(value);
    }

    public void loadJson(JsonArray serializedValues) {
        ArrayList loadedValues = new ArrayList();
        for (int index = 0; index < serializedValues.size(); ++index) {
            JsonObject serializedValue;
            JsonElement element = serializedValues.get(index);
            if (!element.isJsonObject() || element.isJsonNull() || (serializedValue = element.getAsJsonObject()).get("id") == null || serializedValue.get("id").isJsonNull()) continue;
            for (Value<?, ?> value : this.getValues()) {
                if (loadedValues.contains(value) || !CONFIG_SETTINGS_OWNER_TYPES.contains(value.getOwner().getClass().getSuperclass()) && !CONFIG_SETTINGS_OWNER_TYPES.contains(value.getOwner().getClass()) && NON_SERIALIZED_OWNER_TYPES.contains(value.getOwner().getClass().getSuperclass()) || NON_SERIALIZED_OWNER_TYPES.contains(value.getOwner().getClass()) || !value.matchesJsonId(serializedValue)) continue;
                loadedValues.add(value);
                value.loadJson(serializedValue);
            }
        }
    }
}

