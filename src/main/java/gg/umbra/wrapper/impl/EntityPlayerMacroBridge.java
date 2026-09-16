package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MEntityPlayerMacroBridge;
import gg.umbra.visual.proj.EnderPearlProjectileBridge;

public class EntityPlayerMacroBridge
extends Entity {
    public Entity A$src$Lgg_umbra_wrapper_impl_Entity_$12ijiu4() {
        if (ForgeVersion.MC_1_16_5.v()) {
            return new EntityPlayer(MEntityPlayerMacroBridge.F(EntityPlayerMacroBridge.umbraInstance.getMappings().Da, this.I));
        }
        return new EnderPearlProjectileBridge(this.getObject()).getOwnerEntity();
    }

    public Entity r$src$Lgg_umbra_wrapper_impl_Entity_$18p7x3h() {
        return new Entity(MEntityPlayerMacroBridge.W(EntityPlayerMacroBridge.umbraInstance.getMappings().Da, this.I));
    }


    public EntityPlayerMacroBridge(Object object) {
        super(object);
    }

    public boolean o() {
        return MEntityPlayerMacroBridge.B(EntityPlayerMacroBridge.umbraInstance.getMappings().Da, this.I);
    }
}

