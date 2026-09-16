package gg.umbra.utils;

import gg.umbra.utils.RotationUtil;
import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityPlayerSP;
import gg.umbra.wrapper.impl.Minecraft;
import java.util.Comparator;

public class EntityAngleComparator
implements Comparator<Entity> {
    @Override
    public int compare(Entity firstEntity, Entity secondEntity) {
        return this.compareByAngle(firstEntity, secondEntity);
    }

    public int compareByAngle(Entity firstEntity, Entity secondEntity) {
        EntityPlayerSP player = Minecraft.thePlayer();
        return Integer.compare(RotationUtil.a(player, firstEntity), RotationUtil.a(player, secondEntity));
    }
}
