package gg.umbra.wrapper.impl;

public class SPacketEntityTeleport
extends Packet {
    public SPacketEntityTeleport(Object handle) {
        super(handle);
    }

    public int getZ() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getZ(this.I);
    }

    public byte getYaw() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getYaw(this.I);
    }

    public byte getPitch() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getPitch(this.I);
    }

    public int getY() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getY(this.I);
    }

    public int getEntityId() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getEntityId(this.I);
    }

    public int getX() {
        return SPacketEntityTeleport.umbraInstance.getMappings().qH.getX(this.I);
    }
}
