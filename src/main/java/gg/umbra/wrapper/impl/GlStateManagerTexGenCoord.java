package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class GlStateManagerTexGenCoord
extends Wrapper {
    public GlStateManagerTexGenCoord(Object coordinateHandle) {
        super(coordinateHandle);
    }

    public static GlStateManagerTexGenCoord none() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.umbraInstance.getMappingsMapperCompat().glTexGenCoord.getNone());
    }

    public static GlStateManagerTexGenCoord server() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.umbraInstance.getMappingsMapperCompat().glTexGenCoord.getServer());
    }

    public static GlStateManagerTexGenCoord client() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.umbraInstance.getMappingsMapperCompat().glTexGenCoord.getClient());
    }
}
