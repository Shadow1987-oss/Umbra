package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventFogDensity;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import javassist.CtBehavior;

public class FogDensityEventMappingTask
extends JavassistMappingTask {
    private static final String c = "0.1F";

    @Override
    public void transform() {
        CtBehavior ctBehavior = this.F(Umbra.INSTANCE.getMappings().fogRenderer.setupFogMethod);
        this.H(ctBehavior, EventFogDensity.class, c, "", "");
    }

    public FogDensityEventMappingTask() {
        super(MappedClasses.FOG_RENDERER);
    }
}
