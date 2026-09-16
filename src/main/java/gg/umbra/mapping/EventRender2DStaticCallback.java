package gg.umbra.mapping;

import gg.umbra.event.impl.EventRender2D;
import gg.umbra.mapping.InsertedCallbackMarker;

public class EventRender2DStaticCallback
extends InsertedCallbackMarker {
    public static void call() {
        EventRender2D.create();
    }
}

