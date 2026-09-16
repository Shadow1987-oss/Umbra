package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.ui.click.component.GuiClickListener;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsHeaderComponent;

class ProfilesHeaderShowAllRowsClickHandler
implements GuiClickListener {
    private final ProfilesSettingsFrame profilesFrame;

    ProfilesHeaderShowAllRowsClickHandler(ProfilesSettingsFrame profilesFrame) {
        this.profilesFrame = profilesFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.profilesFrame.showAllProfileRows();
    }

}

