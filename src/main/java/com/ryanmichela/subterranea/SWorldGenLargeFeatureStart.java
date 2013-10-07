package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.*;

import java.util.Iterator;
import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SWorldGenLargeFeatureStart extends StructureStart {
    public SWorldGenLargeFeatureStart() {}

    public SWorldGenLargeFeatureStart(World world, Random random, int i, int j) {
        super(i, j);
        BiomeBase biomebase = world.getBiome(i * 16 + 8, j * 16 + 8);

        if (biomebase != BiomeBase.JUNGLE && biomebase != BiomeBase.JUNGLE_HILLS) {
            if (biomebase == BiomeBase.SWAMPLAND) {
                WorldGenWitchHut worldgenwitchhut = new WorldGenWitchHut(random, i * 16, j * 16);

                this.a.add(worldgenwitchhut);
            } else {
                WorldGenPyramidPiece worldgenpyramidpiece = new SWorldGenPyramidPiece(random, i * 16, j * 16);

                this.a.add(worldgenpyramidpiece);
            }
        } else {
            WorldGenJungleTemple worldgenjungletemple = new WorldGenJungleTemple(random, i * 16, j * 16);

            this.a.add(worldgenjungletemple);
        }

        this.c();
    }

    @Override
    public NBTTagCompound a(int i, int j) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.setString("id", SWorldGenFactory.a(this));
        nbttagcompound.setInt("ChunkX", i);
        nbttagcompound.setInt("ChunkZ", j);
        nbttagcompound.set("BB", this.b.a("BB"));
        NBTTagList nbttaglist = new NBTTagList("Children");
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext()) {
            StructurePiece structurepiece = (StructurePiece) iterator.next();

            nbttaglist.add(structurepiece.b());
        }

        nbttagcompound.set("Children", nbttaglist);
        this.a(nbttagcompound);
        return nbttagcompound;
    }

    @Override
    public void a(World world, NBTTagCompound nbttagcompound) {
        ReflectionUtil.setProtectedValue(StructureStart.class, this, "c", nbttagcompound.getInt("ChunkX"));
        ReflectionUtil.setProtectedValue(StructureStart.class, this, "d", nbttagcompound.getInt("ChunkZ"));
        if (nbttagcompound.hasKey("BB")) {
            this.b = new StructureBoundingBox(nbttagcompound.getIntArray("BB"));
        }

        NBTTagList nbttaglist = nbttagcompound.getList("Children");

        for (int i = 0; i < nbttaglist.size(); ++i) {
            this.a.add(SWorldGenFactory.b((NBTTagCompound) nbttaglist.get(i), world));
        }

        this.b(nbttagcompound);
    }
}
