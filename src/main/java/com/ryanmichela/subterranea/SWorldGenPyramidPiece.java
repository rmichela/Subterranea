package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.StructureBoundingBox;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenPyramidPiece;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenPyramidPiece extends WorldGenPyramidPiece {
    public SWorldGenPyramidPiece(Random random, int i, int i1) {
        super(random, i, i1);
        this.f.b = 192; // Move desert temple to new sea level
    }
}
