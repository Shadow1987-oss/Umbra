package gg.umbra.wrapper.impl;

public class SPacketHeldItemChange
extends Packet {
    public SPacketHeldItemChange(Object handle) {
        super(handle);
    }

    public static SPacketHeldItemChange createCloseWindowPacket(int windowId) {
        return new SPacketHeldItemChange(SPacketHeldItemChange.umbraInstance.getMappingsMapperCompat().C6.createCloseWindowPacket(windowId));
    }
}
