package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MEnumHandBridge;
import gg.umbra.ui.click.component.GuiComponent;

public class MRenderStateBuilder
extends Mapping {
    private static final String DRAW_WITH_SHADER = "drawWithShader";
    private final MappingMethod drawWithShaderMethod;

    public MRenderStateBuilder() {
        this(MEnumHandBridge.s());
    }

    private MRenderStateBuilder(int controlFlowState) {
        super(MappedClasses.ug);
        this.drawWithShaderMethod = this.registerStaticMethod(
                DRAW_WITH_SHADER, true, Void.TYPE, MappedClasses.qE);
        if (controlFlowState != 0) {
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
        }
    }

    public void drawWithShader(Object renderStateHandle) {
        this.drawWithShaderMethod.invokeVoid(null, renderStateHandle);
    }
}

