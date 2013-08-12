package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.*;
import net.minecraft.server.v1_6_R2.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunkGenerator extends ChunkGenerator {
    private IChunkProvider provider = null;

    public SChunkGenerator() {
    }

    private IChunkProvider lazyGetProvider(org.bukkit.World bukkitWorld)
    {
        if (provider == null) {
            World world = ((CraftWorld)bukkitWorld).getHandle();

            // Patch WorldProvider so structures generate at correct sea level
            world.worldProvider = new SWorldProvider();
            world.worldProvider.a(world);

            // Patch biomes so ores have thicker bands
            try {
                setFinalStatic(BiomeBase.class.getField("EXTREME_HILLS"), new SBiomeBigHills(3));
                setFinalStatic(BiomeBase.class.getField("JUNGLE"), new SBiomeJungle(21));
                setFinalStatic(BiomeBase.class.getField("JUNGLE_HILLS"), new SBiomeJungle(22));
            }
            catch (Exception e) {}
            for(BiomeBase b : BiomeBase.biomes) {
                if (b != null) {
                    b.I = new SBiomeDecorator(b);
                }
            }

            provider = new SChunkProviderGenerate(world, world.getSeed(), world.getWorldData().shouldGenerateMapFeatures());
        }
        return provider;
    }

    @Override
    public byte[][] generateBlockSections(org.bukkit.World world, Random random, int x, int z, BiomeGrid biomes) {
        ChunkSection[] chunkSections = lazyGetProvider(world).getOrCreateChunk(x, z).i();
        byte[][] sectionBytes = new byte[16][];

        for(int i = 0; i < 16; i++) {
            if (chunkSections[i] != null) {
                sectionBytes[i] = chunkSections[i].getIdArray();
            }
        }

        return sectionBytes;
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return ((CraftWorld) world).getHandle().worldProvider.canSpawn(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        return new ArrayList<BlockPopulator>();
    }

//    public boolean isChunkLoaded(int i, int i1) {
//        return lazyGetProvider().isChunkLoaded(i, i1);
//    }
//
//    public Chunk getOrCreateChunk(int i, int i1) {
//        return lazyGetProvider().getOrCreateChunk(i, i1);
//    }
//
//    public Chunk getChunkAt(int i, int i1) {
//        return lazyGetProvider().getChunkAt(i, i1);
//    }
//
//    public void getChunkAt(IChunkProvider icp, int i, int i1) {
//        lazyGetProvider().getChunkAt(icp, i, i1);
//    }
//
//    public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
//        return lazyGetProvider().saveChunks(bln, ipu);
//    }
//
//    public boolean unloadChunks() {
//        return lazyGetProvider().unloadChunks();
//    }
//
//    public boolean canSave() {
//        return lazyGetProvider().canSave();
//    }
//
//    public List<?> getMobsFor(EnumCreatureType ect, int i, int i1, int i2) {
//        return lazyGetProvider().getMobsFor(ect, i, i1, i2);
//    }
//
//    public ChunkPosition findNearestMapFeature(World world, String string, int i, int i1, int i2) {
//        return lazyGetProvider().findNearestMapFeature(world, string, i, i1, i2);
//    }
//
//    public void recreateStructures(int i, int j) {
//        lazyGetProvider().recreateStructures(i, j);
//    }
//
//    // n.m.s implementations always return 0. (The true implementation is in ChunkProviderServer)
//    public int getLoadedChunks() {
//        return 0;
//    }
//
//    public String getName() {
//        return "SubterraneaWorldGenerator";
//    }
//
//    public void b() {}

    private void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}

