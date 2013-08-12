package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.WorldProviderNormal;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldProvider extends WorldProviderNormal {
    @Override
    public int getSeaLevel() {
        return 192;
    }
}
