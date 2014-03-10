package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.ChunkSection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rmichela on 3/9/14.
 */
public class ChunkDataCache {
    private Map<String, ChunkSection[]> unclaimedChunkData = new HashMap<String, ChunkSection[]>();

    public void storeChunkData(int x, int z, ChunkSection[] data) {
        unclaimedChunkData.put(x + ":" + z, data);
    }

    public ChunkSection[] claimChunkData(int x, int z) {
        return unclaimedChunkData.remove(x + ":" + z);
    }
}
