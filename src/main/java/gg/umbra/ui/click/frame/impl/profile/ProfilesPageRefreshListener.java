package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.ui.click.component.GuiRefreshListener;
import gg.umbra.ui.click.frame.impl.main.ClickGuiProfilesPage;

public class ProfilesPageRefreshListener
implements GuiRefreshListener {
    final ClickGuiProfilesPage profilesPage;

    @Override
    public void onRefresh() {
        ClickGuiProfilesPage.updateCreateButtonState(this.profilesPage);
    }

    public ProfilesPageRefreshListener(ClickGuiProfilesPage kV) {
        this.profilesPage = kV;
    }
}
