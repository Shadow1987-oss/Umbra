package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MNetHandlerPlayClient;

public class NetHandlerPlayClient
extends NetworkPacketHandle {
    public void handleEntityTeleport(SPacketEntity teleportPacket) {
        MNetHandlerPlayClient.handleEntityTeleport(NetHandlerPlayClient.umbraInstance.getMappingsMapperCompat().netHandlerPlayClient, this.I, teleportPacket.getObject());
    }

    public NetHandlerPlayClient(Object handle) {
        super(handle);
    }
}
