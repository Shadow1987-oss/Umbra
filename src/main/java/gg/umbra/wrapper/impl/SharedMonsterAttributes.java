package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SharedMonsterAttributes
extends Wrapper {
    public SharedMonsterAttributes(Object object) {
        super(object);
    }

    public static SharedMonsterAttributes V() {
        return new SharedMonsterAttributes(SharedMonsterAttributes.umbraInstance.getMappingsMapperCompat().h3.getSeeThrough());
    }

    public static SharedMonsterAttributes c() {
        return new SharedMonsterAttributes(SharedMonsterAttributes.umbraInstance.getMappingsMapperCompat().h3.getNormal());
    }
}

