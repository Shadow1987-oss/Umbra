package gg.umbra.wrapper.impl;

public class EntityOtherPlayerMP
extends AbstractClientPlayer {
    public EntityOtherPlayerMP(Object entityHandle) {
        super(entityHandle);
    }

    public static EntityOtherPlayerMP create(World world, GameProfile gameProfile) {
        return new EntityOtherPlayerMP(EntityOtherPlayerMP.umbraInstance.getMappingsMapperCompat().entityOtherPlayer.create(
                world.getObject(), gameProfile.getObject()));
    }
}
