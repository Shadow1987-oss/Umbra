package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventBlockFluidRender;
import gg.umbra.event.impl.EventBlockRenderBounds;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceXNeg;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceXPos;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceYNeg;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceYPos;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceZNeg;
import gg.umbra.event.impl.EventLegacyXRayRenderFaceZPos;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class RenderBlocksEventMappingTask
extends JavassistMappingTask {
    public RenderBlocksEventMappingTask() {
        super(MappedClasses.q5);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderBlockByRenderTypeMethod;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockRenderBounds.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("false");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderStandardBlockMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockFluidRender.class);
        eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3, $4");
        eventInjectionSpec.setReturnExpression("$event.isResult()");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceXNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceYNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceXPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceYPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceYNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceZNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceYPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceZPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceZNegMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceXNeg.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
        mappingMethod = Umbra.INSTANCE.getMappings().renderBlocks.renderFaceZPosMethod;
        eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventLegacyXRayRenderFaceXPos.class);
        eventInjectionSpec.setConstructorArguments("$1");
        this.registerEventInjection(eventInjectionSpec);
    }
}
