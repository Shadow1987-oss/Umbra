package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SoundEvent
extends Wrapper {
    public SoundEvent(Object object) {
        super(object);
    }

    public ResourceLocation V() {
        return new ResourceLocation(SoundEvent.umbraInstance.getMappingsMapperCompat().Cb.getName(this.I));
    }
}
