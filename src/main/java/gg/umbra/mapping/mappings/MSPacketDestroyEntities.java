package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.mappings.MMappedClassSlotNRegistration;

public class MSPacketDestroyEntities
extends Mapping {
    private final MappingField entityIdsField;

    public int[] getEntityIds(Object packet) {
        return this.entityIdsField.getIntArray(packet);
    }

    public MSPacketDestroyEntities() {
        this(MMappedClassSlotNRegistration.r());
    }

    private MSPacketDestroyEntities(int controlFlowState) {
        super(MappedClasses.qc);
        this.entityIdsField = this.J("a", false, int[].class);
    }

}

