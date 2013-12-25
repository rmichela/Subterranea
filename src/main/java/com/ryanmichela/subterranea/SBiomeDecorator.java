package com.ryanmichela.subterranea;

import net.minecraft.server.v1_7_R1.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeDecorator extends BiomeDecorator {
    private final GeneratorOptions options;

    public SBiomeDecorator(BiomeBase biomeBase, GeneratorOptions options) {
        super();
        this.options = options;

        // Clone values from previous decorator
        this.A = ReflectionUtil.getProtectedValue(biomeBase.ar, "A");
        this.B = ReflectionUtil.getProtectedValue(biomeBase.ar, "B");
        this.C = ReflectionUtil.getProtectedValue(biomeBase.ar, "C");
        this.D = ReflectionUtil.getProtectedValue(biomeBase.ar, "D");
        this.E = ReflectionUtil.getProtectedValue(biomeBase.ar, "E");
        this.F = ReflectionUtil.getProtectedValue(biomeBase.ar, "F");
        this.G = ReflectionUtil.getProtectedValue(biomeBase.ar, "G");
        this.H = ReflectionUtil.getProtectedValue(biomeBase.ar, "H");
        this.I = ReflectionUtil.getProtectedValue(biomeBase.ar, "I");
        this.w = ReflectionUtil.getProtectedValue(biomeBase.ar, "w");
        this.x = ReflectionUtil.getProtectedValue(biomeBase.ar, "x");
        this.y = ReflectionUtil.getProtectedValue(biomeBase.ar, "y");
        this.z = ReflectionUtil.getProtectedValue(biomeBase.ar, "z");
        this.c = ReflectionUtil.getProtectedValue(biomeBase.ar, "c");
        this.d = ReflectionUtil.getProtectedValue(biomeBase.ar, "d");
    }

    @Override
    protected void a() {
        // Since biome implementations are global, only adjust ore layers if
        // the world being decorated is a Subterranea world.
        if (this.a.worldProvider instanceof SWorldProvider)
        {
            if (options.oreMultiplier < 0) options.oreMultiplier = 0;
            for (int i = 0; i < options.oreMultiplier; i++) {
                this.a(20, this.i, 0, 256); //256=128  Dirt
                this.a(10, this.j, 0, 256); //256=128  Gravel
                this.a(20, this.k, 0, 256); //256=128  Coal
                this.a(20, this.l, 0, 192); //192=64   Iron
                this.a(2, this.m, 0, 96);   //96=32    Gold
                this.a(8, this.n, 0, 48);   //48=16    Redstone
                this.a(1, this.o, 0, 48);   //48=16    Diamond
                this.b(1, this.p, 16, 48);  //48=16    Lapis
            }
        }
        else
        {
            super.a();
        }
    }
}
