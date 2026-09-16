package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MModelPlayer;
import gg.umbra.wrapper.Wrapper;

public class ModelPlayer
extends Wrapper {
    public boolean isFlying() {
        return MModelPlayer.v(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public boolean N() {
        return MModelPlayer.u(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public float m$src$F$1kykyr0() {
        return MModelPlayer.p(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public boolean isCreativeMode() {
        return MModelPlayer.k(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public float l() {
        return MModelPlayer.Y(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public boolean H() {
        return MModelPlayer.D(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public void c(boolean bl) {
        MModelPlayer.t(ModelPlayer.umbraInstance.getMappings().Ri, this.I, bl);
    }

    public ModelPlayer(Object object) {
        super(object);
    }

    public boolean c() {
        return MModelPlayer.a(ModelPlayer.umbraInstance.getMappings().Ri, this.I);
    }

    public static ModelPlayer Q() {
        return new ModelPlayer(MModelPlayer.C(ModelPlayer.umbraInstance.getMappings().Ri));
    }

    public void G(float f) {
        MModelPlayer.H(ModelPlayer.umbraInstance.getMappings().Ri, this.I, f);
    }

    public void n(boolean bl) {
        MModelPlayer.y(ModelPlayer.umbraInstance.getMappings().Ri, this.I, bl);
    }

    public void R(float f) {
        MModelPlayer.E(ModelPlayer.umbraInstance.getMappings().Ri, this.I, f);
    }
}

