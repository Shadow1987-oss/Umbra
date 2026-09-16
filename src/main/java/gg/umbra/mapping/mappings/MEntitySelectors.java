package gg.umbra.mapping.mappings;

import com.google.common.base.Predicate;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.MappingMethodBuilder;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MEntitySelectors
extends Mapping {
    private final MappingMethod collisionPredicateMethod;

    public Object getCollisionPredicate(Object entityHandle) {
        return this.collisionPredicateMethod.invokeObject(null, entityHandle);
    }

    public MEntitySelectors() {
        super(MappedClasses.qW);
        this.collisionPredicateMethod = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this
                .methodBuilder("getTeamCollisionPredicate", Predicate.class, MappedClasses.zc)
                .setTypeForVersion(ForgeVersion.MC_1_16_5.n(), java.util.function.Predicate.class))
                .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "pushableBy"))
                .setStaticMember(true))
                .buildMethod();
    }
}

