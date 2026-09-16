package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockLayerOverrideFallback;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class BlockLayerOverrideFallbackEventMappingTask
extends JavassistMappingTask {
    public BlockLayerOverrideFallbackEventMappingTask() {
        super(MappedClasses.lA);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().N.gcMethod;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideFallback.class);
        eventInjectionSpec.setInsertBefore(true);
        this.registerEventInjection(eventInjectionSpec);
    }
}
