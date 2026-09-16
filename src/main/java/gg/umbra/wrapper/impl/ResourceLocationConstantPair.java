package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ResourceLocation;

public class ResourceLocationConstantPair {
    private static GuiComponent[] controlFlowState;

    public static void setControlFlowState(GuiComponent[] state) {
        controlFlowState = state;
    }

    public static ResourceLocation getItems() {
        return new ResourceLocation(Umbra.INSTANCE.getMappingsMapperCompat().resourceLocationConstants.getItems());
    }

    public static GuiComponent[] getControlFlowState() {
        return controlFlowState;
    }

    public static ResourceLocation getGui() {
        return new ResourceLocation(Umbra.INSTANCE.getMappingsMapperCompat().resourceLocationConstants.getGui());
    }

    static {
        if (ResourceLocationConstantPair.getControlFlowState() != null) {
            ResourceLocationConstantPair.setControlFlowState(new GuiComponent[3]);
        }
    }
}
