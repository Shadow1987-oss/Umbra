package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.mappings.MSPacketEntityVelocity;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MSPacketEntityStatus
extends Mapping {
    private MappingField entityIdField;
    private MappingField logicOpcodeField;

    public MSPacketEntityStatus() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSPacketEntityStatus(int[] controlFlowState) {
        super(MappedClasses.lU);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.L()) {
                this.entityIdField = this.J("field_149164_a", true, Integer.TYPE);
                this.logicOpcodeField = this.J("field_149163_b", true, Byte.TYPE);
            } else {
                this.entityIdField = this.J("entityId", true, Integer.TYPE);
                this.logicOpcodeField = this.J("logicOpcode", true, Byte.TYPE);
            }
            return;
        }
        this.logicOpcodeField = this.J("logicOpcode", true, Byte.TYPE);
    }

    public byte getLogicOpcode(Object packet) {
        return (byte)this.logicOpcodeField.getInt(packet);
    }

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

}

