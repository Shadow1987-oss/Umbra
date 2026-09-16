package gg.umbra.wrapper.impl;

public class SEntityPacket
extends Packet {
    public int getDeltaY() {
        return SEntityPacket.umbraInstance.getMappingsMapperCompat().qC.getDeltaY(this.I);
    }

    public int getDeltaZ() {
        return SEntityPacket.umbraInstance.getMappingsMapperCompat().qC.getDeltaZ(this.I);
    }

    public Entity getEntity(World world) {
        return new Entity(SEntityPacket.umbraInstance.getMappingsMapperCompat().qC.getEntity(this.I, world.getObject()));
    }

    public SEntityPacket(Object handle) {
        super(handle);
    }

    public int getDeltaX() {
        return SEntityPacket.umbraInstance.getMappingsMapperCompat().qC.getDeltaX(this.I);
    }
}

