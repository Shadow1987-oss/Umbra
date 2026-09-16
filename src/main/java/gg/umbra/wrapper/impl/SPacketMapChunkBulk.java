package gg.umbra.wrapper.impl;

import java.util.ArrayList;
import java.util.List;

public class SPacketMapChunkBulk
extends Packet {
    public int[] getZPositions() {
        return SPacketMapChunkBulk.umbraInstance.getMappingsMapperCompat().Rg.getZPositions(this.I);
    }

    public List<SPacketChunkDataExtracted> getChunksData() {
        Object[] chunkDataHandles = SPacketMapChunkBulk.umbraInstance.getMappingsMapperCompat().Rg.getChunksData(this.I);
        ArrayList<SPacketChunkDataExtracted> chunksData = new ArrayList<SPacketChunkDataExtracted>();
        for (Object chunkDataHandle : chunkDataHandles) {
            chunksData.add(new SPacketChunkDataExtracted(chunkDataHandle));
        }
        return chunksData;
    }

    public SPacketMapChunkBulk(Object handle) {
        super(handle);
    }

    public int[] getXPositions() {
        return SPacketMapChunkBulk.umbraInstance.getMappingsMapperCompat().Rg.getXPositions(this.I);
    }
}
