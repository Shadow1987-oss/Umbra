package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MTessellator;
import gg.umbra.wrapper.Wrapper;

public class Tessellator
extends Wrapper {
    public Tessellator(Object object) {
        super(object);
    }

    public void u(int n, int n2, int n3, int n4) {
        Tessellator.umbraInstance.getMappingsMapperCompat().RB.w(this.I, n, n2, n3, n4);
    }

    public void W(double d, double d2, double d3, double d4, double d5) {
        MTessellator.E(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I, d, d2, d3, d4, d5);
    }


    public void M(int n) {
        MTessellator.k(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I, n);
    }

    public void draw() {
        MTessellator.Y(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I);
    }

    public boolean w() {
        return Tessellator.umbraInstance.getMappingsMapperCompat().RB.K(this.I);
    }

    public static Tessellator getInstance() {
        return new Tessellator(MTessellator.a(Tessellator.umbraInstance.getMappingsMapperCompat().RB));
    }

    public void X(double d, double d2, double d3) {
        MTessellator.v(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I, d, d2, d3);
    }

    public WorldRenderer getWorldRenderer() {
        return new WorldRenderer(MTessellator.W(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I));
    }

    public void h() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        MTessellator.z(Tessellator.umbraInstance.getMappingsMapperCompat().RB, this.I);
    }
}

