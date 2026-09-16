package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.mappings.MMinecraft;

public class MVertexFormat
extends MMinecraft {
    private static boolean eW;

    public static void x(boolean bl) {
        eW = bl;
    }

    static {
        MVertexFormat.x(false);
    }


    public MVertexFormat() {
        boolean bl = MVertexFormat.C();
        Class clazz = MappedClasses.uQ;
        boolean bl2 = true;
        String string = "fontRenderer";
        MVertexFormat mVertexFormat = this;
        this.y = this.J(string, bl2, clazz);
        Class clazz2 = MappedClasses.z8;
        boolean bl3 = true;
        String string2 = "itemRenderer";
        MVertexFormat mVertexFormat2 = this;
        this.j = this.J(string2, bl3, clazz2);
        boolean bl4 = bl;
    }

    public static boolean k() {
        boolean bl = MVertexFormat.C();
        return true;
    }

    public static boolean C() {
        return eW;
    }
}

