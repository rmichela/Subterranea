package com.ryanmichela.subterranea;

import com.ryanmichela.giantcaves.GCPlugin;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.text.html.Option;

/**
 * Copyright 2013 Ryan Michela
 */
public class SPlugin extends JavaPlugin {
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
            }
        };

        try {
            OptionSet optionSet = parser.parse(id.split(" "));

            GeneratorOptions options = new GeneratorOptions();
            options.undergroundBiomes = (Boolean)optionSet.valueOf("underground-biomes");
            options.silverfish = (Boolean)optionSet.valueOf("silverfish");
            options.giantCaves = optionSet.has("giant-caves");
            options.caveSettings = (String)optionSet.valueOf("giant-caves");

            return new SChunkGenerator(this, options);
        } catch (OptionException ex) {
            getLogger().severe(ChatColor.RED + "Failed to parse generator options: " + id);
        }

        return null;
    }
}
