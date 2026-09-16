package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class RenderStateBuilder
extends Wrapper {
    public RenderStateBuilder(Object builderHandle) {
        super(builderHandle);
    }

    public static void drawWithShader(RenderState renderState) {
        RenderStateBuilder.umbraInstance.getMappingsMapperCompat().renderStateBuilder
                .drawWithShader(renderState.getObject());
    }
}
