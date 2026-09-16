package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTickEventPhase;
import gg.umbra.wrapper.Wrapper;

public class TickEventPhase
extends Wrapper {
    public static TickEventPhase q() {
        return new TickEventPhase(MTickEventPhase.B(TickEventPhase.umbraInstance.getMappingsMapperCompat().R8));
    }

    public TickEventPhase(Object object) {
        super(object);
    }

    public static TickEventPhase J() {
        return new TickEventPhase(MTickEventPhase.G(TickEventPhase.umbraInstance.getMappingsMapperCompat().R8));
    }
}

