//    Copyright (C) 2012  Ryan Michela
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.ryanmichela.undergroundbiomes;

import com.ryanmichela.undergroundbiomes.columnpopulators.DesertColumnPopulator;
import com.ryanmichela.undergroundbiomes.columnpopulators.MushroomColumnPopulator;
import com.ryanmichela.undergroundbiomes.columnpopulators.UndergroundTreePopulator;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

/**
 */
public class UndergroundBiomePopulator extends BlockPopulator {

    private ColumnPopulator[] columnPopulators;

    public UndergroundBiomePopulator() {
        columnPopulators = new ColumnPopulator[] { new DesertColumnPopulator(),
                                                   new MushroomColumnPopulator(),
                                                   new UndergroundTreePopulator(Biome.JUNGLE, TreeType.SMALL_JUNGLE, 10),
                                                   new UndergroundTreePopulator(Biome.JUNGLE_HILLS, TreeType.SMALL_JUNGLE, 10),
                                                   new UndergroundTreePopulator(Biome.FOREST, TreeType.TREE, 1),
                                                   new UndergroundTreePopulator(Biome.FOREST_HILLS, TreeType.TREE, 1),
                                                   new UndergroundTreePopulator(Biome.TAIGA, TreeType.REDWOOD, 1),
                                                   new UndergroundTreePopulator(Biome.TAIGA_HILLS, TreeType.REDWOOD, 1),
                                                   new UndergroundTreePopulator(Biome.MUSHROOM_ISLAND, TreeType.RED_MUSHROOM, 60),
                                                   new UndergroundTreePopulator(Biome.MUSHROOM_ISLAND, TreeType.BROWN_MUSHROOM, 60)
                                                 };
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        ChunkSnapshot snapshot = chunk.getChunkSnapshot(true, true, true);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int p = 0; p < columnPopulators.length; p++) {
                    if (columnPopulators[p].appliesToBiome(snapshot.getBiome(x, z))) {
                        columnPopulators[p].decorateColumn(x, z, snapshot, chunk);
                    }
                }
            }
        }
    }
}
