package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsHeaderComponent;

class ProfilesHeaderApplyPendingProfileClickHandler
implements GuiClickListener {
    private final ProfilesSettingsFrame profilesFrame;

    ProfilesHeaderApplyPendingProfileClickHandler(ProfilesSettingsFrame profilesFrame) {
        this.profilesFrame = profilesFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.profilesFrame.closePopupAndDiscardDraft();
    }
}
