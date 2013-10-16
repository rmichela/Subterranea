package com.ryanmichela.subterranea;

import net.minecraft.server.v1_6_R3.EntityTypes;
import net.minecraft.server.v1_6_R3.WorldGenFactory;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Copyright 2013 Ryan Michela
 */
public class SPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        // Patch WorldGenFactory once, the first time the plugin loads
        HashMap factoryA = ReflectionUtil.getProtectedValue(WorldGenFactory.class, "a");
        HashMap factoryB = ReflectionUtil.getProtectedValue(WorldGenFactory.class, "b");
        HashMap factoryC = ReflectionUtil.getProtectedValue(WorldGenFactory.class, "c");
        HashMap factoryD = ReflectionUtil.getProtectedValue(WorldGenFactory.class, "d");
        factoryA.put("Temple", SWorldGenLargeFeatureStart.class);
        factoryB.put(SWorldGenLargeFeatureStart.class, "Temple");
        factoryC.put("TeDP", SWorldGenPyramidPiece.class);
        factoryD.put(SWorldGenPyramidPiece.class, "TeDP");

        // Patch EntityTypes once, the first time the plugin loads
        ReflectionUtil.invokeProtectedMethod(EntityTypes.class, "a", SEntitySlime.class, "Slime", 55, 5349438, 8306542);
        ReflectionUtil.invokeProtectedMethod(EntityTypes.class, "a", SEntitySquid.class, "Squid", 94, 2243405, 7375001);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().info("Subterranea loaded for world " + worldName);
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

            return new SChunkGenerator(this, options);
        } catch (OptionException ex) {
            getLogger().severe(ChatColor.RED + "Failed to parse generator options: " + id);
        }

        return null;
    }
}
