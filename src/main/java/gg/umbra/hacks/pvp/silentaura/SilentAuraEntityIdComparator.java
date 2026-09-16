package gg.umbra.hacks.pvp.silentaura;

import gg.umbra.wrapper.impl.EntityLivingBase;
import java.util.Comparator;

public class SilentAuraEntityIdComparator
implements Comparator<EntityLivingBase> {

    @Override
    public int compare(EntityLivingBase first, EntityLivingBase second) {
        return Integer.compare(first.c$src$I$15a9iwo(), second.c$src$I$15a9iwo());
    }
}
