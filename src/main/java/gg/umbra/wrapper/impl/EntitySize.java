package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EntitySize
extends Wrapper {
    public float c() {
        return EntitySize.umbraInstance.getMappingsMapperCompat().h2.getWidth(this.I);
    }

    public float u() {
        return EntitySize.umbraInstance.getMappingsMapperCompat().h2.getHeight(this.I);
    }

    public EntitySize(Object object) {
        super(object);
    }
}

