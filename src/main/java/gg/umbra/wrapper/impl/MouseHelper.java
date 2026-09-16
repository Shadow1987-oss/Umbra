package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MMouseHelper;
import gg.umbra.wrapper.Wrapper;

public class MouseHelper
extends Wrapper {
    public void D(boolean bl) {
        MMouseHelper.w(MouseHelper.umbraInstance.getMappings().qI, this.I, bl);
    }

    public int getDeltaX() {
        return MMouseHelper.N(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public int getDeltaY() {
        return MMouseHelper.J(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public MouseHelper(Object object) {
        super(object);
    }

    public int z() {
        return MMouseHelper.x(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }


    public void H(int n) {
        MMouseHelper.r(MouseHelper.umbraInstance.getMappings().qI, this.I, n);
    }

    public void Q() {
        this.D(!this.x());
    }

    public boolean x() {
        return MMouseHelper.H$src$Z$1w8ze45(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public int e() {
        return MMouseHelper.H(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public boolean z$src$Z$14t0goe() {
        return MMouseHelper.E(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public int R() {
        return MMouseHelper.t(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public void a(int n) {
        MMouseHelper.i(MouseHelper.umbraInstance.getMappings().qI, this.I, n);
    }

    public long e$src$J$14hgru1() {
        return MMouseHelper.T(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public int I() {
        return MMouseHelper.z(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }

    public int k(int n, boolean bl) {
        return MMouseHelper.m(MouseHelper.umbraInstance.getMappings().qI, this.I, n, bl);
    }

    public int P() {
        return MMouseHelper.M(MouseHelper.umbraInstance.getMappings().qI, this.I);
    }
}

