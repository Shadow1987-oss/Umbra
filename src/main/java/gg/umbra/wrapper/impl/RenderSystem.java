package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderSystem;
import gg.umbra.wrapper.Wrapper;

public class RenderSystem
extends Wrapper {
    public static void o(float f) {
        MRenderSystem.Z(RenderSystem.umbraInstance.getMappings().f, f);
    }

    public static void U(float f, float f2, float f3, float f4) {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        MRenderSystem.S(RenderSystem.umbraInstance.getMappings().f, f, f2, f3, f4);
    }

    public static void p$src$V$18am5c() {
        MRenderSystem.f(RenderSystem.umbraInstance.getMappings().f);
    }

    public static MatrixStack p() {
        return new MatrixStack(RenderSystem.umbraInstance.getMappings().f.P());
    }

    public static void v() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return;
        }
        MRenderSystem.U(RenderSystem.umbraInstance.getMappings().f);
    }


    public static void L(Matrix4f matrix4f, Items items) {
    }

    public static void b(float f) {
        if (ForgeVersion.MC_1_21_11.d()) {
            return;
        }
        MRenderSystem.F(RenderSystem.umbraInstance.getMappings().f, f);
    }

    public static void s(int n, int n2) {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        MRenderSystem.o(RenderSystem.umbraInstance.getMappings().f, n, n2);
    }

    public static void k(int n, int n2) {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        MRenderSystem.n(RenderSystem.umbraInstance.getMappings().f, n, n2);
    }

    public static void R() {
        MRenderSystem.c(RenderSystem.umbraInstance.getMappings().f);
    }

    public static void u(int n, ResourceLocation resourceLocation) {
        MRenderSystem.o(RenderSystem.umbraInstance.getMappings().f, n, resourceLocation.getObject());
    }

    public static void f() {
        MRenderSystem.h(RenderSystem.umbraInstance.getMappings().f);
    }

    public static void n() {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        MRenderSystem.D(RenderSystem.umbraInstance.getMappings().f);
    }

    public RenderSystem(Object object) {
        super(object);
    }

    public static void x() {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        MRenderSystem.j(RenderSystem.umbraInstance.getMappings().f);
    }

    public static int[] I() {
        return MRenderSystem.x(RenderSystem.umbraInstance.getMappings().f);
    }
}

