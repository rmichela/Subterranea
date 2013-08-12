package com.ryanmichela.subterranea;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright 2013 Ryan Michela
 */
public class SPlugin extends JavaPlugin {
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().info("Subterranea loaded for world " + worldName);

        return new SChunkGenerator();
    }
}
