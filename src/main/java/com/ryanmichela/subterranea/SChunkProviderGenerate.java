package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunkProviderGenerate extends ChunkProviderGenerate {
    private final int LAYERS_PER_CHUNK = 256;
    private final int LAYERS_PER_HALF_CHUNK = 128;
    private final int BLOCKS_PER_LAYER = 16 * 16;
    private final int BLOCKS_PER_CHUNK = 16 * 16 * 256;

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

    public byte[][] getChunkSectionsAt(int xx, int zz) {
        Random i = ReflectionUtil.getProtectedValue(this, "i");
        World n = ReflectionUtil.getProtectedValue(this, "n");
        Boolean o = ReflectionUtil.getProtectedValue(this, "o");
        WorldGenBase t = new SWorldGenCaves();//ReflectionUtil.getProtectedValue(this, "t");
        WorldGenStronghold u = ReflectionUtil.getProtectedValue(this, "u");
        WorldGenVillage v = ReflectionUtil.getProtectedValue(this, "v");
        WorldGenMineshaft w = ReflectionUtil.getProtectedValue(this, "w");
        WorldGenLargeFeature x = ReflectionUtil.getProtectedValue(this, "x");
        WorldGenBase y = ReflectionUtil.getProtectedValue(this, "y");
        BiomeBase[] z = ReflectionUtil.getProtectedValue(this, "z");

        i.setSeed(xx * 341873128712L + zz * 132897987541L);

        Block[] initialWorld = new Block[BLOCKS_PER_CHUNK];
        a(xx, zz, initialWorld);

        // Note: data in the world array is stored as stacked columns, from the bottom of the world to the top,
        // iterating over the 16x16 grid of the chunk.

        // Lift the generated terrain data by 128 layers
        Block[] liftedWorld = new Block[BLOCKS_PER_CHUNK];
        for (int ii = 0; ii < BLOCKS_PER_LAYER; ii++) {
            // Bottom half
            Arrays.fill(liftedWorld, LAYERS_PER_CHUNK * ii, LAYERS_PER_CHUNK * ii + LAYERS_PER_HALF_CHUNK, Blocks.STONE);
            // Top half
            System.arraycopy(initialWorld, LAYERS_PER_CHUNK*ii, liftedWorld, LAYERS_PER_CHUNK*ii + LAYERS_PER_HALF_CHUNK, LAYERS_PER_HALF_CHUNK);
        }

        z = n.getWorldChunkManager().getBiomeBlock(z, xx * 16, zz * 16, 16, 16);
        byte[] arrayOfByte1 = new byte[65536];
        a(xx, zz, liftedWorld, arrayOfByte1, z);

        t.a(this, n, xx, zz, liftedWorld);
        y.a(this, n, xx, zz, liftedWorld);
        if (o) {
            w.a(this, n, xx, zz, liftedWorld);
            v.a(this, n, xx, zz, liftedWorld);
            u.a(this, n, xx, zz, liftedWorld);
            x.a(this, n, xx, zz, liftedWorld);
        }

        Chunk localChunk = new Chunk(n, liftedWorld, arrayOfByte1, xx, zz);
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
