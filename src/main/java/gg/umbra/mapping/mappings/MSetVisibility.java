package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.mappings.MVisGraph;
import gg.umbra.ui.click.component.GuiComponent;

public class MSetVisibility
extends Mapping {
    public final MappingMethod setAllVisibleMethod;
    public final MappingMethod constructor;


    public void setAllVisible(Object visibilitySet, boolean visible) {
        this.setAllVisibleMethod.invokeVoid(visibilitySet, visible);
    }

    public MSetVisibility() {
        this(MVisGraph.getVisGraphControlFlowMarker());
    }

    private MSetVisibility(String controlFlowMarker) {
        super(MappedClasses.SET_VISIBILITY);
        Class[] constructorParameterTypes = new Class[]{};
        Class<Void> constructorReturnType = Void.TYPE;
        boolean remapConstructor = false;
        String constructorName = "<init>";
        MSetVisibility mappings = this;
        this.constructor = mappings.Y(constructorName, remapConstructor, constructorReturnType, constructorParameterTypes);
        if (controlFlowMarker != null) {
            Class[] parameterTypes = new Class[]{Boolean.TYPE};
            Class<Void> returnType = Void.TYPE;
            boolean remap = true;
            String methodName = "setAllVisible";
            this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
            return;
        }
        Class[] parameterTypes = new Class[]{Boolean.TYPE};
        Class<Void> returnType = Void.TYPE;
        boolean remap = true;
        String methodName = "setAllVisible";
        this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
        GuiComponent.setLegacyComponentState(new GuiComponent[2]);
    }
}

