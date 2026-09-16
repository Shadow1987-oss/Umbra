package gg.umbra.ui.click.frame.impl.profile;

import gg.umbra.config.Profile;
import gg.umbra.ui.click.component.GlyphIconComponent;
import org.jetbrains.annotations.Nullable;

public class ProfileGlyphIconPanel
extends GlyphIconComponent {
    private Profile profile;

    public ProfileGlyphIconPanel(@Nullable Profile profile) {
        this(profile, 6.0, 8.0);
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void refreshVisibility() {
        this.setVisible(false);
    }

    public ProfileGlyphIconPanel(@Nullable Profile profile, double iconWidth, double iconHeight) {
        super("external link hover@2x", iconWidth, iconWidth, iconHeight, iconHeight, null, null, null);
        this.profile = profile;
        this.setNormalColor(java.awt.Color.WHITE);
        this.w("View public profile");
        this.refreshVisibility();
    }
}
