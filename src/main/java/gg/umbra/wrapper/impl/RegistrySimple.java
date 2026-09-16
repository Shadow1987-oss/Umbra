package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRegistrySimple;
import gg.umbra.wrapper.Wrapper;
import java.util.Set;

public class RegistrySimple
extends Wrapper {
    public Object S(Object object) {
        return MRegistrySimple.s(RegistrySimple.umbraInstance.getMappings().hD, this.I, object);
    }

    public Set D() {
        return RegistrySimple.umbraInstance.getMappings().hD.g(this.I);
    }

    public RegistrySimple(Object object) {
        super(object);
    }
}

