package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGameProfile;
import gg.umbra.wrapper.Wrapper;
import java.util.UUID;

public class GameProfile
extends Wrapper {
    public GameProfile(Object object) {
        super(object);
    }

    public static GameProfile create(UUID uUID, String string) {
        return new GameProfile(MGameProfile.N(GameProfile.umbraInstance.getMappingsMapperCompat().d, uUID, string));
    }

    public String getName() {
        return MGameProfile.P(GameProfile.umbraInstance.getMappingsMapperCompat().d, this.I);
    }

    public void setName(String string) {
        MGameProfile.z(GameProfile.umbraInstance.getMappingsMapperCompat().d).setObject(this.I, string);
    }

    public UUID getUUID() {
        return MGameProfile.h(GameProfile.umbraInstance.getMappingsMapperCompat().d, this.I);
    }
}

