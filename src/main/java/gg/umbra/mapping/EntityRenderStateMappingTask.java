package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventEntityRenderState;
import gg.umbra.mapping.EventPreRenderEntityCallback;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class EntityRenderStateMappingTask
extends JavassistMappingTask {

    @Override
    public void transform() {
        if (!ForgeVersion.MC_1_7_10.L()) {
            MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().qe.p;
            if (ForgeVersion.MC_1_21_10.d()) {
                MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().qe.z;
                this.k(mappingMethod2, EventEntityRenderState.class, "$1, $2");
                return;
            }
            if (ForgeVersion.MC_1_21_0.d()) {
                return;
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                this.c(mappingMethod, EventPreRenderEntityCallback.class, "$1, $4");
                return;
            }
            if (!mappingMethod.hasResolutionFailed()) {
                this.c(mappingMethod, EventPreRenderEntityCallback.class, "$1, $2, $3, $4");
            }
        }
    }

    public EntityRenderStateMappingTask() {
        super(MappedClasses.VQ);
    }
}

