package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class InputMappingsBridge
extends Wrapper {
    public InputMappingsBridge(Object object) {
        super(object);
    }

    public static InputMappingsInput x(int keyCode, int scanCode) {
        if (ForgeVersion.MC_1_21_10.d()) {
            InputMappingsInputFactory keyEvent = InputMappingsInputFactory.create(keyCode, scanCode, 0);
            return new InputMappingsInput(InputMappingsBridge.umbraInstance.getMappingsMapperCompat().CE.getInputByCode(keyEvent.getObject()));
        }
        return new InputMappingsInput(InputMappingsBridge.umbraInstance.getMappingsMapperCompat().CE.getInputByCode(keyCode, scanCode));
    }
}
