package com.ryanmichela.subterranea.worldgen;

import net.minecraft.server.v1_7_R3.WorldProviderNormal;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldProvider extends WorldProviderNormal {
    @Override
    public int getSeaLevel() {
        return 192;
    }
}
