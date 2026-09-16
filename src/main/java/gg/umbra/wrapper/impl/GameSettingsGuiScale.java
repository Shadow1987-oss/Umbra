package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGameSettingsGuiScale;
import gg.umbra.wrapper.Wrapper;

public class GameSettingsGuiScale
extends Wrapper {
    public GameSettingsGuiScale(Object handle) {
        super(handle);
    }

    public Vec3 getBase() {
        return new Vec3(MGameSettingsGuiScale.getBase(GameSettingsGuiScale.umbraInstance.getMappingsMapperCompat().vecDeltaCodec, this.I));
    }
}
