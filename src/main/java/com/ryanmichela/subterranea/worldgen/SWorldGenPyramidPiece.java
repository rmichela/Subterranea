package com.ryanmichela.subterranea.worldgen;

import net.minecraft.server.v1_7_R1.*;

import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenPyramidPiece extends WorldGenPyramidPiece {
    public SWorldGenPyramidPiece() {}

    public SWorldGenPyramidPiece(Random random, int i, int i1) {
        super(random, i, i1);
    }

    @Override
    public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
        this.f.b = world.worldProvider instanceof SWorldProvider ? 192 : 64; //only adjust this value in a subterranea world
        return super.a(world, random, structureboundingbox);
    }
}
