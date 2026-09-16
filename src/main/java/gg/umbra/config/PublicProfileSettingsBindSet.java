/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.config;

import gg.umbra.config.PublicProfileSettings;
import gg.umbra.input.BindSet;

public class PublicProfileSettingsBindSet
extends BindSet {
    final PublicProfileSettings publicProfileSettings;

    @Override
    public void onBindActivated() {
    }

    public PublicProfileSettingsBindSet(PublicProfileSettings publicProfileSettings, int keyCode) {
        super(keyCode);
        this.publicProfileSettings = publicProfileSettings;
    }
}

