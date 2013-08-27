package com.ryanmichela.subterranea;

import com.ryanmichela.moresilverfish.SilverfishPopulator;
import com.ryanmichela.undergroundbiomes.UndergroundBiomePopulator;
import net.minecraft.server.v1_6_R2.*;
import net.minecraft.server.v1_6_R2.World;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunkGenerator extends ChunkGenerator {
    private static SChunkProviderGenerate provider = null;

    public SChunkGenerator() {
    }

    public static SChunkProviderGenerate lazyGetProvider(org.bukkit.World bukkitWorld)
    {
        if (provider == null) {
            World world = ((CraftWorld)bukkitWorld).getHandle();

            // Patch WorldProvider so structures generate at correct sea level
            world.worldProvider = new SWorldProvider();
            world.worldProvider.a(world);

            // Patch biomes so ores have thicker bands
            ReflectionUtil.setProtectedValue(BiomeBase.class, "EXTREME_HILLS", new SBiomeBigHills(3));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE", new SBiomeJungle(21));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE_HILLS", new SBiomeJungle(22));

            ReflectionUtil.setProtectedValue(BiomeBase.class, BiomeBase.SWAMPLAND, "R", new SWorldGenSwampTree());

            for(BiomeBase b : BiomeBase.biomes) {
                if (b != null && b.I.getClass() == BiomeDecorator.class) {  // Don't update the End or the Nether
                    b.I = new SBiomeDecorator(b);
                }
            }

            provider = new SChunkProviderGenerate(world, world.getSeed(), world.getWorldData().shouldGenerateMapFeatures());
        }
        return provider;
    }

    @Override
    public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
        SChunkProviderGenerate chunkProvider = lazyGetProvider(world);
        return chunkProvider.getChunkSectionsAt(x, z);
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return ((CraftWorld) world).getHandle().worldProvider.canSpawn(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
        populators.add(new SBlockPopulator());
        populators.add(new UndergroundBiomePopulator());
        populators.add(new SilverfishPopulator());
        return populators;
    }

    private class SBlockPopulator extends BlockPopulator{

        @Override
        public void populate(org.bukkit.World world, Random random, org.bukkit.Chunk chunk) {
            IChunkProvider chunkProvider = lazyGetProvider(world);
            chunkProvider.getChunkAt(null, chunk.getX(), chunk.getZ());
        }
    }
}

