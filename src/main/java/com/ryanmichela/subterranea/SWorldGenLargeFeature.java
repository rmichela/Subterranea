package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.StructureStart;
import net.minecraft.server.v1_7_R1.WorldGenLargeFeature;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenLargeFeature extends WorldGenLargeFeature {
    @Override
    protected StructureStart b(int i, int j) {
        return new SWorldGenLargeFeatureStart(this.c, this.b, i, j);
    }
}
