package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MCPacketHeldItemChange
extends Mapping {
    private final MappingMethod constructor;
    private final MappingField selectedSlotField;

    public MCPacketHeldItemChange() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketHeldItemChange(GuiComponent[] controlFlowState) {
        super(MappedClasses.e);
        if (ForgeVersion.MC_1_8_9.L()) {
            this.selectedSlotField = this.J("slotId", true, Integer.TYPE);
        } else if (ForgeVersion.MC_1_7_10.L() && Wrapper.umbraInstance.isVanillaMinecraftPresent()) {
            this.selectedSlotField = this.J("slotId", true, Integer.TYPE);
        } else {
            this.selectedSlotField = this.J("field_149615_a", Wrapper.isNativeAvailable, Integer.TYPE);
        }
        this.constructor = this.Y("<init>", false, Void.TYPE, new Class[]{Integer.TYPE});
    }

    public void setSelectedSlot(Object packet, int selectedSlot) {
        this.selectedSlotField.setInt(packet, selectedSlot);
    }

    public int getSelectedSlot(Object packet) {
        return this.selectedSlotField.getInt(packet);
    }

    public Object createPacket(int selectedSlot) {
        return this.constructor.newInstance(selectedSlot);
    }
}
