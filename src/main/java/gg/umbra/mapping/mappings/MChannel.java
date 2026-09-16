package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.ForgeVersion;

public class MChannel
extends Mapping {
    private MappingMethod usesBlockLightMethod;
    private static GuiComponent[] bakedModelControlFlowState;
    private static final String USES_BLOCK_LIGHT_METHOD_NAME;


    public static GuiComponent[] getBakedModelControlFlowState() {
        return bakedModelControlFlowState;
    }

    public MChannel() {
        this(MChannel.getBakedModelControlFlowState());
    }

    private MChannel(GuiComponent[] controlFlowState) {
        super(MappedClasses.lc);
        GuiComponent[] currentControlFlowState = controlFlowState;
        if (ForgeVersion.MC_1_17.d()) {
            Class[] parameterTypes = new Class[]{};
            Class<Boolean> returnType = Boolean.TYPE;
            boolean methodPublic = true;
            String methodName = USES_BLOCK_LIGHT_METHOD_NAME;
            MChannel mapping = this;
            this.usesBlockLightMethod = mapping.Y(methodName, methodPublic, returnType, parameterTypes);
        }
    }

    private boolean invokeUsesBlockLight(Object bakedModelHandle) {
        return this.usesBlockLightMethod.invokeBoolean(bakedModelHandle, new Object[0]);
    }

    static {
        MChannel.setBakedModelControlFlowState(null);
        USES_BLOCK_LIGHT_METHOD_NAME = "usesBlockLight";
    }

    public static void setBakedModelControlFlowState(GuiComponent[] controlFlowState) {
        bakedModelControlFlowState = controlFlowState;
    }

    public static boolean usesBlockLight(MChannel mapping, Object bakedModelHandle) {
        return mapping.invokeUsesBlockLight(bakedModelHandle);
    }
}

