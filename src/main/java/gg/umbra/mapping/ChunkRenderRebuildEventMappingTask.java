package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventChunkRenderRebuild;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class ChunkRenderRebuildEventMappingTask
extends JavassistMappingTask {
    private static final String c = "($r) 1";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().qg.w;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChunkRenderRebuild.class);
        eventInjectionSpec.setReturnExpression(c);
        this.registerEventInjection(eventInjectionSpec);
    }

    public ChunkRenderRebuildEventMappingTask() {
        super(MappedClasses.Zk);
    }
}
