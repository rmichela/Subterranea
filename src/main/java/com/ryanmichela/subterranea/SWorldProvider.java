package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.WorldProviderNormal;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldProvider extends WorldProviderNormal {
    @Override
    public int getSeaLevel() {
        return 192;
    }
}
