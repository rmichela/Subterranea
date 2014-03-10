package com.ryanmichela.subterranea.biome;

import com.ryanmichela.subterranea.worldgen.SWorldProvider;
import net.minecraft.server.v1_7_R1.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by rmichela on 3/7/14.
 */
public abstract class SBiomeMesaBase extends BiomeMesa {

    private byte[] aC;
    private long aD;
    private NoiseGenerator3 aE;
    private NoiseGenerator3 aF;
    private NoiseGenerator3 aG;
    private boolean aH;
    private boolean aI;

    public SBiomeMesaBase(int i, boolean b, boolean b2) {
        super(i, b, b2);
        this.aH = b;
        this.aI = b2;
    }

    public void a(World world, Random random, Block[] ablock, byte[] abyte, int i, int j, double d0) {
        if (!(world.worldProvider instanceof SWorldProvider)) {
            // Only affect Subterranea worlds
            super.a(world, random, ablock, abyte, i, j, d0);
            return;
        }


        if (this.aC == null || this.aD != world.getSeed()) {
            this.a(world.getSeed());
        }

        if (this.aE == null || this.aF == null || this.aD != world.getSeed()) {
            Random random1 = new Random(this.aD);

            this.aE = new NoiseGenerator3(random1, 4);
            this.aF = new NoiseGenerator3(random1, 1);
        }

        this.aD = world.getSeed();
        double d1 = 0.0D;
        int k;
        int l;

        if (this.aH) {
            k = (i & -16) + (j & 15);
            l = (j & -16) + (i & 15);
            double d2 = Math.min(Math.abs(d0), this.aE.a((double) k * 0.25D, (double) l * 0.25D));

            if (d2 > 0.0D) {
                double d3 = 0.001953125D;
                double d4 = Math.abs(this.aF.a((double) k * d3, (double) l * d3));

                d1 = d2 * d2 * 2.5D;
                double d5 = Math.ceil(d4 * 50.0D) + 14.0D;

                if (d1 > d5) {
                    d1 = d5;
                }

                d1 += 64.0D;
            }
        }

        k = i & 15;
        l = j & 15;
        boolean flag = true;
        Block block = Blocks.STAINED_HARDENED_CLAY;
        Block block1 = this.ak;
        int i1 = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        boolean flag1 = Math.cos(d0 / 3.0D * 3.141592653589793D) > 0.0D;
        int j1 = -1;
        boolean flag2 = false;
        int k1 = ablock.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (l * 16 + k) * k1 + l1;

            if ((ablock[i2] == null || ablock[i2].getMaterial() == Material.AIR) && l1 < (int) d1 + 128) { // +128
                ablock[i2] = Blocks.STONE;
            }

            if (l1 <= 0 + random.nextInt(5)) {
                ablock[i2] = Blocks.BEDROCK;
            } else {
                Block block2 = ablock[i2];

                if (block2 != null && block2.getMaterial() != Material.AIR) {
                    if (block2 == Blocks.STONE) {
                        byte b0;

                        if (j1 == -1) {
                            flag2 = false;
                            if (i1 <= 0) {
                                block = null;
                                block1 = Blocks.STONE;
                            } else if (l1 >= 59 + 128 && l1 <= 64 + 128) { // +128
                                block = Blocks.STAINED_HARDENED_CLAY;
                                block1 = this.ak;
                            }

                            if (l1 < 63 + 128 && (block == null || block.getMaterial() == Material.AIR)) { // +128
                                block = Blocks.STATIONARY_WATER;
                            }

                            j1 = i1 + Math.max(0, l1 - 63 - 128); // -128
                            if (l1 >= 62 + 128) { // +128
                                if (this.aI && l1 > 86 + i1 * 2 + 128) { // +128
                                    if (flag1) {
                                        ablock[i2] = Blocks.DIRT;
                                        abyte[i2] = 1;
                                    } else {
                                        ablock[i2] = Blocks.GRASS;
                                    }
                                } else if (l1 > 66 + i1 + 128) { // +128
                                    b0 = 16;
                                    if (l1 >= 64 + 128 && l1 <= 127 + 128) { // +128
                                        if (!flag1) {
                                            b0 = this.d(i, l1, j);
                                        }
                                    } else {
                                        b0 = 1;
                                    }

                                    if (b0 < 16) {
                                        ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
                                        abyte[i2] = (byte) b0;
                                    } else {
                                        ablock[i2] = Blocks.HARDENED_CLAY;
                                    }
                                } else {
                                    ablock[i2] = this.ai;
                                    abyte[i2] = (byte) this.aj;
                                    flag2 = true;
                                }
                            } else {
                                ablock[i2] = block1;
                                if (block1 == Blocks.STAINED_HARDENED_CLAY) {
                                    abyte[i2] = 1;
                                }
                            }
                        } else if (j1 > 0) {
                            --j1;
                            if (flag2) {
                                ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
                                abyte[i2] = 1;
                            } else {
                                b0 = this.d(i, l1, j);
                                if (b0 < 16) {
                                    ablock[i2] = Blocks.STAINED_HARDENED_CLAY;
                                    abyte[i2] = b0;
                                } else {
                                    ablock[i2] = Blocks.HARDENED_CLAY;
                                }
                            }
                        }
                    }
                } else {
                    j1 = -1;
                }
            }
        }
    }

    private void a(long i) {
        this.aC = new byte[64];
        Arrays.fill(this.aC, (byte) 16);
        Random random = new Random(i);

        this.aG = new NoiseGenerator3(random, 1);

        int j;

        for (j = 0; j < 64; ++j) {
            j += random.nextInt(5) + 1;
            if (j < 64) {
                this.aC[j] = 1;
            }
        }

        j = random.nextInt(4) + 2;

        int k;
        int l;
        int i1;
        int j1;

        for (k = 0; k < j; ++k) {
            l = random.nextInt(3) + 1;
            i1 = random.nextInt(64);

            for (j1 = 0; i1 + j1 < 64 && j1 < l; ++j1) {
                this.aC[i1 + j1] = 4;
            }
        }

        k = random.nextInt(4) + 2;

        int k1;

        for (l = 0; l < k; ++l) {
            i1 = random.nextInt(3) + 2;
            j1 = random.nextInt(64);

            for (k1 = 0; j1 + k1 < 64 && k1 < i1; ++k1) {
                this.aC[j1 + k1] = 12;
            }
        }

        l = random.nextInt(4) + 2;

        for (i1 = 0; i1 < l; ++i1) {
            j1 = random.nextInt(3) + 1;
            k1 = random.nextInt(64);

            for (int l1 = 0; k1 + l1 < 64 && l1 < j1; ++l1) {
                this.aC[k1 + l1] = 14;
            }
        }

        i1 = random.nextInt(3) + 3;
        j1 = 0;

        for (k1 = 0; k1 < i1; ++k1) {
            byte b0 = 1;

            j1 += random.nextInt(16) + 4;

            for (int i2 = 0; j1 + i2 < 64 && i2 < b0; ++i2) {
                this.aC[j1 + i2] = 0;
                if (j1 + i2 > 1 && random.nextBoolean()) {
                    this.aC[j1 + i2 - 1] = 8;
                }

                if (j1 + i2 < 63 && random.nextBoolean()) {
                    this.aC[j1 + i2 + 1] = 8;
                }
            }
        }
    }

    private byte d(int i, int j, int k) {
        int l = (int) Math.round(this.aG.a((double) i * 1.0D / 512.0D, (double) i * 1.0D / 512.0D) * 2.0D);

        return this.aC[(j + l + 64) % 64];
    }
}
