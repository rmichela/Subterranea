package com.ryanmichela.subterranea.biome;

import com.ryanmichela.subterranea.worldgen.SWorldGenVines;
import com.ryanmichela.subterranea.worldgen.SWorldProvider;
import net.minecraft.server.v1_7_R1.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public abstract class SBiomeJungleBase extends BiomeJungle {
    // Manually initialize the biome because we can't use the fluent api called by BiomeBase
    public SBiomeJungleBase(int i, boolean b, int i2, String name, int i3, float f1, float f2) {
        super(i, b);
        this.b(i2);
        this.a(name);
        this.a(i3);
        this.temperature = f1;
        this.humidity = f2;
    }

    // Manually initialize the biome because we can't use the fluent api called by BiomeBase
    public SBiomeJungleBase(int i, boolean b, int i2, String name, int i3, float f1, float f2, BiomeTemperature bt) {
        this(i, b, i2, name, i3, f1, f2);
        this.a(bt);
    }

    @Override
    public void a(World world, Random random, int i, int j) {
        super.a(world, random, i, j);
        // Only use SWorldGenVines in a Subterranea world
        WorldGenVines worldgenvines = world.worldProvider instanceof SWorldProvider ? new SWorldGenVines() : new WorldGenVines();

        for (int k = 0; k < 50; ++k) {
            int l = i + random.nextInt(16) + 8;
            byte b0 = 64;
            int i1 = j + random.nextInt(16) + 8;

            worldgenvines.a(world, random, l, b0, i1);
        }
    }
}
