package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MPacketIdFactory;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MCPacketEntityAction
extends Mapping {
    private MappingMethod actionIdConstructor;
    private MappingMethod actionConstructor;

    public MCPacketEntityAction() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketEntityAction(GuiComponent[] controlFlowState) {
        super(MappedClasses.Dj);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.Y()) {
                this.actionConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.zc, MappedClasses.Do});
            } else {
                this.actionIdConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.zc, Integer.TYPE});
            }
            return;
        }
        this.actionIdConstructor = this.Y("<init>", false, Void.TYPE, new Class[]{MappedClasses.zc, Integer.TYPE});
    }

    public Object createPacket(Object entity, int actionId) {
        return this.actionIdConstructor.newInstance(entity, actionId);
    }

    public Object createPacket(Object entity, Object action) {
        return this.actionConstructor.newInstance(entity, action);
    }
}
