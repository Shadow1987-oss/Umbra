package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MGameSettingsValue;
import gg.umbra.wrapper.Wrapper;

public class GameSettingsValue
extends Wrapper {
    public GameSettingsValue(Object object) {
        super(object);
    }

    public void a(Object object) {
        MGameSettingsValue.S(GameSettingsValue.umbraInstance.getMappingsMapperCompat().DM, this.I, object);
    }

    public void f(Object object) {
        GameSettingsValue.umbraInstance.getMappingsMapperCompat().DM.b(this.I, object);
    }

    public Object i() {
        return MGameSettingsValue.U(GameSettingsValue.umbraInstance.getMappingsMapperCompat().DM, this.I);
    }
}

