package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeBigHills extends BiomeBase {

    private WorldGenerator S;

    protected SBiomeBigHills(int i) {
        super(i);

        this.b(6316128);
        this.a("Extreme Hills");
        this.D = 0.3F;
        this.E = 1.5F;
        this.temperature = 0.2F;
        this.humidity = 0.3F;

        this.S = new WorldGenMinable(Block.MONSTER_EGGS.id, 8);
    }

    public void a(World world, Random random, int i, int j) {
        super.a(world, random, i, j);
        int k = 3 + random.nextInt(6);

        int l;
        int i1;
        int j1;

        for (l = 0; l < k; ++l) {
            i1 = i + random.nextInt(16);
            // Only adjust Emerald layers in Subterranea worlds
            j1 = random.nextInt(world.worldProvider instanceof SWorldProvider ? 84 : 28) + 4;  //28->84
            int k1 = j + random.nextInt(16);
            int l1 = world.getTypeId(i1, j1, k1);

            if (l1 == Block.STONE.id) {
                world.setTypeIdAndData(i1, j1, k1, Block.EMERALD_ORE.id, 0, 2);
            }
        }

        for (k = 0; k < 7; ++k) {
            l = i + random.nextInt(16);
            i1 = random.nextInt(64);
            j1 = j + random.nextInt(16);
            this.S.a(world, random, l, i1, j1);
        }
    }
}

