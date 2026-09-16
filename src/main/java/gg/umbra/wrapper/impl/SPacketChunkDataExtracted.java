package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class SPacketChunkDataExtracted
extends Wrapper {
    public SPacketChunkDataExtracted(Object handle) {
        super(handle);
    }

    public int getDataSize() {
        return SPacketChunkDataExtracted.umbraInstance.getMappings().b.getDataSize(this.I);
    }

    public byte[] getData() {
        return SPacketChunkDataExtracted.umbraInstance.getMappings().b.getData(this.I);
    }
}
