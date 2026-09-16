package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.wrapper.Wrapper;

public class RenderBlocks
extends Wrapper {
    public void setRenderAllFaces(boolean renderAllFaces) {
        Umbra.INSTANCE.getMappingsMapperCompat().renderBlocks.setRenderAllFaces(this.I, renderAllFaces);
    }

    public RenderBlocks(Object renderBlocksHandle) {
        super(renderBlocksHandle);
    }
}
