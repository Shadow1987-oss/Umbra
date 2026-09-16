package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPostRenderHand;
import gg.umbra.event.impl.EventPreRenderHand;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class RenderHandEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        if (Umbra.INSTANCE.getMappings().RY.x != null && !Umbra.INSTANCE.getMappings().RY.x.hasResolutionFailed()) {
            this.c(Umbra.INSTANCE.getMappings().RY.x, EventPreRenderHand.class, "");
            this.k(Umbra.INSTANCE.getMappings().RY.x, EventPostRenderHand.class, "");
        }
    }

    public RenderHandEventMappingTask() {
        super(MappedClasses.lt);
    }

}

