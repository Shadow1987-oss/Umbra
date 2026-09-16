package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockRenderColorOpacity;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class BlockRenderColorOpacityMappingTask
extends JavassistMappingTask {
    private static final String c = "$0, $1, $2, $3, $4";

    public BlockRenderColorOpacityMappingTask() {
        super(MappedClasses.lX);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().qZ.b;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockRenderColorOpacity.class);
        eventInjectionSpec.setInsertBefore(false);
        eventInjectionSpec.setConstructorArguments(c);
        this.registerEventInjection(eventInjectionSpec);
    }
}
