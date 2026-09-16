package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MBuiltInRegistries;
import gg.umbra.ui.click.component.GuiComponent;
import gg.umbra.wrapper.impl.Registry;

public class BuiltInRegistries {
    private static GuiComponent[] N;

    public static Registry W() {
        return new Registry(MBuiltInRegistries.M(Umbra.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry j() {
        return new Registry(MBuiltInRegistries.w(Umbra.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry Y() {
        return new Registry(MBuiltInRegistries.v(Umbra.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry I() {
        return new Registry(MBuiltInRegistries.F(Umbra.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static GuiComponent[] f() {
        return N;
    }

    public static void O(GuiComponent[] guiComponentArray) {
        N = guiComponentArray;
    }

    public static Registry a() {
        return new Registry(MBuiltInRegistries.g(Umbra.INSTANCE.getMappingsMapperCompat().q7));
    }

    static {
        if (BuiltInRegistries.f() != null) {
            BuiltInRegistries.O(new GuiComponent[1]);
        }
    }
}

