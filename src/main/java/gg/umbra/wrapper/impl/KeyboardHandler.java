package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MKeyboardHandler;
import gg.umbra.wrapper.Wrapper;

public class KeyboardHandler
extends Wrapper {
    public KeyboardHandler(Object object) {
        super(object);
    }

    public String F() {
        return MKeyboardHandler.X(KeyboardHandler.umbraInstance.getMappingsMapperCompat().CR, this.I);
    }

    public void y(String string) {
        MKeyboardHandler.f(KeyboardHandler.umbraInstance.getMappingsMapperCompat().CR, this.I, string);
    }
}

