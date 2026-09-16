package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class VoxelShape
extends Wrapper {
    public VoxelShape(Object handle) {
        super(handle);
    }

    public RenderItemFontBridge getBufferSource() {
        return new RenderItemFontBridge(VoxelShape.umbraInstance.getMappingsMapperCompat().Cl.getBufferSource(this.I));
    }
}
