package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class BlockStateContainerBridge
extends Wrapper {
    public GpuTextureView getTextureView() {
        return new GpuTextureView(BlockStateContainerBridge.umbraInstance.getMappingsMapperCompat().Ds.getTextureView(this.I));
    }

    public Object getOrUpdate(TextComponentTranslation renderState) {
        return BlockStateContainerBridge.umbraInstance.getMappingsMapperCompat().Ds.getOrUpdate(this.I, renderState.getObject());
    }

    public BlockStateContainerBridge(Object handle) {
        super(handle);
    }

    public int getTextureId() {
        GpuTextureView textureView = this.getTextureView();
        return textureView.isNull() ? -1 : textureView.getTextureId();
    }

}

