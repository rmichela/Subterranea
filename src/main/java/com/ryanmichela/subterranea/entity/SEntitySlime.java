package com.ryanmichela.subterranea.entity;

import net.minecraft.server.v1_7_R4.*;

/**
 * Copyright 2013 Ryan Michela
 */
public class SEntitySlime extends EntitySlime {
    public SEntitySlime(World world) {
        super(world);
    }

    @Override
    public boolean canSpawn() {
        Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
        if ((this.world.getWorldData().getType() == WorldType.FLAT) && (this.random.nextInt(4) != 1)) {
            return false;
        }
        if ((getSize() == 1) || (this.world.difficulty != EnumDifficulty.PEACEFUL)) {
            BiomeBase biomebase = this.world.getBiome(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
            if ((biomebase == BiomeBase.SWAMPLAND) && (this.locY > 180D) && (this.locY < 200D) && (this.random.nextFloat() < 0.5F) && (this.random.nextFloat() < this.world.y()) && (this.world.getLightLevel(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) <= this.random.nextInt(8))) {  //50=>180, 70=>200
                return superCanSpawn(); // super.canSpawn() => superCanSpawn()
            }
            if ((this.random.nextInt(10) == 0) && (chunk.a(987234911L).nextInt(10) == 0) && (this.locY < 40.0D)) {
                return superCanSpawn(); // super.canSpawn() => superCanSpawn()
            }
        }
        return false;
    }

    private boolean superCanSpawn() {
        return this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }
}
