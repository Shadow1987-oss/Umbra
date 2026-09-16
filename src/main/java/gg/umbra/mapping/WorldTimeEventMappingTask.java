package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventWorldTime;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class WorldTimeEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().worldInfo.getWorldTimeMethod, EventWorldTime.class);
        eventInjectionSpec.setConstructorArguments("$0");
        eventInjectionSpec.setReturnExpression("$event.getWorldTime()");
        this.registerEventInjection(eventInjectionSpec);
    }

    public WorldTimeEventMappingTask() {
        super(MappedClasses.WORLD_INFO);
    }
}
