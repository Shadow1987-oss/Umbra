package gg.umbra.wrapper.impl;

import com.google.common.base.Predicate;
import gg.umbra.wrapper.Wrapper;

public class EntitySelectors
extends Wrapper {
    public static Predicate getTeamCollisionPredicate(Entity entity) {
        return (Predicate)EntitySelectors.umbraInstance.getMappings().entitySelectors
                .getCollisionPredicate(entity.getObject());
    }

    public EntitySelectors(Object selectorsHandle) {
        super(selectorsHandle);
    }

    public static java.util.function.Predicate pushableBy(Entity entity) {
        return (java.util.function.Predicate)EntitySelectors.umbraInstance.getMappings().entitySelectors
                .getCollisionPredicate(entity.getObject());
    }
}
