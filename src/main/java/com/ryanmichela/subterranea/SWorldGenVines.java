package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenVines extends WorldGenVines {
    @Override
    public boolean a(World world, Random random, int i, int j, int k) {
        int l = i;

        for (int i1 = k; j < 256; ++j) { //128=>256
            if (world.isEmpty(i, j, k)) {
                for (int j1 = 2; j1 <= 5; ++j1) {
                    if (Block.VINE.canPlace(world, i, j, k, j1)) {
                        world.setTypeIdAndData(i, j, k, Block.VINE.id, 1 << Direction.e[Facing.OPPOSITE_FACING[j1]], 2);
                        break;
                    }
                }
            } else {
                i = l + random.nextInt(4) - random.nextInt(4);
                k = i1 + random.nextInt(4) - random.nextInt(4);
            }
        }

        return true;
    }
}
