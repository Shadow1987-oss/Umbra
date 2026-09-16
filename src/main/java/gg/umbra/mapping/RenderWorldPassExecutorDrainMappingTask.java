package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventRenderWorldPassExecutorDrain;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class RenderWorldPassExecutorDrainMappingTask
extends JavassistMappingTask {
    private static final String c;

    void V() {
        this.c(Umbra.INSTANCE.getMappings().RY.J, EventRenderWorldPassExecutorDrain.class, c);
    }

    public RenderWorldPassExecutorDrainMappingTask() {
        super(MappedClasses.FW);
    }

    @Override
    public void transform() {
        this.V();
    }

    static {
        try {
            c = "$1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
