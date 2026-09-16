package gg.umbra.mapping;

import gg.umbra.event.impl.EventThreadBoundPreTick;
import gg.umbra.event.impl.EventTickBase;
import gg.umbra.mapping.ThreadBoundTickCallbackBase;

public class ThreadBoundEventPreTickCallback
extends ThreadBoundTickCallbackBase {
    public static void call() {
        if (Thread.currentThread().equals(EventTickBase.PRE_TICK_EXECUTOR.getOwnerThread())) {
            new EventThreadBoundPreTick().fire();
        }
    }

}

