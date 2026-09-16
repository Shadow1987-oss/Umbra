package gg.umbra.visual.proj;

import gg.umbra.wrapper.impl.Entity;
import gg.umbra.wrapper.impl.EntityEnderPearl;

public class EnderPearlProjectileBridge
extends EntityEnderPearl {
    public EnderPearlProjectileBridge(Object handle) {
        super(handle);
    }

    public Entity getOwnerEntity() {
        Object ownerHandle = EnderPearlProjectileBridge.umbraInstance.getMappings()
                .enderPearlProjectileBridge.getOwner(this.getObject());
        return new Entity(ownerHandle);
    }
}
