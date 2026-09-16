package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsHeaderComponent;

class ProfilesHeaderShowActiveRowsClickHandler
implements GuiClickListener {
    private final ProfilesSettingsFrame profilesFrame;

    ProfilesHeaderShowActiveRowsClickHandler(ProfilesSettingsFrame profilesFrame) {
        this.profilesFrame = profilesFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.profilesFrame.showActiveProfileRows();
    }

}

