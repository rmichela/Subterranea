package com.ryanmichela.subterranea.biome;

import net.minecraft.server.v1_7_R1.*;

import java.util.Random;

/**
 * Created by rmichela on 3/10/14.
 */
public class SBiomeDesertBase extends BiomeDesert {
    public SBiomeDesertBase(int i) {
        super(i);
    }

    public void a(World world, Random random, Block[] ablock, byte[] abyte, int i, int j, double d0) {
        boolean flag = true;
        Block block = this.ai;
        byte b0 = (byte) (this.aj & 255);
        Block block1 = this.ak;
        int k = -1;
        int l = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int i1 = i & 15;
        int j1 = j & 15;
        int k1 = ablock.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + random.nextInt(5)) {
                ablock[i2] = Blocks.BEDROCK;
            } else {
                Block block2 = ablock[i2];

                if (block2 != null && block2.getMaterial() != Material.AIR) {
                    if (block2 == Blocks.STONE) {
                        if (k == -1) {
                            if (l <= 0) {
                                block = null;
                                b0 = 0;
                                block1 = Blocks.STONE;
                            } else if (l1 >= 59 + 128 && l1 <= 64 + 128) { // +128
                                block = this.ai;
                                b0 = (byte) (this.aj & 255);
                                block1 = this.ak;
                            }

                            if (l1 < 63 + 128 && (block == null || block.getMaterial() == Material.AIR)) { // +128
                                if (this.a(i, l1, j) < 0.15F) {
                                    block = Blocks.ICE;
                                    b0 = 0;
                                } else {
                                    block = Blocks.STATIONARY_WATER;
                                    b0 = 0;
                                }
                            }

                            k = l;
                            if (l1 >= 62 + 128) { // +128
                                ablock[i2] = block;
                                abyte[i2] = b0;
                            } else if (l1 < 56 - l + 128) { // +128
                                block = null;
                                block1 = Blocks.STONE;
                                ablock[i2] = Blocks.GRAVEL;
                            } else {
                                ablock[i2] = block1;
                            }
                        } else if (k > 0) {
                            --k;
                            ablock[i2] = block1;
                            if (k == 0 && block1 == Blocks.SAND) {
                                k = random.nextInt(4) + Math.max(0, l1 - 63 - 128); // -128
                                block1 = Blocks.SANDSTONE;
                            }
                        }
                    }
                } else {
                    k = -1;
                }
            }
        }
    }
}
