package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDataComponents;
import gg.umbra.wrapper.Wrapper;

public class DataComponents
extends Wrapper {
    public DataComponents(Object object) {
        super(object);
    }

    public static DataComponentType E() {
        return new DataComponentType(MDataComponents.E(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType V() {
        return new DataComponentType(MDataComponents.m(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType l() {
        return new DataComponentType(MDataComponents.T(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType X() {
        return new DataComponentType(MDataComponents.e(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType p() {
        return new DataComponentType(MDataComponents.L(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType k() {
        return new DataComponentType(MDataComponents.F(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType d() {
        return new DataComponentType(MDataComponents.a(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType F() {
        return new DataComponentType(MDataComponents.y(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType T() {
        return new DataComponentType(MDataComponents.M(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType o() {
        return new DataComponentType(MDataComponents.h(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType R() {
        return new DataComponentType(MDataComponents.O(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType t() {
        return new DataComponentType(MDataComponents.I(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType O() {
        return new DataComponentType(MDataComponents.z(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }

    public static DataComponentType I() {
        return new DataComponentType(MDataComponents.b(DataComponents.umbraInstance.getMappingsMapperCompat().q5));
    }
}

