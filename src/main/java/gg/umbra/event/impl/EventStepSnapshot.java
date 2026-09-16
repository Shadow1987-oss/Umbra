package gg.umbra.event.impl;

import gg.umbra.event.impl.EventStep;

public class EventStepSnapshot
extends EventStep {
    public EventStepSnapshot(Object entityHandle) {
        super(entityHandle);
        EventStep.storeOriginalStepHeight(this.getEntity().u());
    }
}
