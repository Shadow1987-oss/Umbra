package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MRenderItemContext;
import gg.umbra.wrapper.Wrapper;

public class RenderItemContext
extends Wrapper {
    public static RenderItemContext gui() {
        return new RenderItemContext(MRenderItemContext.getGui(RenderItemContext.umbraInstance.getMappingsMapperCompat().A));
    }

    public RenderItemContext(Object wrappedObject) {
        super(wrappedObject);
    }
}
