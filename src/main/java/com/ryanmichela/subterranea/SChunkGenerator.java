package com.ryanmichela.subterranea;

import com.ryanmichela.giantcaves.Config;
import com.ryanmichela.giantcaves.GiantCavePopulator;
import com.ryanmichela.moresilverfish.SilverfishPopulator;
import com.ryanmichela.subterranea.biome.*;
import com.ryanmichela.subterranea.entity.SEntitySlime;
import com.ryanmichela.subterranea.entity.SEntitySquid;
import com.ryanmichela.subterranea.worldgen.SWorldProvider;
import com.ryanmichela.undergroundbiomes.UndergroundBiomePopulator;
import net.minecraft.server.v1_7_R3.*;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SChunkGenerator extends ChunkGenerator {
    private static SChunkProviderGenerate provider = null;
    private final Plugin plugin;
    private final GeneratorOptions options;
    private final ChunkDataCache chunkDataCache;

    public SChunkGenerator(Plugin plugin, GeneratorOptions options, ChunkDataCache chunkDataCache) {
        this.plugin = plugin;
        this.options = options;
        this.chunkDataCache = chunkDataCache;
    }

    public SChunkProviderGenerate lazyGetProvider(org.bukkit.World bukkitWorld)
    {
        if (provider == null) {
            World world = ((CraftWorld)bukkitWorld).getHandle();
            plugin.getLogger().info("Subterranea loaded for world " + bukkitWorld.getName());

            // Patch WorldProvider so structures generate at correct sea level
            world.worldProvider = new SWorldProvider();
            world.worldProvider.a(world);

            // Patch biomes
            ReflectionUtil.setProtectedValue(BiomeBase.class, "EXTREME_HILLS", new SBiomeExtremeHills());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "SMALL_MOUNTAINS", new SBiomeSmallMountains());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "EXTREME_HILLS_PLUS", new SBiomeExtremeHillsPlus());

            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE", new SBiomeJungle());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE_HILLS", new SBiomeJungleHills());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE_EDGE", new SBiomeJungleEdge());

            ReflectionUtil.setProtectedValue(BiomeBase.class, "MESA", new SBiomeMesa());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "MESA_PLATEAU_F", new SBiomeMesaPlateauF());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "MESA_PLATEAU", new SBiomeMesaPlateau());

            ReflectionUtil.setProtectedValue(BiomeBase.class, "DESERT", new SBiomeDesert());
            ReflectionUtil.setProtectedValue(BiomeBase.class, "DESERT_HILLS", new SBiomeDesertHills());

            ReflectionUtil.setProtectedValue(BiomeBase.class, "BEACH", new SBiomeBeach());

            // Patch common aspects of all biomes
            BiomeBase[] biomes = ReflectionUtil.getProtectedValue(BiomeBase.class, "biomes");
            for(BiomeBase b : biomes) {
                if (b != null && b.ar.getClass() == BiomeDecorator.class) {  // Don't update the End or the Nether
                    // Patch the biome decorator
                    b.ar = new SBiomeDecorator(b, options);
                    // Patch biome entity lists
                    ArrayList<BiomeMeta> aquatics = ReflectionUtil.getProtectedValue(b, "au");
                    for(BiomeMeta meta : aquatics) {
                        if (meta.b == EntitySquid.class) {
                            meta.b = SEntitySquid.class;
                        }
                    }
                    ArrayList<BiomeMeta> hostiles = ReflectionUtil.getProtectedValue(b, "as");
                    for(BiomeMeta meta : hostiles) {
                        if (meta.b == EntitySlime.class) {
                            meta.b = SEntitySlime.class;
                        }
                    }
                }
            }

            provider = new SChunkProviderGenerate(world, world.getSeed(), world.getWorldData().shouldGenerateMapFeatures());
        }
        return provider;
    }

    @Override
    public byte[][] generateBlockSections(org.bukkit.World world, Random random, int x, int z, BiomeGrid biomes) {
        try {
            SChunkProviderGenerate chunkProvider = lazyGetProvider(world);
            ChunkSection[] chunkSections = chunkProvider.getChunkSectionsAt(x, z);

            chunkDataCache.storeChunkData(chunkSections);

            // Extract and return base chunk data
            byte[][] chunkSectionBytes = new byte[chunkSections.length][];
            for(int k = 0; k < chunkSectionBytes.length; k++) {
                if (chunkSections[k] != null) {
                    chunkSectionBytes[k] = chunkSections[k].getIdArray();
                } else {
                    chunkSectionBytes[k] = null;
                }
            }
            return chunkSectionBytes;
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            e.printStackTrace();
            return new byte[][] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
        }
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return ((CraftWorld) world).getHandle().worldProvider.canSpawn(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
        populators.add(new SBlockPopulator());

        try {
            if (options.giantCaves) {
                plugin.getLogger().info("Adding Giant Caves to world '" + world.getName() + "' with settings " + options.caveSettings);
                Config caveConfig = parseCaveConfig(options.caveSettings);
                populators.add(new GiantCavePopulator(plugin, caveConfig));
            }
        } catch (NoClassDefFoundError ex) {
            plugin.getLogger().severe("Failed to locate Giant Caves plugin.");
            plugin.getLogger().severe("Download from http://dev.bukkit.org/bukkit-plugins/giant-caves/");
        }

        if (options.undergroundBiomes) {
            plugin.getLogger().info("Adding underground biomes to world '" + world.getName() + "'");
            populators.add(new UndergroundBiomePopulator());
        } else {
            plugin.getLogger().info("Disabling underground biomes in world '" + world.getName() + "'");
        }

        if (options.silverfish) {
            plugin.getLogger().info("Adding silverfish colonies to world '" + world.getName() + "'");
            populators.add(new SilverfishPopulator());
        } else {
            plugin.getLogger().info("Disabling silverfish colonies in world '" + world.getName() + "'");
        }


        return populators;
    }

    private Config parseCaveConfig(String caveSettings) {
        Map<String, Object> kv = new HashMap<String, Object>();
        for(String setting : caveSettings.split(",")) {
            String[] splits = setting.split("=");
            kv.put(splits[0], splits[1]);
        }
        return new Config(kv);
    }

    private class SBlockPopulator extends BlockPopulator{
        @Override
        public void populate(org.bukkit.World world, Random random, org.bukkit.Chunk chunk) {
            IChunkProvider chunkProvider = lazyGetProvider(world);
            chunkProvider.getChunkAt(null, chunk.getX(), chunk.getZ());
        }
    }
}

