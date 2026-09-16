package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRegistry;
import gg.umbra.wrapper.Wrapper;

import java.util.Optional;
import java.util.stream.Stream;

public class Registry
extends Wrapper {
    public Object t(int n) {
        return MRegistry.p(Registry.umbraInstance.getMappingsMapperCompat().CV, this.I, n);
    }

    public int K(Object object) {
        return MRegistry.e(Registry.umbraInstance.getMappingsMapperCompat().CV, this.I, object);
    }

    public ResourceLocation W(Object object) {
        return new ResourceLocation(MRegistry.F(Registry.umbraInstance.getMappingsMapperCompat().CV, this.I, object));
    }

    public Optional<Holder> t(ResourceLocation resourceLocation) {
        Object object = this.B(resourceLocation);
        if (object == null) {
            return Optional.empty();
        }
        return Optional.of(this.J(object));
    }


    public Object B(ResourceLocation resourceLocation) {
        return MRegistry.d(Registry.umbraInstance.getMappingsMapperCompat().CV, this.I, resourceLocation.getObject());
    }

    public Stream o() {
        return MRegistry.h(Registry.umbraInstance.getMappingsMapperCompat().CV, this.getObject());
    }

    public Holder J(Object object) {
        return new Holder(MRegistry.S(Registry.umbraInstance.getMappingsMapperCompat().CV, this.I, object));
    }

    public Registry(Object object) {
        super(object);
    }
}

