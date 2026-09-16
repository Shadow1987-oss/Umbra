package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventRenderItemInFirstPerson;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class ItemRendererFirstPersonEventMappingTask
extends JavassistMappingTask {
    private static final String c = "$0, $1";

    @Override
    public void transform() {
        this.c(Umbra.INSTANCE.getMappings().qE.S, EventRenderItemInFirstPerson.class, c);
    }

    public ItemRendererFirstPersonEventMappingTask() {
        super(MappedClasses.zN);
    }
}
