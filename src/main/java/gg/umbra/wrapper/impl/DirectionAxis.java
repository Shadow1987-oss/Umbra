package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MDirectionAxis;
import gg.umbra.wrapper.Wrapper;

public class DirectionAxis
extends Wrapper {
    public static DirectionAxis x() {
        return new DirectionAxis(MDirectionAxis.getX(DirectionAxis.umbraInstance.getMappingsMapperCompat().directionAxis));
    }

    public static DirectionAxis y() {
        return new DirectionAxis(MDirectionAxis.getY(DirectionAxis.umbraInstance.getMappingsMapperCompat().directionAxis));
    }

    public DirectionAxis(Object handle) {
        super(handle);
    }

    public double choose(double x, double y, double z) {
        return DirectionAxis.umbraInstance.getMappingsMapperCompat().directionAxis.choose(this.I, x, y, z);
    }
}
