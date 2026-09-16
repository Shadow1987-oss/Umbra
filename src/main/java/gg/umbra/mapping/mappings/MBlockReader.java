package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MITooltipFlagBridge;
import gg.umbra.ui.click.component.GuiComponent;

public class MBlockReader
extends Mapping {
    private final MappingMethod clipContextConstructor;

    public MBlockReader() {
        this(MITooltipFlagBridge.getFluidModeControlFlowState());
    }

    private MBlockReader(GuiComponent[] controlFlowState) {
        super(MappedClasses.ZS);
        if (controlFlowState != null) {
            this.clipContextConstructor = this.registerConstructor(new Class[]{MappedClasses.qP, MappedClasses.qP, MappedClasses.lN, MappedClasses.Y9, MappedClasses.zc});
            return;
        }
        this.clipContextConstructor = this.registerConstructor(new Class[]{MappedClasses.qP, MappedClasses.qP, MappedClasses.lN, MappedClasses.Y9, MappedClasses.zc});
        GuiComponent.setLegacyComponentState(new GuiComponent[1]);
    }

    public Object createClipContext(Object from, Object to, Object blockMode, Object fluidMode, Object entity) {
        return this.clipContextConstructor.newInstance(from, to, blockMode, fluidMode, entity);
    }
}
