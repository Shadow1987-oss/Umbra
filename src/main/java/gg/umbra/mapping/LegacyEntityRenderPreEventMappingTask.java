package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventPreRenderEntityForgeCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class LegacyEntityRenderPreEventMappingTask
extends JavassistMappingTask {
    private static final String c = "$0, $1, $3, $4, $5";

    public LegacyEntityRenderPreEventMappingTask() {
        super(MappedClasses.x);
    }

    @Override
    public void transform() {
        this.k(Umbra.INSTANCE.getMappings().legacyEntityRenderPreHook.constructorMethod, EventPreRenderEntityForgeCallback.class, c);
    }
}
