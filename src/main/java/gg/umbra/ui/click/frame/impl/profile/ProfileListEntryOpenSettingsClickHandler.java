package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.profile.ProfileListEntryComponent;

class ProfileListEntryOpenSettingsClickHandler
implements GuiClickListener {
    private final ProfileListEntryComponent entry;
    private final Profile profile;

    ProfileListEntryOpenSettingsClickHandler(ProfileListEntryComponent entry, Profile profile) {
        this.entry = entry;
        this.profile = profile;
    }

    @Override
    public void onPrimaryClick() {
        if (Umbra.INSTANCE.getProfilesManager().getActiveProfile().equals(this.profile)) {
            this.profile.captureCurrentState();
        }
        this.entry.openSettings();
    }

}

