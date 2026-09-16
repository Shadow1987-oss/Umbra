package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.mappings.MS08PacketPlayerPosLook;
import gg.umbra.ui.click.component.GuiComponent;

public class MNetworkPlayerInfo
extends Mapping {
    private final MappingField entityIdField;
    private final MappingField valuesField;

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

    public MNetworkPlayerInfo() {
        super(MappedClasses.ly);
        this.entityIdField = this.J("id", true, Integer.TYPE);
        this.valuesField = this.J("values", true, MappedClasses.Dd);
        if (GuiComponent.getLegacyComponentState() == null) {
            MS08PacketPlayerPosLook.setMappingControlFlowState(new String[5]);
        }
    }

    public Object getValues(Object packet) {
        return this.valuesField.getObject(packet);
    }
}

