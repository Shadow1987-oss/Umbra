package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPreRenderPlayerSpec;
import gg.umbra.event.impl.EventRenderPlayerPost;
import gg.umbra.event.impl.EventRenderPlayerPre;
import gg.umbra.event.impl.EventSetArmorModel;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class RenderPlayerEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().renderPlayer.renderMethod;
        if (!ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.c(mappingMethod, EventRenderPlayerPre.class, "$0, $1, $3, $4");
                this.k(mappingMethod, EventRenderPlayerPost.class, "$0, $1, $3, $4");
            } else {
                this.c(mappingMethod, EventRenderPlayerPre.class, "$0, $1, $2, $3, $4, $6");
                this.k(mappingMethod, EventRenderPlayerPost.class, "$0, $1, $6");
                if (ForgeVersion.MC_1_7_10.L()) {
                    MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().renderPlayer.shouldRenderPassMethod;
                    EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod2, EventSetArmorModel.class);
                    eventInjectionSpec.setConstructorArguments("$1, $2, $3");
                    eventInjectionSpec.setReturnExpression("($r) $event.getResult()");
                    this.registerEventInjection(eventInjectionSpec);
                    MappingMethod mappingMethod3 = Umbra.INSTANCE.getMappings().renderPlayer.renderEquippedItemsMethod;
                    this.c(mappingMethod3, EventPreRenderPlayerSpec.class, "$1, $2");
                }
            }
        }
    }

    public RenderPlayerEventMappingTask() {
        super(MappedClasses.D0);
    }

}

