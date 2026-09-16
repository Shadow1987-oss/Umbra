package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventVisGraphComputeVisibility;
import gg.umbra.event.impl.EventVisGraphSetOpaqueCube;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class VisGraphXRayVisibilityMappingTask
extends JavassistMappingTask {
    private static final String VISIBILITY_RETURN_EXPRESSION = "($r) $event.getVisibility()";

    @Override
    public void transform() {
        MappingMethod computeVisibilityMethod = Umbra.INSTANCE.getMappings().visGraph.computeVisibilityMethod;
        EventInjectionSpec computeVisibilityInjection = new EventInjectionSpec(computeVisibilityMethod, EventVisGraphComputeVisibility.class);
        computeVisibilityInjection.setReturnExpression(VISIBILITY_RETURN_EXPRESSION);
        this.registerEventInjection(computeVisibilityInjection);
        MappingMethod setOpaqueCubeMethod = Umbra.INSTANCE.getMappings().visGraph.setOpaqueCubeMethod;
        EventInjectionSpec setOpaqueCubeInjection = new EventInjectionSpec(setOpaqueCubeMethod, EventVisGraphSetOpaqueCube.class);
        setOpaqueCubeInjection.setInsertBefore(true);
        this.registerEventInjection(setOpaqueCubeInjection);
    }

    public VisGraphXRayVisibilityMappingTask() {
        super(MappedClasses.VIS_GRAPH);
    }
}
