package gg.umbra.event.impl;

import gg.umbra.event.impl.EventTickBase;

public class EventPreTick
extends EventTickBase {
    @Override
    public boolean fire() {
        PRE_TICK_EXECUTOR.runPending();
        return super.fire();
    }
}
