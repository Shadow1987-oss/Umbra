package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;
import gg.umbra.mapping.MappingFieldBuilder;
import gg.umbra.mapping.mappings.MChestTypeHolder;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MMLGBlockWrapper
extends Mapping {
    private final MappingField lavaField;
    private final MappingField waterField;

    public static Object getLava(MMLGBlockWrapper mapping) {
        return mapping.readLava();
    }

    private Object readLava() {
        return this.lavaField.getObject(null);
    }


    public MMLGBlockWrapper() {
        this(MChestTypeHolder.getChestTypeHolderControlFlowState());
    }

    private MMLGBlockWrapper(boolean controlFlowState) {
        super(MappedClasses.Za);
        this.waterField = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder("WATER", MappedClasses.W).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.qC)).setStaticMember(true)).buildField();
        this.lavaField = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder("LAVA", MappedClasses.W).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.qC)).setStaticMember(true)).buildField();
        if (!controlFlowState) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }

    private Object readWater() {
        return this.waterField.getObject(null);
    }

    public static Object getWater(MMLGBlockWrapper mapping) {
        return mapping.readWater();
    }
}

