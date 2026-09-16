package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPlayerTabOverlayDisplayName;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class PlayerTabOverlayDisplayNameMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().hP.O, EventPlayerTabOverlayDisplayName.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("$event.getDisplayNameInstance()");
        this.registerEventInjection(eventInjectionSpec);
    }

    public PlayerTabOverlayDisplayNameMappingTask() {
        super(MappedClasses.lF);
    }
}
