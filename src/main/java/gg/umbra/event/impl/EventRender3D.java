package gg.umbra.event.impl;

import gg.umbra.event.EventListeners;
import gg.umbra.event.impl.EventRender3DBase;
import gg.umbra.wrapper.impl.MatrixStack;

public class EventRender3D
extends EventRender3DBase {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventRender3D(MatrixStack matrixStack, float f) {
        super(matrixStack, f);
    }
}
