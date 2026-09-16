package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.ui.click.component.GuiComponent;

public class MPacketIdFactory
extends Mapping {
    private final MappingMethod confirmTeleportPacketConstructor;
    private static GuiComponent[] packetMappingControlFlowState;

    public static GuiComponent[] getPacketMappingControlFlowState() {
        return packetMappingControlFlowState;
    }

    public static void setPacketMappingControlFlowState(GuiComponent[] state) {
        packetMappingControlFlowState = state;
    }

    static {
        if (MPacketIdFactory.getPacketMappingControlFlowState() == null) {
            MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
        }
    }

    public Object createConfirmTeleportPacket(int teleportId) {
        return this.confirmTeleportPacketConstructor.newInstance(teleportId);
    }

    public MPacketIdFactory() {
        super(MappedClasses.Yq);
        this.confirmTeleportPacketConstructor = this.registerConstructor(new Class[]{Integer.TYPE});
    }
}

