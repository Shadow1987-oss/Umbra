package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityRemovalReason;
import gg.umbra.wrapper.Wrapper;

public class EntityRemovalReason
extends Wrapper {
    public static EntityRemovalReason U() {
        return new EntityRemovalReason(MEntityRemovalReason.U(EntityRemovalReason.umbraInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason X() {
        return new EntityRemovalReason(MEntityRemovalReason.N(EntityRemovalReason.umbraInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason c() {
        return new EntityRemovalReason(MEntityRemovalReason.Z(EntityRemovalReason.umbraInstance.getMappingsMapperCompat().h));
    }

    public EntityRemovalReason(Object object) {
        super(object);
    }

    public static EntityRemovalReason K() {
        return new EntityRemovalReason(MEntityRemovalReason.k(EntityRemovalReason.umbraInstance.getMappingsMapperCompat().h));
    }

    public static EntityRemovalReason P() {
        return new EntityRemovalReason(MEntityRemovalReason.i(EntityRemovalReason.umbraInstance.getMappingsMapperCompat().h));
    }
}

