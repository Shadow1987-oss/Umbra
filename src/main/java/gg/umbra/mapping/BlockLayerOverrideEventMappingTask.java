package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockLayerOverrideLegacy;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class BlockLayerOverrideEventMappingTask
extends JavassistMappingTask {
    public BlockLayerOverrideEventMappingTask() {
        super(MappedClasses.ZU);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().qg.S;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideLegacy.class);
        eventInjectionSpec.setConstructorArguments("$0");
        eventInjectionSpec.setReturnExpression("($r) $event.getBlockLayer()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
