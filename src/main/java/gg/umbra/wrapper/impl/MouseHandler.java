package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MInputMappings;
import gg.umbra.wrapper.Wrapper;

public class MouseHandler
extends Wrapper {
    public void O() {
        MInputMappings.F$src$V$13evi63(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public double R() {
        return MInputMappings.g(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public MouseHandler(Object object) {
        super(object);
    }

    public void L(long l, int n, boolean bl) {
        MInputMappings.d(MouseHandler.umbraInstance.getMappings().CW, this.I, l, n, bl);
    }

    public void u() {
        MInputMappings.V(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public int d() {
        return MInputMappings.T(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public int z() {
        return MInputMappings.F(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public boolean Z() {
        return MInputMappings.w(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }

    public double b() {
        return MInputMappings.B(MouseHandler.umbraInstance.getMappings().CW, this.I);
    }
}

