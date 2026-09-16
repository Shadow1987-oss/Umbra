package gg.umbra.wrapper.impl;

public class IntegerFactoryPacketBridge
extends Packet {
    public static IntegerFactoryPacketBridge createConfirmTeleportPacket(int teleportId) {
        return new IntegerFactoryPacketBridge(IntegerFactoryPacketBridge.umbraInstance.getMappingsMapperCompat().hp.createConfirmTeleportPacket(teleportId));
    }

    public IntegerFactoryPacketBridge(Object handle) {
        super(handle);
    }
}
