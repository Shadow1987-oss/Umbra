package gg.umbra.utils.render;

import gg.umbra.wrapper.impl.EntityRenderer;
import gg.umbra.wrapper.impl.ForgeVersion;
import gg.umbra.wrapper.impl.Minecraft;
import gg.umbra.wrapper.impl.FogType;

public class Post117RenderPhaseCompat {

    public static void applyRenderPhaseCompat() {
        if (ForgeVersion.MC_1_21_6.v()) {
            return;
        }
        EntityRenderer entityRenderer = Minecraft.m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf();
        entityRenderer.V().P(entityRenderer.getFogRenderer().getBuffer(FogType.noneOrSky()));
    }
}

