package com.ryanmichela.subterranea.biome;

import net.minecraft.server.v1_7_R2.BiomeTemperature;

/**
 * Created by rmichela on 3/10/14.
 */
public class SBiomeSmallMountains extends SBiomeBigHillsBase {
    public SBiomeSmallMountains() {
        super(20, true, 7501978, "Extreme Hills Edge", 0.2F, 0.3F, new BiomeTemperature(1.0F, 0.5F).a());
    }
}
