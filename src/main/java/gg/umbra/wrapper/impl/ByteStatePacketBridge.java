package gg.umbra.wrapper.impl;

public class ByteStatePacketBridge
extends Packet {
    public ByteStatePacketBridge(Object handle) {
        super(handle);
    }

    public byte getHeadYaw() {
        return ByteStatePacketBridge.umbraInstance.getMappingsMapperCompat().hy.getHeadYaw(this.I);
    }
}
