package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ResourceLocationConstantPair;

public class MModelManager
extends Mapping {
    private static final String GET_ATLAS_OR_THROW = "getAtlasOrThrow";
    private final MappingMethod getAtlasMethod;

    public MModelManager() {
        this(ResourceLocationConstantPair.getControlFlowState());
    }

    private MModelManager(GuiComponent[] guiComponentArray) {
        super(MappedClasses.q4);
        this.getAtlasMethod = this.Y(GET_ATLAS_OR_THROW, true, MappedClasses.L, new Class[]{MappedClasses.zC});
        if (GuiComponent.getLegacyComponentState() == null) {
            ResourceLocationConstantPair.setControlFlowState(new GuiComponent[1]);
        }
    }

    public Object getAtlas(Object modelManagerHandle, Object atlasLocation) {
        return this.getAtlasMethod.invokeObject(modelManagerHandle, atlasLocation);
    }
}
