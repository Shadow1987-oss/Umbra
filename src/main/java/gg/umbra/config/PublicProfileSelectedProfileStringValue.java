/*
 * Decompiled with CFR 0.152.
 */
package gg.umbra.config;

import gg.umbra.Umbra;
import gg.umbra.config.Profile;
import gg.umbra.config.PublicProfileSettings;
import gg.umbra.utils.StringUtils;
import gg.umbra.value.StringValue;
import java.util.UUID;

public class PublicProfileSelectedProfileStringValue
extends StringValue {
    final PublicProfileSettings settings;

    @Override
    public String getValue() {
        Profile activeProfile = Umbra.INSTANCE.getProfilesManager().getActiveProfile();
        UUID onlineId = activeProfile.getOnlineId();
        if (onlineId == null) {
            return "";
        }
        return onlineId.toString();
    }

    public PublicProfileSelectedProfileStringValue(PublicProfileSettings settings, Object owner, String name, String defaultValue) {
        super(owner, name, defaultValue);
        this.settings = settings;
    }

    @Override
    public void setValue(String profileIdentifier) {
        super.setValue(profileIdentifier);
        boolean isUuid = StringUtils.n(profileIdentifier);
        if (isUuid) {
            Profile profile = Umbra.INSTANCE.getProfilesManager().getProfileByOnlineId(UUID.fromString(profileIdentifier));
            if (profile != null) {
                PublicProfileSettings.setSelectedProfile(this.settings, profile);
            }
        } else {
            Profile profile = Umbra.INSTANCE.getProfilesManager().getProfileByName(profileIdentifier);
            if (profile != null) {
                PublicProfileSettings.setSelectedProfile(this.settings, profile);
            }
        }
    }
}

