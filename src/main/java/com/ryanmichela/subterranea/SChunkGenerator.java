package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R2.*;
import net.minecraft.server.v1_6_R2.Chunk;
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
    private static IChunkProvider provider = null;

    public SChunkGenerator() {
    }

    public static IChunkProvider lazyGetProvider(org.bukkit.World bukkitWorld)
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

        IChunkProvider chunkProvider = lazyGetProvider(world);
        Chunk chunk = chunkProvider.getChunkAt(x, z);



        ChunkSection[] chunkSections = chunk.i();
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
        ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
        populators.add(new SBlockPopulator());
        return populators;
    }

    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    private class SBlockPopulator extends BlockPopulator{

        @Override
        public void populate(org.bukkit.World world, Random random, org.bukkit.Chunk chunk) {
            IChunkProvider chunkProvider = lazyGetProvider(world);
            chunkProvider.getChunkAt(null, chunk.getX(), chunk.getZ());
        }
    }
}

