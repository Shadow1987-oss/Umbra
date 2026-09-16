package gg.umbra.event.impl;

import gg.umbra.event.impl.EventTickBase;

public class EventPostTick
extends EventTickBase {
    @Override
    public boolean fire() {
        POST_TICK_EXECUTOR.runPending();
        return super.fire();
    }
}
