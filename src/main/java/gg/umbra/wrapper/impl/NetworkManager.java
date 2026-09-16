package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MNetworkManager;
import gg.umbra.wrapper.Wrapper;

public class NetworkManager
extends Wrapper {

    public void G(Packet packet) {
        if (ForgeVersion.MC_1_20_6.d()) {
            MNetworkManager.m(NetworkManager.umbraInstance.getMappingsMapperCompat().Do, this.I, packet.getObject(), null, true);
            return;
        }
        MNetworkManager.B(NetworkManager.umbraInstance.getMappingsMapperCompat().Do, this.I, packet.getObject());
    }

    public EntityFishHookState getChannel() {
        return new EntityFishHookState(MNetworkManager.getChannel(NetworkManager.umbraInstance.getMappingsMapperCompat().Do, this.I));
    }

    public NetworkPacketHandle c() {
        return new NetworkPacketHandle(MNetworkManager.U(NetworkManager.umbraInstance.getMappingsMapperCompat().Do, this.I));
    }

    public NetworkManager(Object object) {
        super(object);
    }
}

