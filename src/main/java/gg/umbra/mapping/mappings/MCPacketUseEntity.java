package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.Wrapper;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MCPacketUseEntity
extends Mapping {
    private MappingField interactAtActionField;
    private final MappingField attackActionField;
    private final MappingField interactActionField;

    public Object getAttackAction() {
        return this.attackActionField.getObject(null);
    }

    public Object getInteractAtAction() {
        return this.interactAtActionField.getObject(null);
    }

    public Object getInteractAction() {
        return this.interactActionField.getObject(null);
    }

    public MCPacketUseEntity() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketUseEntity(GuiComponent[] controlFlowState) {
        super(MappedClasses.D5);
        if (controlFlowState != null) {
            this.interactActionField = this.registerStaticField("INTERACT", Wrapper.isNativeAvailable, MappedClasses.D5);
            this.attackActionField = this.registerStaticField("ATTACK", Wrapper.isNativeAvailable, MappedClasses.D5);
            if (ForgeVersion.MC_1_8_9.d()) {
                this.interactAtActionField = this.registerStaticField("INTERACT_AT", Wrapper.isNativeAvailable, MappedClasses.D5);
            }
            return;
        }
        this.interactActionField = this.registerStaticField("INTERACT", Wrapper.isNativeAvailable, MappedClasses.D5);
        this.interactAtActionField = this.registerStaticField("ATTACK", Wrapper.isNativeAvailable, MappedClasses.D5);
        this.attackActionField = null;
    }
}

