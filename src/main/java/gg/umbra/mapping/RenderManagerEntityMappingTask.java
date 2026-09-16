package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPreRenderEntity;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class RenderManagerEntityMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod;
        if (ForgeVersion.MC_1_8_9.L() && !(mappingMethod = Umbra.INSTANCE.getMappings().CA.W).hasResolutionFailed()) {
            this.O(mappingMethod, EventPreRenderEntity.class, "$1", "false");
        }
        if (ForgeVersion.MC_1_16_5.d() && !(mappingMethod = Umbra.INSTANCE.getMappings().CA.W).hasResolutionFailed()) {
            this.c(mappingMethod, EventPreRenderEntity.class, "$1");
        }
    }


    public RenderManagerEntityMappingTask() {
        super(MappedClasses.Dc);
    }
}

