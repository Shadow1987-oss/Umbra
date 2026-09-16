package gg.umbra.event;

import gg.umbra.event.impl.EventPacketSend;
import gg.umbra.utils.network.PacketDispatchGuard;

public class PacketSendDispatchGuardCallback {
    private final EventPacketSend event;

    public PacketSendDispatchGuardCallback(EventPacketSend eventPacketSend) {
        this.event = eventPacketSend;
    }

    public void dispatch(PacketDispatchGuard dispatchGuard) {
        dispatchGuard.o(this.event);
    }
}
