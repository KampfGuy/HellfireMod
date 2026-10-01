
package com.kampfkaiser.hellfire.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;

public abstract class DurableEntity extends TrackedEntity {
    public DurableEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
