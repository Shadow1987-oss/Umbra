package gg.umbra.mapping;

import gg.umbra.Umbra;
import gg.umbra.event.impl.EventChatMessageRender;
import gg.umbra.mapping.EventInjectionSpec;
import gg.umbra.mapping.JavassistMappingTask;
import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.MappingMethod;

public class ChatMessageRenderMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        this.registerChatMessageRenderEvent();
    }

    public ChatMessageRenderMappingTask() {
        super(MappedClasses.d);
    }

    private void registerChatMessageRenderEvent() {
        MappingMethod mappingMethod = Umbra.INSTANCE.getMappings().chatMessageRenderTarget.addMessageMethod;
        if (mappingMethod != null && !mappingMethod.hasResolutionFailed()) {
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChatMessageRender.class);
            eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3");
            eventInjectionSpec.setAfterCode("$1 = (" + MappedClasses.Yr.getName() + ") $event.getOutputContentComponent();");
            this.registerEventInjection(eventInjectionSpec);
        }
    }

}

