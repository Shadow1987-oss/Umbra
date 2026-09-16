package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockModelRender;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class BlockModelRenderEventMappingTask
extends JavassistMappingTask {
    public BlockModelRenderEventMappingTask() {
        super(MappedClasses.VU);
    }

    @Override
    public void transform() {
        MappingMethod renderModelMethod = Umbra.INSTANCE.getMappings().hE.renderModelMethod;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(renderModelMethod, EventBlockModelRender.class);
        eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3, $4, $5, $6");
        eventInjectionSpec.setReturnExpression("$event.getResult()");
        this.registerEventInjection(eventInjectionSpec);
    }
}
