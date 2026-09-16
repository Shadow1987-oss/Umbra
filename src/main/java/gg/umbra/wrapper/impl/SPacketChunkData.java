package gg.umbra.wrapper.impl;

public class SPacketChunkData
extends Packet {
    public SPacketChunkData(Object handle) {
        super(handle);
    }

    public int getChunkZ() {
        return SPacketChunkData.umbraInstance.getMappingsMapperCompat().h8.getChunkZ(this.I);
    }

    public SPacketChunkDataExtracted getExtractedData() {
        return new SPacketChunkDataExtracted(SPacketChunkData.umbraInstance.getMappingsMapperCompat().h8.getExtractedData(this.I));
    }

    public int getChunkX() {
        return SPacketChunkData.umbraInstance.getMappingsMapperCompat().h8.getChunkX(this.I);
    }
}
