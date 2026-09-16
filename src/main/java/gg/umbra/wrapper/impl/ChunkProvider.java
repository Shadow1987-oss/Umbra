package gg.umbra.wrapper.impl;

import gg.umbra.wrapper.Wrapper;

public class ChunkProvider
extends Wrapper {
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return ChunkProvider.umbraInstance.getMappingsMapperCompat().chunkProvider
                .chunkExists(this.I, chunkX, chunkZ);
    }

    public ChunkProvider(Object chunkProviderHandle) {
        super(chunkProviderHandle);
    }
}
