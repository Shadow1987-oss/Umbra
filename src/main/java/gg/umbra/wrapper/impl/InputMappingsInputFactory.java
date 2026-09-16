package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class InputMappingsInputFactory
extends Wrapper {
    public static InputMappingsInputFactory create(int keyCode, int scanCode, int modifiers) {
        return new InputMappingsInputFactory(InputMappingsInputFactory.umbraInstance.getMappingsMapperCompat().Du.createKeyEvent(keyCode, scanCode, modifiers));
    }

    public InputMappingsInputFactory(Object handle) {
        super(handle);
    }
}
