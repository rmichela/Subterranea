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

package com.ryanmichela.undergroundbiomes.columnpopulators;

import com.ryanmichela.undergroundbiomes.ColumnPopulator;
import org.bukkit.*;
import org.bukkit.block.Biome;

import java.util.Random;

/**
 */
public class UndergroundTreePopulator implements ColumnPopulator{
    private Biome appliesTo;
    private TreeType treeType;
    private int maxRand;
    private Random r = new Random();

    public UndergroundTreePopulator(Biome appliesTo, TreeType treeType, int maxRand) {
        this.appliesTo = appliesTo;
        this.treeType = treeType;
        this.maxRand = maxRand;
    }

    @Override
    public boolean appliesToBiome(Biome biome) {
        return biome == appliesTo;
    }

    @Override
    public void decorateColumn(int x, int z, ChunkSnapshot snapshot, Chunk chunk) {
        // 1 in 1000 chance of spawning a tree in a column underground
        int nextRand = r.nextInt(10000);
        if (nextRand > maxRand) {
            return;
        }

        Material lastMaterial = Material.getMaterial(snapshot.getBlockTypeId(x, snapshot.getHighestBlockYAt(x, z), z));

        for (int y = snapshot.getHighestBlockYAt(x, z) - 1; y >=0; y--) {
            Material thisMaterial = Material.getMaterial(snapshot.getBlockTypeId(x, y, z));

            // Look for an Air->Stone boundary going down
            if (lastMaterial == Material.AIR && thisMaterial == Material.STONE) {
                int xx = 16 * chunk.getX() + x;
                int zz = 16 * chunk.getZ() + z;
                Location treeLocation = new Location(chunk.getWorld(), xx, y + 1, zz);
                Location dirtLocation = new Location(chunk.getWorld(), xx, y, zz);
                dirtLocation.getWorld().getBlockAt(dirtLocation).setType(Material.DIRT);
                treeLocation.getWorld().generateTree(treeLocation, treeType);
            }
        }
    }
}
