package com.ryanmichela.subterranea.entity;

import net.minecraft.server.v1_7_R2.EntitySquid;
import net.minecraft.server.v1_7_R2.World;

/**
 * Copyright 2013 Ryan Michela
 */
public class SEntitySquid extends EntitySquid {
    public SEntitySquid(World world) {
        super(world);
    }

    @Override
    public boolean canSpawn() {
        return this.locY > 45.0D && this.locY < 191.0D && superCanSpawn();  //63 => 191
    }

    private boolean superCanSpawn() {
        return this.world.b(this.boundingBox);
    }
}
