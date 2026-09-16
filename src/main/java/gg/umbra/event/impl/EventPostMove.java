package gg.umbra.event.impl;

import gg.umbra.event.impl.EventMove;
import gg.umbra.wrapper.impl.Vec3;

public class EventPostMove
extends EventMove {
    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventPostMove(Object vectorHandle) {
        this(new Vec3(vectorHandle).getX(), new Vec3(vectorHandle).getY(), new Vec3(vectorHandle).getZ());
    }

    public EventPostMove(double x, double y, double z) {
        super(x, y, z);
    }
}
