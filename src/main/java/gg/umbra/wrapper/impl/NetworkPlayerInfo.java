package gg.umbra.wrapper.impl;

public class NetworkPlayerInfo
extends Packet {
    public PositionMoveRotation getValues() {
        return new PositionMoveRotation(NetworkPlayerInfo.umbraInstance.getMappingsMapperCompat().RH.getValues(this.I));
    }

    public NetworkPlayerInfo(Object handle) {
        super(handle);
    }

    public int getEntityId() {
        return NetworkPlayerInfo.umbraInstance.getMappingsMapperCompat().RH.getEntityId(this.I);
    }
}
