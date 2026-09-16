package gg.umbra.wrapper.impl;

public class CPacketUseEntityActionPacket
extends CPacketUseEntityAction {
    public CPacketUseEntityActionPacket(Object wrappedObject) {
        super(wrappedObject, null);
    }

    public Vec3 getLocation() {
        return new Vec3(CPacketUseEntityActionPacket.umbraInstance.getMappingsMapperCompat().h6.getLocation(this.I));
    }
}
