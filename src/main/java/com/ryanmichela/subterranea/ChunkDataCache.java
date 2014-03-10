package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.ChunkSection;

/**
 * Created by rmichela on 3/9/14.
 *
 * This class is used to hold the chunk data bytes until after the chunk has been built so the data can be
 * reapplied. Reapplication happens in the ChunkLoaded event which always happens immediately after a chunk
 * has been generated or loaded.
 */
public class ChunkDataCache {
    private ChunkSection[] data;

    public void storeChunkData(ChunkSection[] data) {
        this.data = data;
    }

    public ChunkSection[] claimChunkData() {
        ChunkSection[] temp = data;
        data = null;
        return temp;
    }
}
