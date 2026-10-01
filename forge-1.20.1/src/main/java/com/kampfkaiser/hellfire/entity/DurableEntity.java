
package com.kampfkaiser.hellfire.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class DurableEntity extends TrackedEntity {
    public DurableEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
