package com.ryanmichela.subterranea.biome;

import net.minecraft.server.v1_7_R1.BiomeTemperature;

/**
 * Created by rmichela on 3/10/14.
 */
public class SBiomeExtremeHillsPlus extends SBiomeBigHillsBase {
    public SBiomeExtremeHillsPlus() {
        super(34, true, 5271632, "Extreme Hills+", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F));
    }
}
