package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class WorldProvider
extends Wrapper {
    public boolean hasNoSky() {
        return WorldProvider.umbraInstance.getMappingsMapperCompat().worldProvider.hasNoSky(this.I);
    }

    public WorldProvider(Object wrappedObject) {
        super(wrappedObject);
    }
}
