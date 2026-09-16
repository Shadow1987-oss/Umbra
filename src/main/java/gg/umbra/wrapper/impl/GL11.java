package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MGL11;
import gg.umbra.wrapper.Wrapper;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GL11
extends Wrapper {
    public GL11(Object object) {
        super(object);
    }

    public static void G(int n, FloatBuffer floatBuffer) {
        MGL11.Y(Umbra.INSTANCE.getMappingsMapperCompat().k, n, floatBuffer);
    }

    public static void c(FloatBuffer floatBuffer) {
        MGL11.J(Umbra.INSTANCE.getMappingsMapperCompat().k, floatBuffer);
    }

    public static void X(int n, IntBuffer intBuffer) {
        MGL11.X(Umbra.INSTANCE.getMappingsMapperCompat().k, n, intBuffer);
    }

    public static void f(int n, int n2, FloatBuffer floatBuffer) {
        MGL11.H(Umbra.INSTANCE.getMappingsMapperCompat().k, n, 5126, n2, floatBuffer);
    }
}

