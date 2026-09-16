package gg.umbra.wrapper.impl;

public class Direction
extends EnumFacing {
    public DirectionVector Q$src$Lgg_umbra_wrapper_impl_DirectionVector_$l2h44r() {
        return new DirectionVector(Direction.umbraInstance.getMappings().Q.g(this.I));
    }

    public Direction(Object object) {
        super(object);
    }

    public DirectionAxis n() {
        return new DirectionAxis(Direction.umbraInstance.getMappings().Q.J(this.I));
    }

    public int F() {
        return Direction.umbraInstance.getMappings().Q.z(this.I);
    }

    public static Direction i(double d, double d2, double d3) {
        return new Direction(Direction.umbraInstance.getMappings().Q.w(d, d2, d3));
    }

    public int Q() {
        return Direction.umbraInstance.getMappings().Q.o(this.I);
    }

    public int S() {
        return Direction.umbraInstance.getMappings().Q.S(this.I);
    }
}

