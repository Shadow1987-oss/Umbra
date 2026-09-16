package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPreRenderLivingSpecials;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.wrapper.impl.ForgeVersion;

public class LivingSpecialsRenderMappingTask
extends JavassistMappingTask {
    public LivingSpecialsRenderMappingTask() {
        super(MappedClasses.uP);
    }


    @Override
    public void transform() {
        if (ForgeVersion.c() == ForgeVersion.MC_1_8_9.i()) {
            this.O(Umbra.INSTANCE.getMappings().U.V, EventPreRenderLivingSpecials.class, "$1", "");
            return;
        }
        if (ForgeVersion.c() != ForgeVersion.MC_1_21_11.i()) {
            return;
        }
        this.O(Umbra.INSTANCE.getMappings().U.d, EventPreRenderLivingSpecials.class, "$2", "0");
    }
}

