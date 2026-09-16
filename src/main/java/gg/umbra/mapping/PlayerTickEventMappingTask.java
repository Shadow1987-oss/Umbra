package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPostPlayerTick;
import gg.umbra.event.impl.EventPrePlayerTick;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;
import gg.umbra.mapping.PlayerNameFormatCallback;
import gg.umbra.wrapper.impl.ForgeVersion;

public class PlayerTickEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().Rr.V;
        this.c(mappingMethod, EventPrePlayerTick.class, "$0");
        this.k(mappingMethod, EventPostPlayerTick.class, "$0");
        if (ForgeVersion.MC_1_21_10.v()) {
            MappingMethod mappingMethod2 = Umbra.INSTANCE.getMappings().Rr.jO;
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod2, PlayerNameFormatCallback.class);
            eventInjectionSpec.setConstructorArguments("$0");
            eventInjectionSpec.setReturnExpression("($r) $event.getRawDisplayName()");
            this.registerEventInjection(eventInjectionSpec);
        }
    }

    public PlayerTickEventMappingTask() {
        super(MappedClasses.Yl);
    }
}
