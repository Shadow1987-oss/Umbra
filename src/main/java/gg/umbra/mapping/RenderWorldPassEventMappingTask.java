package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPostRenderWorldPass;
import gg.umbra.event.impl.EventPreRenderWorldPass;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class RenderWorldPassEventMappingTask
extends JavassistMappingTask {
    public RenderWorldPassEventMappingTask() {
        super(ForgeVersion.MC_26_2.d() ? MappedClasses.LEVEL_EXTRACTOR : MappedClasses.zs);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_21_4.d()) {
            try {
                MappingMethod mappingMethod = null;
                if (ForgeVersion.MC_1_21_10.d()) {
                    mappingMethod = Umbra.INSTANCE.getMappings().renderGlobal.extractVisibleEntitiesMethod;
                } else if (ForgeVersion.MC_1_21_4.d()) {
                    mappingMethod = Umbra.INSTANCE.getMappings().renderGlobal.renderEntitiesMethod;
                }
                this.c(mappingMethod, EventPreRenderWorldPass.class, "");
                this.k(mappingMethod, EventPostRenderWorldPass.class, "");
            }
            catch (Exception exception) {
                Umbra.logThrowable(exception);
            }
        }
    }
}
