package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEnumCreatureAttribute;
import gg.umbra.wrapper.Wrapper;

public class DamageSource
extends Wrapper {
    public static DamageSource C(EntityPlayer entityPlayer) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return new DamageSource(MEnumCreatureAttribute.X(DamageSource.umbraInstance.getMappings().hW, Minecraft.theWorld().q(), entityPlayer.getObject()));
        }
        return new DamageSource(MEnumCreatureAttribute.X(DamageSource.umbraInstance.getMappings().hW, null, entityPlayer.getObject()));
    }


    public DamageSource(Object object) {
        super(object);
    }

    public static DamageSource m$src$Lgg_umbra_wrapper_impl_DamageSource_$z0ibym() {
        return new DamageSource(MEnumCreatureAttribute.E(DamageSource.umbraInstance.getMappings().hW));
    }
}

