package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenLargeFeatureStart extends StructureStart {

    public SWorldGenLargeFeatureStart() {
        super();
    }

    public SWorldGenLargeFeatureStart(World world, Random random, int i, int j) {
        BiomeBase biomebase = world.getBiome(i * 16 + 8, j * 16 + 8);

        if (biomebase != BiomeBase.JUNGLE && biomebase != BiomeBase.JUNGLE_HILLS) {
            if (biomebase == BiomeBase.SWAMPLAND) {
                WorldGenWitchHut worldgenwitchhut = new WorldGenWitchHut(random, i * 16, j * 16);

                this.a.add(worldgenwitchhut);
            } else {
                WorldGenPyramidPiece worldgenpyramidpiece = new SWorldGenPyramidPiece(random, i * 16, j * 16);

                this.a.add(worldgenpyramidpiece);
            }
        } else {
            WorldGenJungleTemple worldgenjungletemple = new WorldGenJungleTemple(random, i * 16, j * 16);

            this.a.add(worldgenjungletemple);
        }

        this.c();
    }
}
