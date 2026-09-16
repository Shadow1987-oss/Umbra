package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.mapping.EventRender3DCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class Render3DEventMappingTask
extends JavassistMappingTask {
    private static final String c = "$1";

    public Render3DEventMappingTask() {
        super(MappedClasses.zs);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().renderGlobal.renderLevelMethod;
        this.k(mappingMethod, EventRender3DCallback.class,
                ForgeVersion.MC_26_2.d() ? "$5" : c);
    }
}
