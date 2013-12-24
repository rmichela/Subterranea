package com.ryanmichela.subterranea;

/**
 * Copyright 2013 Ryan Michela
 */
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SChunkProviderGenerate implements IChunkProvider {

    private Random random; //k=random
    private NoiseGeneratorOctaves noise1; //l=noise1
    private NoiseGeneratorOctaves noise2; //m=noise2
    private NoiseGeneratorOctaves noise3; //n=noise3
    private NoiseGeneratorOctaves noise4; //o=noise4
    public NoiseGeneratorOctaves a;
    public NoiseGeneratorOctaves b;
    public NoiseGeneratorOctaves c;
    private World world; //p=world
    private final boolean generateStructures; //q=generateStructures
    private double[] r;
    private double[] s = new double[256];
    private WorldGenBase caveGen = new SWorldGenCaves(); //t=caveGen
    private WorldGenStronghold strongholdGen = new WorldGenStronghold(); //u=strongholdGen
    private WorldGenVillage villageGen = new WorldGenVillage(); //v=villageGen
    private WorldGenMineshaft mineshaftGen = new WorldGenMineshaft(); //w=mineshaftGen
    private WorldGenLargeFeature largeFeatureGen = new SWorldGenLargeFeature(); //x=largeFeatureGen
    private WorldGenBase canyonGen = new SWorldGenCanyon(); //y=canyonGen
    private BiomeBase[] biomeBases; //z=biomeBases
    double[] d;
    double[] e;
    double[] f;
    double[] g;
    double[] h;
    float[] i;
    int[][] j = new int[32][32];

    public SChunkProviderGenerate(World world, long i, boolean flag) {
        this.world = world;
        this.generateStructures = flag;
        this.random = new Random(i);
        this.noise1 = new NoiseGeneratorOctaves(this.random, 16);
        this.noise2 = new NoiseGeneratorOctaves(this.random, 16);
        this.noise3 = new NoiseGeneratorOctaves(this.random, 8);
        this.noise4 = new NoiseGeneratorOctaves(this.random, 4);
        this.a = new NoiseGeneratorOctaves(this.random, 10);
        this.b = new NoiseGeneratorOctaves(this.random, 16);
        this.c = new NoiseGeneratorOctaves(this.random, 8);
    }

    public void makeWorldStoneAndWater(int xx, int zz, byte[] rawChunk) { //a()=makeWorldStoneAndWater(), i=xx, j=zz, abyte=rawChunk
        byte b0 = 4;
        byte b1 = 16;
        byte waterline = 63; // Absolute sea level, b2=waterline
        int k = b0 + 1;
        byte b3 = 17;
        int l = b0 + 1;

        this.biomeBases = this.world.getWorldChunkManager().getBiomes(this.biomeBases, xx * 4 - 2, zz * 4 - 2, k + 5, l + 5);
        this.r = this.a(this.r, xx * b0, 0, zz * b0, k, b3, l);

        for (int xxx = 0; xxx < b0; ++xxx) { // This loop makes the world's stone, i1=xxx
            for (int zzz = 0; zzz < b0; ++zzz) { //j1=zzz
                for (int yyy = 0; yyy < b1; ++yyy) { //k1=yyy
                    double d0 = 0.125D;
                    double d1 = this.r[((xxx + 0) * l + zzz + 0) * b3 + yyy + 0];
                    double d2 = this.r[((xxx + 0) * l + zzz + 1) * b3 + yyy + 0];
                    double d3 = this.r[((xxx + 1) * l + zzz + 0) * b3 + yyy + 0];
                    double d4 = this.r[((xxx + 1) * l + zzz + 1) * b3 + yyy + 0];
                    double d5 = (this.r[((xxx + 0) * l + zzz + 0) * b3 + yyy + 1] - d1) * d0;
                    double d6 = (this.r[((xxx + 0) * l + zzz + 1) * b3 + yyy + 1] - d2) * d0;
                    double d7 = (this.r[((xxx + 1) * l + zzz + 0) * b3 + yyy + 1] - d3) * d0;
                    double d8 = (this.r[((xxx + 1) * l + zzz + 1) * b3 + yyy + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2) {
                            int chunkOffset = i2 + xxx * 4 << 11 | 0 + zzz * 4 << 7 | yyy * 8 + l1; //j2=chunkOffset
                            short short1 = 128;

                            chunkOffset -= short1;
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2) {
                                if ((d16 += d15) > 0.0D) {
                                    rawChunk[chunkOffset += short1] = (byte) Block.STONE.id;
                                } else if (yyy * 8 + l1 < waterline) {
                                    rawChunk[chunkOffset += short1] = (byte) Block.STATIONARY_WATER.id;
                                } else {
                                    rawChunk[chunkOffset += short1] = 0;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void applyBiomeTopCoverAndBedrock(int xx, int j, byte[] rawChunk, BiomeBase[] biomeBases) { //a=applyBiomeTopCoverAndBedrock, i=xx, abyte=rawChunk, abiomebase=biomeBases
        byte grassBeginHeight = 63; //b0=grassBeginHeight CraftBukkit SEALEVEL - above here, grass/sandstone/ice form
        double d0 = 0.03125D;

        this.s = this.noise4.a(this.s, xx * 16, j * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                BiomeBase biomebase = biomeBases[l + k * 16];
                float f = biomebase.j();
                int i1 = (int) (this.s[k + l * 16] / 3.0D + 3.0D + this.random.nextDouble() * 0.25D);
                int j1 = -1;
                byte b1 = biomebase.A;
                byte b2 = biomebase.B;

                for (int k1 = 255; k1 >= 0; --k1) { //127=>255
                    int l1 = (l * 16 + k) * 256 + k1; //128=>256

                    if (k1 <= 0 + this.random.nextInt(5)) {
                        rawChunk[l1] = (byte) Block.BEDROCK.id;
                    } else {
                        byte b3 = rawChunk[l1];

                        if (b3 == 0) {
                            j1 = -1;
                        } else if (b3 == Block.STONE.id) {
                            if (j1 == -1) {
                                if (i1 <= 0) {
                                    b1 = 0;
                                    b2 = (byte) Block.STONE.id;
                                } else if (k1 >= grassBeginHeight - 4 && k1 <= grassBeginHeight + 1) {
                                    b1 = biomebase.A;
                                    b2 = biomebase.B;
                                }

                                if (k1 < grassBeginHeight && b1 == 0) {
                                    if (f < 0.15F) {
                                        b1 = (byte) Block.ICE.id;
                                    } else {
                                        b1 = (byte) Block.STATIONARY_WATER.id;
                                    }
                                }

                                j1 = i1;
                                if (k1 >= grassBeginHeight - 1) {
                                    rawChunk[l1] = b1;
                                } else {
                                    rawChunk[l1] = b2;
                                }
                            } else if (j1 > 0) {
                                --j1;
                                rawChunk[l1] = b2;
                                if (j1 == 0 && b2 == Block.SAND.id) {
                                    j1 = this.random.nextInt(4);
                                    b2 = (byte) Block.SANDSTONE.id;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Chunk getChunkAt(int xx, int zz) { //i=xx, j=zz
        throw new UnsupportedOperationException();
    }

    public Chunk getOrCreateChunk(int xx, int zz) { //i=xx, j=zz
        throw new UnsupportedOperationException();
    }
    public byte[] getChunkSectionsAt(int xx, int zz) { //i=xx, j=zz
        this.random.setSeed((long) xx * 341873128712L + (long) zz * 132897987541L);

        byte[] chunk128 = new byte[16*16*128]; //abyte=chunk128, byte['\u8000']=byte[16*16*128]
        this.makeWorldStoneAndWater(xx, zz, chunk128);

        byte[] stone128 = new byte[2048];
        Arrays.fill(stone128, (byte) Block.STONE.id);
        byte[] chunk256 = new byte[16*16*256];
        for (int i = 0; i < 16*16; i++) {
            System.arraycopy(stone128, 0, chunk256, 256*i, 128);
            System.arraycopy(chunk128, 128*i, chunk256, 256*i+128, 128);
        }

        this.biomeBases = this.world.getWorldChunkManager().getBiomeBlock(this.biomeBases, xx * 16, zz * 16, 16, 16);
        this.applyBiomeTopCoverAndBedrock(xx, zz, chunk256, this.biomeBases);
        this.caveGen.a(this, this.world, xx, zz, chunk256);
        this.canyonGen.a(this, this.world, xx, zz, chunk256);
        if (this.generateStructures) {
            this.mineshaftGen.a(this, this.world, xx, zz, chunk256);
            this.villageGen.a(this, this.world, xx, zz, chunk256);
            this.strongholdGen.a(this, this.world, xx, zz, chunk256);
            this.largeFeatureGen.a(this, this.world, xx, zz, chunk256);
        }

        return chunk256;
    }

    private double[] a(double[] adouble, int i, int j, int k, int l, int i1, int j1) {
        if (adouble == null) {
            adouble = new double[l * i1 * j1];
        }

        if (this.i == null) {
            this.i = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1) {
                for (int l1 = -2; l1 <= 2; ++l1) {
                    float f = 10.0F / MathHelper.c((float) (k1 * k1 + l1 * l1) + 0.2F);

                    this.i[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;

        this.g = this.a.a(this.g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        this.h = this.b.a(this.h, i, k, l, j1, 200.0D, 200.0D, 0.5D);
        this.d = this.noise3.a(this.d, i, j, k, l, i1, j1, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.e = this.noise1.a(this.e, i, j, k, l, i1, j1, d0, d1, d0);
        this.f = this.noise2.a(this.f, i, j, k, l, i1, j1, d0, d1, d0);
        boolean flag = false;
        boolean flag1 = false;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < l; ++k2) {
            for (int l2 = 0; l2 < j1; ++l2) {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                BiomeBase biomebase = this.biomeBases[k2 + 2 + (l2 + 2) * (l + 5)];

                for (int i3 = -b0; i3 <= b0; ++i3) {
                    for (int j3 = -b0; j3 <= b0; ++j3) {
                        BiomeBase biomebase1 = this.biomeBases[k2 + i3 + 2 + (l2 + j3 + 2) * (l + 5)];
                        float f4 = this.i[i3 + 2 + (j3 + 2) * 5] / (biomebase1.D + 2.0F);

                        if (biomebase1.D > biomebase.D) {
                            f4 /= 2.0F;
                        }

                        f1 += biomebase1.E * f4;
                        f2 += biomebase1.D * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = this.h[j2] / 8000.0D;

                if (d2 < 0.0D) {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;
                if (d2 < 0.0D) {
                    d2 /= 2.0D;
                    if (d2 < -1.0D) {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                } else {
                    if (d2 > 1.0D) {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < i1; ++k3) {
                    double d3 = (double) f2;
                    double d4 = (double) f1;

                    d3 += d2 * 0.2D;
                    d3 = d3 * (double) i1 / 16.0D;
                    double d5 = (double) i1 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = ((double) k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D) {
                        d7 *= 4.0D;
                    }

                    double d8 = this.e[i2] / 512.0D;
                    double d9 = this.f[i2] / 512.0D;
                    double d10 = (this.d[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D) {
                        d6 = d8;
                    } else if (d10 > 1.0D) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    if (k3 > i1 - 4) {
                        double d11 = (double) ((float) (k3 - (i1 - 4)) / 3.0F);

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    adouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return adouble;
    }

    public boolean isChunkLoaded(int i, int j) {
        return true;
    }

    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
        BlockSand.instaFall = true;
        int k = i * 16;
        int l = j * 16;
        BiomeBase biomebase = this.world.getBiome(k + 16, l + 16);

        this.random.setSeed(this.world.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;

        this.random.setSeed((long) i * i1 + (long) j * j1 ^ this.world.getSeed());
        boolean flag = false;

        if (this.generateStructures) {
            this.mineshaftGen.a(this.world, this.random, i, j);
            flag = this.villageGen.a(this.world, this.random, i, j);
            this.strongholdGen.a(this.world, this.random, i, j);
            this.largeFeatureGen.a(this.world, this.random, i, j);
        }

        int k1;
        int l1;
        int i2;

        if (biomebase != BiomeBase.DESERT && biomebase != BiomeBase.DESERT_HILLS && !flag && this.random.nextInt(4) == 0) {
            k1 = k + this.random.nextInt(16) + 8;
            l1 = this.random.nextInt(256); //128=>256
            i2 = l + this.random.nextInt(16) + 8;
            (new WorldGenLakes(Block.STATIONARY_WATER.id)).a(this.world, this.random, k1, l1, i2);
        }

        if (!flag && this.random.nextInt(8) == 0) {
            k1 = k + this.random.nextInt(16) + 8;
            l1 = this.random.nextInt(this.random.nextInt(148) + 8);  //120=>148
            i2 = l + this.random.nextInt(16) + 8;
            if (l1 < 63 || this.random.nextInt(10) == 0) {
                (new WorldGenLakes(Block.STATIONARY_LAVA.id)).a(this.world, this.random, k1, l1, i2);
            }
        }

        for (k1 = 0; k1 < 8; ++k1) {
            l1 = k + this.random.nextInt(16) + 8;
            i2 = this.random.nextInt(256); //128=>256
            int j2 = l + this.random.nextInt(16) + 8;

            (new WorldGenDungeons()).a(this.world, this.random, l1, i2, j2);
        }

        biomebase.a(this.world, this.random, k, l);
        SpawnerCreature.a(this.world, biomebase, k + 8, l + 8, 16, 16, this.random);
        k += 8;
        l += 8;

        for (k1 = 0; k1 < 16; ++k1) {
            for (l1 = 0; l1 < 16; ++l1) {
                i2 = this.world.h(k + k1, l + l1);
                if (this.world.x(k1 + k, i2 - 1, l1 + l)) {
                    this.world.setTypeIdAndData(k1 + k, i2 - 1, l1 + l, Block.ICE.id, 0, 2);
                }

                if (this.world.z(k1 + k, i2, l1 + l)) {
                    this.world.setTypeIdAndData(k1 + k, i2, l1 + l, Block.SNOW.id, 0, 2);
                }
            }
        }

        BlockSand.instaFall = false;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return true;
    }

    public boolean unloadChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String getName() {
        return "RandomLevelSource";
    }

    public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
        BiomeBase biomebase = this.world.getBiome(i, k);

        return biomebase == null ? null : biomebase.getMobs(enumcreaturetype);
    }

    public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
        return "Stronghold".equals(s) && this.strongholdGen != null ? this.strongholdGen.getNearestGeneratedFeature(world, i, j, k) : null;
    }

    public int getLoadedChunks() {
        return 0;
    }

    public void b() {}

    public void recreateStructures(int i, int j) {
        if (this.generateStructures) {
            this.mineshaftGen.a(this, this.world, i, j, (byte[]) null);
            this.villageGen.a(this, this.world, i, j, (byte[]) null);
            this.strongholdGen.a(this, this.world, i, j, (byte[]) null);
            this.largeFeatureGen.a(this, this.world, i, j, (byte[]) null);
        }
    }
}
