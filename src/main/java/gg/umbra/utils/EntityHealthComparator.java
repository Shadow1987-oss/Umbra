package gg.umbra.utils;

import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityLivingBase;
import java.util.Comparator;

public class EntityHealthComparator
implements Comparator<Entity> {
    private float getHealth(Entity entity) {
        EntityLivingBase entityLivingBase = new EntityLivingBase(entity.getObject());
        return entityLivingBase.w$src$F$15l9epb();
    }

    @Override
    public int compare(Entity entity, Entity entity2) {
        return Float.compare(this.getHealth(entity), this.getHealth(entity2));
    }
}

