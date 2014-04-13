package com.ryanmichela.subterranea;

import com.ryanmichela.subterranea.entity.SEntitySlime;
import com.ryanmichela.subterranea.entity.SEntitySquid;
import com.ryanmichela.subterranea.worldgen.SWorldGenLargeFeatureStart;
import com.ryanmichela.subterranea.worldgen.SWorldGenPyramidPiece;
import com.ryanmichela.subterranea.worldgen.SWorldProvider;
import net.minecraft.server.v1_7_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_7_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.Map;

/**
 * Copyright 2013 Ryan Michela
 */
public class SPlugin extends JavaPlugin implements Listener {
    private final ChunkDataCache chunkDataCache = new ChunkDataCache();

    @Override
    public void onLoad() {
        // Patch WorldGenFactory once, the first time the plugin loads
        ReflectionUtil.invokeProtectedMethod(WorldGenFactory.class, "a", SWorldGenPyramidPiece.class, "TeDP");
        ReflectionUtil.invokeProtectedMethod(WorldGenFactory.class, "b", SWorldGenLargeFeatureStart.class, "Temple");

        // Patch EntityTypes once, the first time the plugin loads
        patchEntity(SEntitySlime.class, "Slime", 55, 5349438, 8306542);
        patchEntity(SEntitySquid.class, "Squid", 94, 2243405, 7375001);
    }

    @Override
    public void onEnable() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void patchEntity(Class entityClass, String entityName, int entityId, int i2, int i3) {
        Map c = ReflectionUtil.getProtectedValue(EntityTypes.class, "c");
        Map d = ReflectionUtil.getProtectedValue(EntityTypes.class, "d");
        Map e = ReflectionUtil.getProtectedValue(EntityTypes.class, "e");
        Map f = ReflectionUtil.getProtectedValue(EntityTypes.class, "f");
        Map g = ReflectionUtil.getProtectedValue(EntityTypes.class, "g");

        c.put(entityName, entityClass);
        d.put(entityClass, entityName);
        e.put(entityId, entityClass);
        f.put(entityClass, entityId);
        g.put(entityName, entityId);
    }

    @EventHandler
    public void OnChunkLoad(ChunkLoadEvent event) {
        // The custom chunk generator API does not pass through the block data info to the world.
        // As a solution, block data is cached until after the chunk is generated and then reapplied.
        if (event.isNewChunk() && ((CraftWorld)event.getChunk().getWorld()).getHandle().worldProvider instanceof SWorldProvider) {
            Chunk nmsChunk = ((CraftChunk)event.getChunk()).getHandle();
            ChunkSection[] nmsSections = nmsChunk.i();
            ChunkSection[] chunkData = chunkDataCache.claimChunkData();

            for(int i = 0; i < 16; i++) {
                if (nmsSections[i] != null && chunkData[i] != null) {
                    nmsSections[i].setDataArray(chunkData[i].getDataArray());
                }
            }
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (id == null) {
            id = "";
        }

        OptionParser parser = new OptionParser() {
            {
                accepts("underground-biomes")
                        .withRequiredArg()
                        .ofType(Boolean.class)
                        .defaultsTo(true);

                accepts("silverfish")
                        .withRequiredArg()
                        .ofType(Boolean.class)
                        .defaultsTo(true);

                accepts("giant-caves")
                        .withOptionalArg()
                        .ofType(String.class)
                        .defaultsTo("sxz=500,sy=175,cutoff=65,miny=40,maxy=160");

                accepts("ore-multiplier")
                        .withRequiredArg()
                        .ofType(Integer.class)
                        .defaultsTo(3);
            }
        };

        try {
            OptionSet optionSet = parser.parse(id.split(" "));

            GeneratorOptions options = new GeneratorOptions();
            options.undergroundBiomes = (Boolean)optionSet.valueOf("underground-biomes");
            options.silverfish = (Boolean)optionSet.valueOf("silverfish");
            options.giantCaves = optionSet.has("giant-caves");
            options.caveSettings = (String)optionSet.valueOf("giant-caves");
            options.oreMultiplier = (Integer)optionSet.valueOf("ore-multiplier");

            return new SChunkGenerator(this, options, chunkDataCache);
        } catch (OptionException ex) {
            getLogger().severe(ChatColor.RED + "Failed to parse generator options: " + id);
        }

        return null;
    }
}
