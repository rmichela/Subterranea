package com.ryanmichela.subterranea;

import com.ryanmichela.giantcaves.Config;
import com.ryanmichela.giantcaves.GiantCavePopulator;
import com.ryanmichela.moresilverfish.SilverfishPopulator;
import com.ryanmichela.undergroundbiomes.UndergroundBiomePopulator;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
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

    public SChunkGenerator(Plugin plugin, GeneratorOptions options) {
        this.plugin = plugin;
        this.options = options;
    }

    public SChunkProviderGenerate lazyGetProvider(org.bukkit.World bukkitWorld)
    {
        if (provider == null) {
            World world = ((CraftWorld)bukkitWorld).getHandle();

            // Patch WorldProvider so structures generate at correct sea level
            world.worldProvider = new SWorldProvider();
            world.worldProvider.a(world);

            // Patch biomes so ores have thicker bands
            ReflectionUtil.setProtectedValue(BiomeBase.class, "EXTREME_HILLS", new SBiomeBigHills(3, false, 6316128, "Extreme Hills", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F)));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "SMALL_MOUNTAINS", new SBiomeBigHills(20, true, 7501978, "Extreme Hills Edge", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F).a()));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "EXTREME_HILLS_PLUS", new SBiomeBigHills(34, true, 5271632, "Extreme Hills+", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F)));

            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE", new SBiomeJungle(21, false, 5470985, "Jungle", 5470985, 0.95F, 0.9F));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE_HILLS", new SBiomeJungle(22, false, 2900485, "JungleHills", 5470985, 0.95F, 0.9F, new BiomeTemperature(0.45F, 0.3F)));
            ReflectionUtil.setProtectedValue(BiomeBase.class, "JUNGLE_EDGE", new SBiomeJungle(23, true, 6458135, "JungleEdge", 5470985, 0.95F, 0.8F));

            // Patch common aspects of all biomes
            BiomeBase[] biomes = ReflectionUtil.getProtectedValue(BiomeBase.class, "biomes");
            for(BiomeBase b : biomes) {
                if (b != null && b.ar.getClass() == BiomeDecorator.class) {  // Don't update the End or the Nether
                    // Patch the biome decorator
                    b.ar = new SBiomeDecorator(b, options);
                    // Patch biome entity lists
                    ArrayList<BiomeMeta> aquatics = ReflectionUtil.getProtectedValue(b, "L");
                    for(BiomeMeta meta : aquatics) {
                        if (meta.b == EntitySquid.class) {
                            meta.b = SEntitySquid.class;
                        }
                    }
                    ArrayList<BiomeMeta> hostiles = ReflectionUtil.getProtectedValue(b, "J");
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
        SChunkProviderGenerate chunkProvider = lazyGetProvider(world);
        return chunkProvider.getChunkSectionsAt(x, z);
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

