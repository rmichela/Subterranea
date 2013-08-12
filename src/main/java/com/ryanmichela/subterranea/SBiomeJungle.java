package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeJungle extends BiomeJungle {
    public SBiomeJungle(int i) {
        super(i);
        this.b(5470985);
        this.a("Jungle");
        this.a(5470985);
        this.temperature = 1.2F;
        this.humidity = 0.9F;
        this.D = 0.2F;
        this.E = 0.4F;
    }

    @Override
    public void a(World world, Random random, int i, int j) {
        super.a(world, random, i, j);
        WorldGenVines worldgenvines = world.worldProvider instanceof SWorldProvider ? new SWorldGenVines() : new WorldGenVines();

        for (int k = 0; k < 50; ++k) {
            int l = i + random.nextInt(16) + 8;
            byte b0 = 64;
            int i1 = j + random.nextInt(16) + 8;

            worldgenvines.a(world, random, l, b0, i1);
        }
    }
}
