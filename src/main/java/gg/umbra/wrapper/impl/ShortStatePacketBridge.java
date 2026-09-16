package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.wrapper.impl.Packet;

public class ShortStatePacketBridge
extends Packet {
    public short getTransactionId() {
        return Umbra.INSTANCE.getMappingsMapperCompat().F.getTransactionId(this.I);
    }

    public ShortStatePacketBridge(Object handle) {
        super(handle);
    }
}
