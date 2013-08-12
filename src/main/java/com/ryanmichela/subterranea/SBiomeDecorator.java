package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.BiomeBase;
import net.minecraft.server.v1_6_R2.BiomeDecorator;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeDecorator extends BiomeDecorator {
    public SBiomeDecorator(BiomeBase biomeBase) {
        super(biomeBase);
    }

    @Override
    protected void b() {
        // Since biome implementations are global, only adjust ore layers if
        // the world being decorated is a Subterranea world.
        if (this.a.worldProvider instanceof SWorldProvider)
        {
            this.a(20, this.i, 0, 256); //256=128  Dirt
            this.a(10, this.j, 0, 256); //256=128  Gravel
            this.a(20, this.k, 0, 256); //256=128  Coal
            this.a(20, this.l, 0, 192); //192=64   Iron
            this.a(2, this.m, 0, 96);   //96=32    Gold
            this.a(8, this.n, 0, 48);   //48=16    Redstone
            this.a(1, this.o, 0, 48);   //48=16    Diamond
            this.b(1, this.p, 16, 48);  //48=16    Lapis
        }
        else
        {
            super.b();
        }
    }
}
