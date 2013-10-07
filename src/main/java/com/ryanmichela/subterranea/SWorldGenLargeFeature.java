package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.*;

import java.util.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenLargeFeature extends SStructureGenerator {
    private static List e = Arrays.asList(new BiomeBase[]{BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND});
    private List f;
    private int g;
    private int h;

    public SWorldGenLargeFeature() {
        this.f = new ArrayList();
        this.g = 32;
        this.h = 8;
        this.f.add(new BiomeMeta(EntityWitch.class, 1, 1, 1));
    }

    public SWorldGenLargeFeature(Map map) {
        this();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            if (((String) entry.getKey()).equals("distance")) {
                this.g = MathHelper.a((String) entry.getValue(), this.g, this.h + 1);
            }
        }
    }

    public String a() {
        return "Temple";
    }

    protected boolean a(int i, int j) {
        int k = i;
        int l = j;

        if (i < 0) {
            i -= this.g - 1;
        }

        if (j < 0) {
            j -= this.g - 1;
        }

        int i1 = i / this.g;
        int j1 = j / this.g;
        Random random = this.c.H(i1, j1, 14357617);

        i1 *= this.g;
        j1 *= this.g;
        i1 += random.nextInt(this.g - this.h);
        j1 += random.nextInt(this.g - this.h);
        if (k == i1 && l == j1) {
            BiomeBase biomebase = this.c.getWorldChunkManager().getBiome(k * 16 + 8, l * 16 + 8);
            Iterator iterator = e.iterator();

            while (iterator.hasNext()) {
                BiomeBase biomebase1 = (BiomeBase) iterator.next();

                if (biomebase == biomebase1) {
                    return true;
                }
            }
        }

        return false;
    }

    protected StructureStart b(int i, int j) {
        return new SWorldGenLargeFeatureStart(this.c, this.b, i, j);
    }

    public boolean a(int i, int j, int k) {
        StructureStart structurestart = this.c(i, j, k);

        if (structurestart != null && structurestart instanceof SWorldGenLargeFeatureStart) {
            LinkedList structurestartA = ReflectionUtil.getProtectedValue(structurestart, "a");
            if (!structurestartA.isEmpty()) {
                StructurePiece structurepiece = (StructurePiece) structurestartA.getFirst();

                return structurepiece instanceof WorldGenWitchHut;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List b() {
        return this.f;
    }
}
