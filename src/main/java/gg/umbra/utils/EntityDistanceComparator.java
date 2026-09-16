package gg.umbra.utils;

import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Comparator;

public class EntityDistanceComparator
implements Comparator<Entity> {
    @Override
    public int compare(Entity firstEntity, Entity secondEntity) {
        return this.compareByDistance(firstEntity, secondEntity);
    }

    public int compareByDistance(Entity firstEntity, Entity secondEntity) {
        EntityPlayerSP player = Minecraft.thePlayer();
        return Float.compare(player.getDistanceToEntity(firstEntity), player.getDistanceToEntity(secondEntity));
    }
}
