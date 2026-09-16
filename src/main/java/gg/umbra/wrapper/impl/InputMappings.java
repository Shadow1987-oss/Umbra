package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MInputMappings;
import gg.umbra.wrapper.Wrapper;

public class InputMappings
extends Wrapper {
    public static InputMappings S(int n, int n2) {
        return new InputMappings(MInputMappings.k(InputMappings.umbraInstance.getMappingsMapperCompat().CW, n, n2));
    }

    public InputMappings(Object object) {
        super(object);
    }
}

