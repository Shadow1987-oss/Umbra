package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.ui.click.component.AnimatedIconButtonComponent;
import org.jetbrains.annotations.Nullable;

public class ProfileListEntryOpenButtonComponent
extends AnimatedIconButtonComponent {
    private Profile profile;
    private boolean selectedProfile;
    @Nullable
    private Runnable afterDelete;

    private void refreshActionMode() {
        if (this.profile == null) {
            return;
        }
        this.selectedProfile = Umbra.INSTANCE.getProfilesManager().getActiveProfile().equals(this.profile);
        if (this.selectedProfile) {
            this.w("You cannot delete your selected profile");
        } else {
            this.w("Delete this profile");
        }
        this.setIconResource("newtrash");
        this.getBackgroundAnimation().setEndColor(ProfileListEntryOpenButtonComponent.J.d);
    }

    private void handleClick() {
        if (this.profile == null) {
            return;
        }
        if (this.selectedProfile) {
            return;
        }
        Umbra.INSTANCE.getProfilesManager().removeProfile(this.profile);
        if (this.afterDelete != null) {
            this.afterDelete.run();
        }
    }

    public ProfileListEntryOpenButtonComponent(Profile profile, @Nullable Runnable afterDelete) {
        super("newtrash", ProfileListEntryOpenButtonComponent.J.d);
        this.profile = profile;
        this.afterDelete = afterDelete;
        this.setBorderRadius(2.0f);
        this.setBorderAlpha(1.0f);
        this.setIconScale(0.85);
        this.addClickListener(this::handleClick);
        this.refreshActionMode();
    }

    public ProfileListEntryOpenButtonComponent useOverlayStyle() {
        this.getBackgroundAnimation().setStartColor(ProfileListEntryOpenButtonComponent.J.l);
        this.setAnimatedBorderColor(ProfileListEntryOpenButtonComponent.J.l);
        this.setDisabledOverlayColor(ProfileListEntryOpenButtonComponent.J.m);
        return this;
    }

    @Override
    public void u() {
        this.refreshActionMode();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return this.profile;
    }
}
