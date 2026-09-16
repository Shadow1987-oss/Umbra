package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockLayerOverrideModern;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class BlockLayerOverrideModernEventMappingTask
extends JavassistMappingTask {
    public BlockLayerOverrideModernEventMappingTask() {
        super(MappedClasses.qa);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().qg.S;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideModern.class);
        eventInjectionSpec.setConstructorArguments("$0");
        eventInjectionSpec.setReturnExpression("($r) $event.getBlockLayer()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
