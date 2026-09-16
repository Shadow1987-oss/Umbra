package gg.umbra.visual.pearl;

import gg.umbra.wrapper.impl.Entity;

public class PearlEspEnderPearlEntityWrapper
extends Entity {
    public PearlEspEnderPearlEntityWrapper(Object handle) {
        super(handle);
    }

    public int getAgeTicks() {
        return PearlEspEnderPearlEntityWrapper.umbraInstance.getMappings().entityEnderPearl.getFuse(this.I);
    }
}
