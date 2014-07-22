package com.ryanmichela.subterranea.biome;

import net.minecraft.server.v1_7_R4.BiomeTemperature;

/**
 * Created by rmichela on 3/10/14.
 */
public class SBiomeJungleHills extends SBiomeJungleBase {
    public SBiomeJungleHills() {
        super(22, false, 2900485, "JungleHills", 5470985, 0.95F, 0.9F, new BiomeTemperature(0.45F, 0.3F));
    }
}
