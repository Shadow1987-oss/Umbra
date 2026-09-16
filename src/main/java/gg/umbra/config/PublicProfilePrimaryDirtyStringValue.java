/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.config;

import gg.umbra.config.PublicProfileSettings;
import gg.umbra.value.StringValue;

public class PublicProfilePrimaryDirtyStringValue
extends StringValue {
    final PublicProfileSettings settings;
    boolean dirty;

    @Override
    public void notifyChangeListeners() {
        if (this.dirty) {
            return;
        }
        this.dirty = true;
    }

    public PublicProfilePrimaryDirtyStringValue(PublicProfileSettings settings, Object owner, String name, String defaultValue) {
        super(owner, name, defaultValue);
        this.settings = settings;
    }
}

