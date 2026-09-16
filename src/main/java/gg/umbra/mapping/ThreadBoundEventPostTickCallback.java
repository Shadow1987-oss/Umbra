package gg.umbra.mapping;

import gg.umbra.event.impl.EventThreadBoundPostTick;
import gg.umbra.event.impl.EventTickBase;
import gg.umbra.mapping.ThreadBoundTickCallbackBase;

public class ThreadBoundEventPostTickCallback
extends ThreadBoundTickCallbackBase {
    public static void call() {
        if (Thread.currentThread().equals(EventTickBase.PRE_TICK_EXECUTOR.getOwnerThread())) {
            new EventThreadBoundPostTick().fire();
        }
    }

}

