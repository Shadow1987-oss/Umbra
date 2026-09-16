package gg.umbra.mapping;

import gg.umbra.event.impl.EventPreTick;
import gg.umbra.mapping.InsertedTickCallbackBase;

public class EventPreTickCallback
extends InsertedTickCallbackBase {
    public static void call() {
        new EventPreTick().fire();
    }
}

