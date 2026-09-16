package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class GameSettingsGuiScaleValue
extends Wrapper {
    public static int getAntialiasingLevel() {
        if (GameSettingsGuiScaleValue.umbraInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue == null) {
            return 0;
        }
        return GameSettingsGuiScaleValue.umbraInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue
                .getAntialiasingLevel();
    }


    public GameSettingsGuiScaleValue(Object valueHandle) {
        super(valueHandle);
    }
}

