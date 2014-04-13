package com.ryanmichela.subterranea.biome;

import net.minecraft.server.v1_7_R3.BiomeTemperature;

/**
 * Created by rmichela on 3/10/14.
 */
public class SBiomeExtremeHills extends SBiomeBigHillsBase {
    public SBiomeExtremeHills() {
        super(3, false, 6316128, "Extreme Hills", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F));
    }
}
