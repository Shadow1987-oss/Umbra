package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class WorldInfo
extends Wrapper {
    public long getWorldTime() {
        return WorldInfo.umbraInstance.getMappingsMapperCompat().worldInfo.getWorldTime(this.I);
    }

    public WorldInfo(Object wrappedObject) {
        super(wrappedObject);
    }
}
