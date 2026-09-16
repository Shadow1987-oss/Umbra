package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class PlaySoundAtEntityEvent
extends Wrapper {
    public PlaySoundAtEntityEvent(Object object) {
        super(object);
    }

    public String getName() {
        return PlaySoundAtEntityEvent.umbraInstance.getMappingsMapperCompat().C5.Z(this.I);
    }
}

