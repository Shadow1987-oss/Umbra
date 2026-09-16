package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.config.Profile;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreateNameInputComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreatePanelComponent;

class ProfileCreateSubmitNameInputComponent
extends ProfileCreateNameInputComponent {
    private final ProfileCreatePanelComponent createPanel;

    @Override
    public void submit() {
        super.submit();
        this.createPanel.setPendingProfile(null);
        this.createPanel.getSettingsFrame().closePopupAndDiscardDraft();
        this.clearFocus();
    }

    ProfileCreateSubmitNameInputComponent(ProfileCreatePanelComponent createPanel, String placeholder, Profile profile) {
        super(placeholder, profile);
        this.createPanel = createPanel;
    }
}
