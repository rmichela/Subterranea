package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.Chunk;
import net.minecraft.server.v1_6_R2.ChunkSection;
import net.minecraft.server.v1_6_R2.World;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunk extends Chunk {
    public SChunk(World world, byte[] abyte, int i, int j) {
        super(world, i, j);
        int k = abyte.length / 256;

        for (int l = 0; l < 16; ++l) {
            for (int i1 = 0; i1 < 16; ++i1) {
                for (int j1 = 0; j1 < k; ++j1) {
                    byte b0 = abyte[l << (k > 128 ? 12 : 11) | i1 << (k > 128 ? 8 : 7) | j1];   // This line allows 256depth

                    if (b0 != 0) {
                        int k1 = j1 >> 4;

                        if (this.i()[k1] == null) {
                            this.i()[k1] = new ChunkSection(k1 << 4, !world.worldProvider.g);
                        }

                        this.i()[k1].setTypeId(l, j1 & 15, i1, b0);
                    }
                }
            }
        }
    }
}
