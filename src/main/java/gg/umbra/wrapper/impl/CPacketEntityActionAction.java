package gg.umbra.wrapper.impl;

public class CPacketEntityActionAction
extends CPacketEntityAction {
    public static CPacketEntityActionAction startSneaking() {
        return new CPacketEntityActionAction(CPacketEntityActionAction.umbraInstance.getMappingsMapperCompat().DQ.getStartSneakingAction());
    }

    public static CPacketEntityActionAction stopSneaking() {
        return new CPacketEntityActionAction(CPacketEntityActionAction.umbraInstance.getMappingsMapperCompat().DQ.getStopSneakingAction());
    }

    public CPacketEntityActionAction(Object handle) {
        super(handle);
    }
}
