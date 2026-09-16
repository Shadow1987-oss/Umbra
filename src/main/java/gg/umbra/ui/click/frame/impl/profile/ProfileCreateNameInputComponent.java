package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.ui.click.component.TextInputComponentBase;

public class ProfileCreateNameInputComponent
extends TextInputComponentBase {
    private final Profile profile;

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        String profileName = this.getText();
        Profile existingProfile = Umbra.INSTANCE.getProfilesManager().getProfileByName(profileName);
        if (existingProfile != null) {
            return;
        }
        this.profile.setName(profileName);
        this.profile.setDirty(true);
        Umbra.INSTANCE.getProfilesManager().addProfile(this.profile, true);
        Umbra.INSTANCE.getProfilesManager().setActiveProfile(this.profile);
        this.setText("");
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public double getComponentWidth() {
        return this.A() + 2.5;
    }


    public ProfileCreateNameInputComponent(String placeholder, Profile profile) {
        super(placeholder);
        this.profile = profile;
        this.setShowDisabledOverlay(false);
        this.setMaxLength(48);
    }
}
