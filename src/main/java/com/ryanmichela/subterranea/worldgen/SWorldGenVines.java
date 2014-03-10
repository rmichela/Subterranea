package com.ryanmichela.subterranea.worldgen;

import net.minecraft.server.v1_7_R1.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenVines extends WorldGenVines {
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        int l = i;
        int i1 = k;
        label0:
        for (; j < 256; ++j) { //128=>256
            if (world.isEmpty(i, j, k)) {
                int j1 = 2;
                do {
                    if (j1 > 5)
                        continue label0;
                    if (Blocks.VINE.canPlace(world, i, j, k, j1)) {
                        world.setTypeAndData(i, j, k, Blocks.VINE, 1 << Direction.e[Facing.OPPOSITE_FACING[j1]], 2);
                        continue label0;
                    }
                    j1++;
                } while (true);
            }
            i = (l + random.nextInt(4)) - random.nextInt(4);
            k = (i1 + random.nextInt(4)) - random.nextInt(4);
        }

        return true;
    }
}
