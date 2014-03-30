package com.ryanmichela.subterranea.biome;

import com.ryanmichela.subterranea.ReflectionUtil;
import com.ryanmichela.subterranea.worldgen.SWorldProvider;
import net.minecraft.server.v1_7_R2.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeBigHillsBase extends BiomeBigHills {

    // Manually initialize the biome because we can't use the fluent api called by BiomeBase
    public SBiomeBigHillsBase(int i, boolean b, int i2, String name, float f1, float f2) {
        super(i, b);
        this.b(i2);
        this.a(name);
        this.temperature = f1;
        this.humidity = f2;
    }

    // Manually initialize the biome because we can't use the fluent api called by BiomeBase
    public SBiomeBigHillsBase(int i, boolean b, int i2, String name, float f1, float f2, BiomeTemperature bt) {
        this(i, b, i2, name, f1, f2);
        this.a(bt);
    }

    @Override
    public void a(World world, Random random, int i, int j) {
        super.a(world, random, i, j);
        int l = 3 + random.nextInt(6);
        for (int j1 = 0; j1 < l; j1++) {
            int l1 = i + random.nextInt(16);
            // Only adjust Emerald layers in Subterranea worlds
            int j2 = random.nextInt(world.worldProvider instanceof SWorldProvider ? 84 : 28) + 4; //28->84
            int l2 = j + random.nextInt(16);
            if (world.getType(l1, j2, l2) == Blocks.STONE)
                world.setTypeAndData(l1, j2, l2, Blocks.EMERALD_ORE, 0, 2);
        }

        for (int i1 = 0; i1 < 7; i1++) {
            int k1 = i + random.nextInt(16);
            int i2 = random.nextInt(64);
            int k2 = j + random.nextInt(16);
            ((WorldGenerator) ReflectionUtil.getProtectedValue(this, "aC")).a(world, random, k1, i2, k2);
        }
    }
}

