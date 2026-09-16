package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MLegacyEntityRenderPreHook;
import gg.umbra.ui.click.component.GuiComponent;

public class MEvent
extends Mapping {
    private static final String SET_CANCELED_METHOD_NAME = "setCanceled";
    private final MappingMethod setCanceledMethod;

    public MEvent() {
        this(MLegacyEntityRenderPreHook.getControlFlowState());
    }

    private MEvent(String[] controlFlowState) {
        super(MappedClasses.S);
        String[] currentControlFlowState = controlFlowState;
        Class[] parameterTypes = new Class[]{Boolean.TYPE};
        Class<Void> returnType = Void.TYPE;
        boolean methodPublic = false;
        String methodName = SET_CANCELED_METHOD_NAME;
        MEvent mapping = this;
        this.setCanceledMethod = mapping.Y(methodName, methodPublic, returnType, parameterTypes);
        if (GuiComponent.getLegacyComponentState() == null) {
            MLegacyEntityRenderPreHook.setControlFlowState(new String[5]);
        }
    }

    public void setCanceled(Object eventHandle, boolean canceled) {
        this.setCanceledMethod.invokeVoid(eventHandle, canceled);
    }

}

