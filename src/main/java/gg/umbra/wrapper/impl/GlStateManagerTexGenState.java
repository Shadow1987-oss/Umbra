package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.wrapper.Wrapper;

public class GlStateManagerTexGenState
extends Wrapper {
    public static final int F;

    public static boolean p() {
        return Umbra.INSTANCE.getMappings().hs != null;
    }

    static {
        long l2 = -7022238366481794461L;
        F = (int)l2;
    }


    public static void I(int n, int n2) {
        if (Umbra.INSTANCE.getMappings().hs != null) {
            Umbra.INSTANCE.getMappings().hs.K(n, n2);
        }
    }

    public GlStateManagerTexGenState(Object object) {
        super(object);
    }

    public static int J(int n) {
        if (Umbra.INSTANCE.getMappings().hs != null) {
            return Umbra.INSTANCE.getMappings().hs.F(n);
        }
        return 0;
    }
}

