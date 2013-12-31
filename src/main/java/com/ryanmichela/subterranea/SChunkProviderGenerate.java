package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunkProviderGenerate extends ChunkProviderGenerate {
    private final int BLOCKS_IN_HALF_CHUNK = 16 * 16 * 128;

    public SChunkProviderGenerate(World world, long l, boolean b) {
        super(world, l, b);
    }

    @Override
    public Chunk getChunkAt(int i, int j) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getOrCreateChunk(int i, int j) {
        throw new UnsupportedOperationException();
    }

    public byte[][] getChunkSectionsAt(int ii, int jj) {
        Random i = ReflectionUtil.getProtectedValue(this, "i");
        World n = ReflectionUtil.getProtectedValue(this, "n");
        Boolean o = ReflectionUtil.getProtectedValue(this, "o");
        WorldGenBase t = ReflectionUtil.getProtectedValue(this, "t");
        WorldGenStronghold u = ReflectionUtil.getProtectedValue(this, "u");
        WorldGenVillage v = ReflectionUtil.getProtectedValue(this, "v");
        WorldGenMineshaft w = ReflectionUtil.getProtectedValue(this, "w");
        WorldGenLargeFeature x = ReflectionUtil.getProtectedValue(this, "x");
        WorldGenBase y = ReflectionUtil.getProtectedValue(this, "y");
        BiomeBase[] z = ReflectionUtil.getProtectedValue(this, "z");

        i.setSeed(ii * 341873128712L + jj * 132897987541L);

        Block[] arrayOfBlock = new Block[65536];
        byte[] arrayOfByte1 = new byte[65536];

        a(ii, jj, arrayOfBlock);

        // Lift the generated terrain data by 128 layers
        System.arraycopy(arrayOfBlock, 0, arrayOfBlock, BLOCKS_IN_HALF_CHUNK, BLOCKS_IN_HALF_CHUNK);
        System.arraycopy(arrayOfByte1, 0, arrayOfByte1, BLOCKS_IN_HALF_CHUNK, BLOCKS_IN_HALF_CHUNK);
        // Fill the lower half with stone
        Arrays.fill(arrayOfBlock, 0, BLOCKS_IN_HALF_CHUNK, Blocks.STONE);
        Arrays.fill(arrayOfByte1, 0, BLOCKS_IN_HALF_CHUNK, (byte)0);

        z = n.getWorldChunkManager().getBiomeBlock(z, ii * 16, jj * 16, 16, 16);
        a(ii, jj, arrayOfBlock, arrayOfByte1, z);

        t.a(this, n, ii, jj, arrayOfBlock);
        y.a(this, n, ii, jj, arrayOfBlock);
        if (o) {
            w.a(this, n, ii, jj, arrayOfBlock);
            v.a(this, n, ii, jj, arrayOfBlock);
            u.a(this, n, ii, jj, arrayOfBlock);
            x.a(this, n, ii, jj, arrayOfBlock);
        }

        Chunk localChunk = new Chunk(n, arrayOfBlock, arrayOfByte1, ii, jj);
        ChunkSection[] chunkSections = localChunk.i();

        byte[][] chunkSectionBytes = new byte[chunkSections.length][];
        for(int k = 0; k < chunkSectionBytes.length; k++) {
            if (chunkSections[k] != null) {
                chunkSectionBytes[k] = chunkSections[k].getIdArray();
            } else {
                chunkSectionBytes[k] = null;
            }
        }
        return chunkSectionBytes;
    }
}
