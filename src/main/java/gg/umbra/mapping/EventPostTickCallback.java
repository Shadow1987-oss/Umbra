package gg.umbra.mapping;

import gg.umbra.event.impl.EventPostTick;
import gg.umbra.mapping.InsertedTickCallbackBase;

public class EventPostTickCallback
extends InsertedTickCallbackBase {
    public static void call() {
        new EventPostTick().fire();
    }
}

