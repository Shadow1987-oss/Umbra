/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.config;

import gg.umbra.config.Profile;
import gg.umbra.config.PublicProfilePrimaryDirtyStringValue;
import gg.umbra.config.PublicProfileSecondaryDirtyStringValue;
import gg.umbra.config.PublicProfileSelectedProfileStringValue;
import gg.umbra.config.PublicProfileSettingsBindSet;
import gg.umbra.config.PublicProfileSettingsBindValue;
import gg.umbra.ui.font.FontSelector;
import gg.umbra.unmap.ModeOption;
import gg.umbra.unmap.ModeSelection;
import gg.umbra.value.BindValue;
import gg.umbra.value.ToggleSetting;
import gg.umbra.value.OptionSetting;
import gg.umbra.value.SliderSetting;
import gg.umbra.value.StringValue;

public class PublicProfileSettings {
    public SliderSetting volume;
    public ToggleSetting profileSwitchNotifications;
    private Profile selectedProfile;
    public OptionSetting language;
    public ToggleSetting friendNotifications;
    public BindValue guiBind;
    public ToggleSetting muted;
    public ToggleSetting autoSave;
    public ToggleSetting notifications;
    public final ModeOption centralGuiStyle;
    public ToggleSetting autoLoadModuleStates;
    public final ModeOption framesGuiStyle;
    public StringValue alternateAccounts;
    private static int runtimeState;
    public ToggleSetting saveToCloud = ToggleSetting.create(this, "Save to Cloud", false, "Logs into an account in offline mode.");
    public OptionSetting guiStyle;
    public StringValue alteningKey;
    public StringValue selectedProfileId;
    public ToggleSetting framePositionsPerProfile;
    public ToggleSetting toggleAlerts;

    private void clearAlteningKeyWhenCloudEnabled(ToggleSetting saveToCloudValue) {
        if (saveToCloudValue.getEffectiveValue().booleanValue()) {
            this.alteningKey.setValue("");
        }
    }

    public static int getDefaultRuntimeState() {
        int currentState = PublicProfileSettings.getRuntimeState();
        return 0;
    }

    public static void setRuntimeState(int runtimeState) {
        PublicProfileSettings.runtimeState = runtimeState;
    }

    public PublicProfileSettings() {
        this.saveToCloud.addChangeListener(this::clearAlteningKeyWhenCloudEnabled);
        this.alteningKey = new PublicProfilePrimaryDirtyStringValue(this, this, "alteningKey", "");
        this.autoLoadModuleStates = ToggleSetting.create(this, "Auto-load module states", true, "Automatically enable saved module states upon loading, and when selecting profiles");
        this.alternateAccounts = new PublicProfileSecondaryDirtyStringValue(this, this, "alts", "");
        this.selectedProfileId = (StringValue)((StringValue)new PublicProfileSelectedProfileStringValue(this, this, "selectedprofile_uuid", "").setBase64Encoded(true)).addAlias("selectedprofile");
        this.autoSave = ToggleSetting.create(this, "Auto save", false, "Saves your active modules automatically when the game closes");
        this.framePositionsPerProfile = ToggleSetting.create(this, "Frame positions per profile", false, "Saves the positions of your GUI frames per profile");
        this.language = (OptionSetting)OptionSetting.create((Object)this, "Language", FontSelector.j, FontSelector.j, FontSelector.S, FontSelector.c, FontSelector.a, FontSelector.P).setResettable(false);
        this.volume = SliderSetting.create(this, "Volume", "#", "%", 0.0, 50.0, 100.0);
        this.muted = ToggleSetting.create(this, "Muted", false, "Mutes all sounds");
        this.guiBind = new PublicProfileSettingsBindValue(this, this, "GUI Bind", new PublicProfileSettingsBindSet(this, 161));
        this.framesGuiStyle = new ModeOption("Frames", 0.8);
        this.centralGuiStyle = new ModeOption("Central", 0.8);
        this.guiStyle = OptionSetting.create((Object)this, "GUI style", "Switch between the frames gui and the central gui", (ModeSelection)this.centralGuiStyle, this.framesGuiStyle, this.centralGuiStyle);
        this.notifications = ToggleSetting.create(this, "Notifications", true, "Shows notifications");
        this.toggleAlerts = ToggleSetting.create(this, "Toggle alert", false, "Notifies you if a module is enabled/disabled.");
        this.profileSwitchNotifications = ToggleSetting.create(this, "Profile switch", false, "Notifies you when you switch profiles");
        this.friendNotifications = ToggleSetting.create(this, "Friend notifications", true, "Shows notifications related to friends");
    }

    public Profile getSelectedProfile() {
        return this.selectedProfile;
    }

    public static Profile setSelectedProfile(PublicProfileSettings settings, Profile profile) {
        settings.selectedProfile = profile;
        return settings.selectedProfile;
    }

    public static int getRuntimeState() {
        return runtimeState;
    }

    static {
        PublicProfileSettings.setRuntimeState(36);
    }
}

