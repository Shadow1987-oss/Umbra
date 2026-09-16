/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package gg.umbra.config;

import com.google.gson.JsonObject;
import gg.umbra.config.PublicProfileSettings;
import gg.umbra.input.BindSet;
import gg.umbra.value.BindValue;

public class PublicProfileSettingsBindValue
extends BindValue {
    final PublicProfileSettings settings;

    @Override
    public boolean loadJson(JsonObject object) {
        boolean loaded = super.loadJson(object);
        return loaded;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    public PublicProfileSettingsBindValue(PublicProfileSettings settings, Object owner, String name, BindSet bindSet) {
        super(owner, name, bindSet);
        this.settings = settings;
    }
}

