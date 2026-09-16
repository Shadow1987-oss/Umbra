package gg.umbra.ui.theme;

import gg.umbra.Umbra;
import gg.umbra.settings.ClientSettings;
import gg.umbra.unmap.ColorUtil;
import java.awt.Color;

public class ThemeColors {
    public final Color s;
    public final Color Y;
    public final Color r;
    public final Color i;
    public final Color f;
    public final Color M;
    public final Color U;
    public final Color A;
    public static final ThemeColors J = new ThemeColors();
    public final Color I;
    public final Color Q;
    public final Color V;
    public final Color u;
    public final Color N;
    public final Color Z;
    public final Color q;
    public final Color c;
    public final Color h;
    public final Color W;
    public final Color C;
    private static int j;
    public final Color l;
    private Color G;
    public final Color K;
    public final Color m;
    public final Color H;
    public final Color S;
    public final Color g;
    public final Color o;
    public final Color d;
    public final Color X;
    public final Color a;
    public final Color R;
    public final Color O;
    public final Color k;
    public final Color T;
    public final Color E;
    public final Color y;
    public final Color B = new Color(139, 92, 246);
    public final Color t;
    public final Color F;
    private Color x;
    public final Color z;

    public ThemeColors() {
        this.O = new Color(109, 40, 217);
        this.d = new Color(250, 50, 56);
        this.c = new Color(255, 89, 94);
        this.T = new Color(47, 122, 229);
        this.X = new Color(80, 141, 229);
        this.I = new Color(236, 129, 44);
        this.N = new Color(236, 129, 44, 51);
        this.Y = new Color(255, 160, 84);
        this.V = new Color(168, 171, 178);
        this.W = new Color(118, 121, 128);
        this.f = new Color(203, 206, 213);
        this.K = new Color(46, 41, 66);
        this.U = new Color(139, 92, 246);
        this.r = new Color(15, 13, 22);
        this.i = new Color(20, 17, 30);
        this.H = new Color(30, 27, 45);
        this.S = new Color(30, 27, 45);
        this.m = new Color(24, 21, 36);
        this.l = new Color(46, 41, 66, 128);
        this.F = new Color(38, 34, 55);
        this.R = new Color(28, 25, 42);
        this.a = new Color(33, 30, 48);
        this.o = new Color(42, 38, 60);
        this.y = new Color(46, 41, 66);
        this.k = new Color(255, 255, 255, 10);
        this.A = new Color(203, 206, 213);
        this.Z = new Color(158, 161, 168);
        this.h = new Color(86, 88, 94);
        this.s = new Color(255, 255, 255, 22);
        this.Q = new Color(38, 34, 55);
        this.C = new Color(112, 114, 120);
        this.g = new Color(36, 32, 52, 255);
        this.t = new Color(0, 0, 0, 0);
        this.E = new Color(255, 255, 255, 5);
        this.z = new Color(255, 255, 255, 10);
        this.M = new Color(255, 255, 255, 15);
        this.q = new Color(236, 170, 118);
        this.u = new Color(0, 0, 0, 152);
        this.x = Color.WHITE;
        this.G = Color.BLACK;
    }


    public static int U() {
        int n = ThemeColors.W();
        return 15;
    }

    public Color z() {
        return ClientSettings.INSTANCE.getAccentColor();
    }

    static {
        ThemeColors.l(0);
    }

    public Color B() {
        if (Umbra.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()) {
            return new Color(45, 45, 45);
        }
        if (this.z().equals(this.x)) {
            return this.G;
        }
        this.x = this.z();
        this.G = ColorUtil.getContrastingGray(this.x, 45, 240);
        return this.G;
    }

    public static int W() {
        return j;
    }

    public static void l(int n) {
        j = n;
    }
}

