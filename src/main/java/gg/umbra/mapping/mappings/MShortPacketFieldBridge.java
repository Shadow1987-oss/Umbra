package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MShortPacketFieldBridge
extends Mapping {
    private final MappingField transactionIdField;

    public short getTransactionId(Object packet) {
        return this.transactionIdField.getShort(packet);
    }

    public MShortPacketFieldBridge() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MShortPacketFieldBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.zy);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.L() && !Wrapper.umbraInstance.isVanillaMinecraftPresent()) {
                this.transactionIdField = this.J("field_149534_b", Wrapper.isNativeAvailable, Short.TYPE);
            } else {
                this.transactionIdField = this.J("uid", true, Short.TYPE);
            }
            return;
        }
        if (!ForgeVersion.MC_1_7_10.L()) {
            this.J("field_149534_b", Wrapper.isNativeAvailable, Short.TYPE);
        }
        this.transactionIdField = this.J("uid", true, Short.TYPE);
    }
}
