package com.ryanmichela.subterranea.entity;

import net.minecraft.server.v1_7_R1.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SEntitySlime extends EntitySlime {
    public SEntitySlime(World world) {
        super(world);
    }

    @Override
    public boolean canSpawn() {
        Chunk chunk = world.getChunkAtWorldCoords(MathHelper.floor(locX), MathHelper.floor(locZ));
        if (world.getWorldData().getType() == WorldType.FLAT && random.nextInt(4) != 1)
            return false;
        if (getSize() == 1 || world.difficulty != EnumDifficulty.PEACEFUL) {
            BiomeBase biomebase = world.getBiome(MathHelper.floor(locX), MathHelper.floor(locZ));
            if (biomebase == BiomeBase.SWAMPLAND && locY > 180D && locY < 200D && random.nextFloat() < 0.5F && random.nextFloat() < world.x() && world.getLightLevel(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ)) <= random.nextInt(8)) //50=>180, 70=>200
                return superCanSpawn();
            if (random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && locY < 40D)
                return superCanSpawn();
        }
        return false;
    }

    private boolean superCanSpawn() {
        return this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }
}
