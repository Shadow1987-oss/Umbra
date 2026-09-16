package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderTypeBuffer;
import gg.umbra.wrapper.Wrapper;

public class RenderItemFontBridge
extends Wrapper {

    public static RenderItemFontBridge V(WorldRenderer wg_12) {
        if (ForgeVersion.MC_1_21_0.d()) {
            return null;
        }
        return new RenderItemFontBridge(MRenderTypeBuffer.t(RenderItemFontBridge.umbraInstance.getMappingsMapperCompat().c, wg_12.getObject()));
    }

    public void X() {
        MRenderTypeBuffer.Q(RenderItemFontBridge.umbraInstance.getMappingsMapperCompat().c, this.I);
    }

    public RenderItemFontBridge(Object object) {
        super(object);
    }

    public void q() {
        MRenderTypeBuffer.n(RenderItemFontBridge.umbraInstance.getMappingsMapperCompat().c, this.I);
    }
}

