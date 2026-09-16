package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MChannel;
import gg.umbra.wrapper.Wrapper;

public class Channel
extends Wrapper {
    public boolean usesBlockLight() {
        return MChannel.usesBlockLight(Channel.umbraInstance.getMappingsMapperCompat().bakedModel, this.I);
    }

    public Channel(Object handle) {
        super(handle);
    }
}
