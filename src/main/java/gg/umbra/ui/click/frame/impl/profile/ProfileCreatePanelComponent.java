package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.settings.ClientSettings;
import gg.umbra.ui.click.GuiMouseEvent;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.ui.click.component.PanelComponent;
import gg.umbra.ui.click.frame.CenteredPopupFrame;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreateActionButtonComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreateDividerComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreateNameInputComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfileCreateSubmitNameInputComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfileModuleSnapshotListComponent;
import gg.umbra.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import java.util.UUID;

public class ProfileCreatePanelComponent
extends GuiComponent {
    private final ProfileCreateActionButtonComponent createButton;
    private Profile pendingProfile;
    private final ProfileCreateNameInputComponent nameInput;
    private final ProfilesSettingsFrame settingsFrame;
    private final ProfileCreateDividerComponent divider;

    @Override
    public void I() {
    }

    public ProfileCreateNameInputComponent getNameInput() {
        return this.nameInput;
    }

    @Override
    public void F() {
    }

    public Profile getPendingProfile() {
        return this.pendingProfile;
    }

    @Override
    public void u() {
    }

    private void startProfileCreation() {
        Profile activeProfile = Umbra.INSTANCE.getProfilesManager().getActiveProfile();
        activeProfile.captureCurrentState();
        this.pendingProfile = activeProfile;
        Profile draftProfile = new Profile(activeProfile.getName(), Umbra.VERSION);
        draftProfile.loadJson(activeProfile.toJson(true));
        draftProfile.setLocalId(UUID.randomUUID());
        draftProfile.setOnlineId(null);
        draftProfile.setPublicProfileFlag(false);
        draftProfile.setDraft(true);
        Umbra.INSTANCE.getProfilesManager().switchProfile(draftProfile);
        PanelComponent popupContent = new PanelComponent(this.settingsFrame.A(), this.settingsFrame.getContentLayout().L());
        popupContent.l$src$Lgg_umbra_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        ProfileCreateSubmitNameInputComponent submitNameInput = new ProfileCreateSubmitNameInputComponent(this, "Type name", draftProfile);
        submitNameInput.o(this.settingsFrame.A() - 2.0);
        submitNameInput.Y(22.5);
        popupContent.h(submitNameInput, new Object[0]);
        popupContent.h(new ProfileModuleSnapshotListComponent(draftProfile, 105.0, 110.0), new Object[0]);
        CenteredPopupFrame popup = ClientSettings.createPopup(this.settingsFrame.getContentLayout(), popupContent, CenteredPopupFrame.class);
        this.settingsFrame.setActivePopup(popup);
        this.settingsFrame.i$src$Lgg_umbra_ui_click_frame_FrameToolbarComponent_$gnpgc6().showBackNavigation("New Profile", false);
    }

    public ProfileCreatePanelComponent(ProfilesSettingsFrame profilesSettingsFrame) {
        this.createButton = new ProfileCreateActionButtonComponent("Create new", true, false, 0.8, null, "newadd", 0.8, J.z(), ProfileCreatePanelComponent.J.l);
        this.nameInput = new ProfileCreateNameInputComponent("Type name", null);
        this.divider = new ProfileCreateDividerComponent();
        this.settingsFrame = profilesSettingsFrame;
        this.createButton.addClickListener(this::startProfileCreation);
        this.createButton.w("Create a new profile");
        this.addChildren(this.createButton, this.divider);
    }

    ProfilesSettingsFrame getSettingsFrame() {
        return this.settingsFrame;
    }

    @Override
    public void H() {
        this.createButton.setTextScale(0.7);
        this.createButton.setIconOffset(2.0);
        this.createButton.K(this.G$src$D$1b2f02a() + 5.0);
        this.createButton.S(this.n());
        this.createButton.Y(this.L() - 5.5);
        this.divider.setVisible(false);
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void g(GuiMouseEvent event) {
    }

    public void setPendingProfile(Profile profile) {
        this.pendingProfile = profile;
    }

    @Override
    public double x() {
        return 110.0;
    }
}
