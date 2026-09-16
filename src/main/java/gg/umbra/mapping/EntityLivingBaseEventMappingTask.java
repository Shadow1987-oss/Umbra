package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventEntityRendererRayTrace;
import gg.umbra.event.impl.EventPostEntityUpdate;
import gg.umbra.event.impl.EventPostLivingTravel;
import gg.umbra.event.impl.EventPotionEffectCheck;
import gg.umbra.event.impl.EventPreEntityUpdate;
import gg.umbra.event.impl.EventPreLivingTravel;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.wrapper.impl.ForgeVersion;

public class EntityLivingBaseEventMappingTask
extends JavassistMappingTask {
    public EntityLivingBaseEventMappingTask() {
        super(MappedClasses.zm);
    }


    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Rr.V;
        this.c(mappingMethod, EventPreEntityUpdate.class, "$0");
        this.k(mappingMethod, EventPostEntityUpdate.class, "$0");
        MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().hx.U;
        this.c(mappingMethod2, EventPreLivingTravel.class, "$0");
        this.k(mappingMethod2, EventPostLivingTravel.class, "$0");
        if (ForgeVersion.MC_1_8_9.L()) {
            EventInjectionSpec eventInjectionSpec;
            if (!Umbra.INSTANCE.isLabyModPresent()) {
                eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().Rr.jL, EventEntityRendererRayTrace.class);
                eventInjectionSpec.setConstructorArguments("$0, $1");
                eventInjectionSpec.setReturnExpression("($r) $event.getVec();");
                this.registerEventInjection(eventInjectionSpec);
            }
            eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().hx.s, EventPotionEffectCheck.class);
            eventInjectionSpec.setConstructorArguments("$0, $1");
            eventInjectionSpec.setReturnExpression("($r) $event.isActive();");
            this.registerEventInjection(eventInjectionSpec);
        }
    }
}

