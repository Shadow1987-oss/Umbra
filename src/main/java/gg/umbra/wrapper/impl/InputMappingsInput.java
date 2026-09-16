package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MInputMappingsInput;
import gg.umbra.wrapper.Wrapper;

public class InputMappingsInput
extends Wrapper {
    public InputMappingsInput(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getKeyCode() {
        return MInputMappingsInput.getKeyCode(InputMappingsInput.umbraInstance.getMappingsMapperCompat().hm, this.I);
    }
}
