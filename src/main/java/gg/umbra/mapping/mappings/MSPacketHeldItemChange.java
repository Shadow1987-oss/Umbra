package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;

public class MSPacketHeldItemChange
extends Mapping {
    private final MappingMethod closeWindowPacketConstructor;

    public MSPacketHeldItemChange() {
        super(MappedClasses.l7);
        this.closeWindowPacketConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{Integer.TYPE});
    }

    public Object createCloseWindowPacket(int windowId) {
        return this.closeWindowPacketConstructor.newInstance(windowId);
    }
}

