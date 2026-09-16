package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDataComponentMap;
import gg.umbra.wrapper.Wrapper;

import java.util.Set;

public class DataComponentMap
extends Wrapper {
    public Object E(DataComponentType jr_12) {
        return DataComponentMap.umbraInstance.getMappingsMapperCompat().qk.j(this.I, jr_12.getObject());
    }

    public boolean V(DataComponentType jr_12) {
        return DataComponentMap.umbraInstance.getMappingsMapperCompat().qk.V(this.getObject(), jr_12.getObject());
    }

    public static DataComponentMap u() {
        return new DataComponentMap(MDataComponentMap.m(DataComponentMap.umbraInstance.getMappingsMapperCompat().qk));
    }

    public Set Z() {
        return (Set)MDataComponentMap.w(DataComponentMap.umbraInstance.getMappingsMapperCompat().qk, this.I);
    }

    public DataComponentMap(Object object) {
        super(object);
    }
}

