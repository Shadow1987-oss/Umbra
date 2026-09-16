package gg.umbra.wrapper.impl;

public class SPacketDestroyEntities
extends SPacketDestroyEntitiesBase {
    public int[] getEntityIds() {
        return SPacketDestroyEntities.umbraInstance.getMappingsMapperCompat().C7.getEntityIds(this.I);
    }

    public SPacketDestroyEntities(Object handle) {
        super(handle);
    }
}
