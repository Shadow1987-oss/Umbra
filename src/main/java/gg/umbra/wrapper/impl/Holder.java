package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MHolder;
import gg.umbra.wrapper.Wrapper;

import java.util.Optional;

public class Holder
extends Wrapper {
    public boolean F(ResourceKey jy_12) {
        return MHolder.q(Holder.umbraInstance.getMappingsMapperCompat().hN, this.I, jy_12.getObject());
    }

    public String Z() {
        return MHolder.F(Holder.umbraInstance.getMappingsMapperCompat().hN, this.I);
    }

    public static Holder A(Object object) {
        return new Holder(MHolder.f(Holder.umbraInstance.getMappingsMapperCompat().hN, object));
    }

    public Holder(Object object) {
        super(object);
    }

    public Object N() {
        return MHolder.E(Holder.umbraInstance.getMappingsMapperCompat().hN, this.I);
    }

    public Optional f() {
        return (Optional)MHolder.m(Holder.umbraInstance.getMappingsMapperCompat().hN, this.I);
    }
}

