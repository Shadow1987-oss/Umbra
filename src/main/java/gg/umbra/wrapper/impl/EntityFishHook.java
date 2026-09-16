package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class EntityFishHook
extends Wrapper {
    public EntityFishHook(Object object) {
        super(object);
    }

    public boolean o() {
        return EntityFishHook.umbraInstance.getMappingsMapperCompat().Cr.isEmpty(this.I);
    }

    public AxisAlignedBB n() {
        return new AxisAlignedBB(EntityFishHook.umbraInstance.getMappingsMapperCompat().Cr.getBoundingBox(this.I));
    }
}

