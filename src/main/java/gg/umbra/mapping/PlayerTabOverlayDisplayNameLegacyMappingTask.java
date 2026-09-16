package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventPlayerTabOverlayDisplayNameLegacy;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;

public class PlayerTabOverlayDisplayNameLegacyMappingTask
extends JavassistMappingTask {
    public PlayerTabOverlayDisplayNameLegacyMappingTask() {
        super(MappedClasses.lF);
    }

    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Umbra.INSTANCE.getMappings().hP.O, EventPlayerTabOverlayDisplayNameLegacy.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("$event.getDisplayName()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
