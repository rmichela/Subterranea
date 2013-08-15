package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SBiomeDecorator extends BiomeDecorator {
    public SBiomeDecorator(BiomeBase biomeBase) {
        super(biomeBase);
    }

    @Override
    protected void a() {
        this.b();

        int i;
        int j;
        int k;

        for (i = 0; i < this.H; ++i) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, j, this.a.h(j, k), k);
        }

        for (i = 0; i < this.I; ++i) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.d + this.b.nextInt(16) + 8;
            this.f.a(this.a, this.b, j, this.a.h(j, k), k);
        }

        for (i = 0; i < this.G; ++i) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.d + this.b.nextInt(16) + 8;
            this.g.a(this.a, this.b, j, this.a.h(j, k), k);
        }

        i = this.z;
        if (this.b.nextInt(10) == 0) {
            ++i;
        }

        int l;

        for (j = 0; j < i; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            WorldGenerator worldgenerator = this.e.a(this.b);

            worldgenerator.a(1.0D, 1.0D, 1.0D);
            worldgenerator.a(this.a, this.b, k, this.a.getHighestBlockYAt(k, l), l);
        }

        for (j = 0; j < this.J; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            this.u.a(this.a, this.b, k, this.a.getHighestBlockYAt(k, l), l);
        }

        int i1;

        for (j = 0; j < this.A; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.b.nextInt(256);  //256=128
            i1 = this.d + this.b.nextInt(16) + 8;
            this.q.a(this.a, this.b, k, l, i1);
            if (this.b.nextInt(4) == 0) {
                k = this.c + this.b.nextInt(16) + 8;
                l = this.b.nextInt(256);  //256=128
                i1 = this.d + this.b.nextInt(16) + 8;
                this.r.a(this.a, this.b, k, l, i1);
            }
        }

        for (j = 0; j < this.B; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.b.nextInt(256);  //256=128
            i1 = this.d + this.b.nextInt(16) + 8;
            WorldGenerator worldgenerator1 = this.e.b(this.b);

            worldgenerator1.a(this.a, this.b, k, l, i1);
        }

        for (j = 0; j < this.C; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.b.nextInt(256);  //256=128
            i1 = this.d + this.b.nextInt(16) + 8;
            (new WorldGenDeadBush(Block.DEAD_BUSH.id)).a(this.a, this.b, k, l, i1);
        }

        for (j = 0; j < this.y; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;

            for (i1 = this.b.nextInt(256); i1 > 0 && this.a.getTypeId(k, i1 - 1, l) == 0; --i1) {   //256=128
                ;
            }

            this.x.a(this.a, this.b, k, i1, l);
        }

        for (j = 0; j < this.D; ++j) {
            if (this.b.nextInt(4) == 0) {
                k = this.c + this.b.nextInt(16) + 8;
                l = this.d + this.b.nextInt(16) + 8;
                i1 = this.a.getHighestBlockYAt(k, l);
                this.s.a(this.a, this.b, k, i1, l);
            }

            if (this.b.nextInt(8) == 0) {
                k = this.c + this.b.nextInt(16) + 8;
                l = this.d + this.b.nextInt(16) + 8;
                i1 = this.b.nextInt(256);  //256=128
                this.t.a(this.a, this.b, k, i1, l);
            }
        }

        if (this.b.nextInt(4) == 0) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.b.nextInt(256);  //256=128
            l = this.d + this.b.nextInt(16) + 8;
            this.s.a(this.a, this.b, j, k, l);
        }

        if (this.b.nextInt(8) == 0) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.b.nextInt(256);  //256=128
            l = this.d + this.b.nextInt(16) + 8;
            this.t.a(this.a, this.b, j, k, l);
        }

        for (j = 0; j < this.E; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(256);  //256=128
            this.v.a(this.a, this.b, k, i1, l);
        }

        for (j = 0; j < 10; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.b.nextInt(256);  //256=128
            i1 = this.d + this.b.nextInt(16) + 8;
            this.v.a(this.a, this.b, k, l, i1);
        }

        if (this.b.nextInt(32) == 0) {
            j = this.c + this.b.nextInt(16) + 8;
            k = this.b.nextInt(256);  //256=128
            l = this.d + this.b.nextInt(16) + 8;
            (new WorldGenPumpkin()).a(this.a, this.b, j, k, l);
        }

        for (j = 0; j < this.F; ++j) {
            k = this.c + this.b.nextInt(16) + 8;
            l = this.b.nextInt(256);  //256=128
            i1 = this.d + this.b.nextInt(16) + 8;
            this.w.a(this.a, this.b, k, l, i1);
        }

        if (this.K) {
            for (j = 0; j < 50; ++j) {
                k = this.c + this.b.nextInt(16) + 8;
                l = this.b.nextInt(this.b.nextInt(240) + 8); //120=240
                i1 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Block.WATER.id)).a(this.a, this.b, k, l, i1);
            }

            for (j = 0; j < 20; ++j) {
                k = this.c + this.b.nextInt(16) + 8;
                l = this.b.nextInt(this.b.nextInt(this.b.nextInt(224) + 8) + 8); //112=224
                i1 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Block.LAVA.id)).a(this.a, this.b, k, l, i1);
            }
        }
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
