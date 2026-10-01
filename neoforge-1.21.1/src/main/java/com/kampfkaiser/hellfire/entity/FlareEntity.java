package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FlareEntity extends DurableEntity {
    private int life = 600;

    public FlareEntity(EntityType<? extends FlareEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, int life) {
        if (Env.client(level)) return;
        FlareEntity flare = new FlareEntity(ModEntities.flareType(), level);
        flare.setPos(pos.x, pos.y, pos.z);
        flare.markGhost();
        flare.life = life;
        flare.setGlowingTag(true);
        level.addFreshEntity(flare);
    }

    public static FlareEntity nearest(Level level, Vec3 pos, double radius) {
        FlareEntity best = null;
        double bestDistance = radius * radius;
        AABB box = new AABB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);
        for (FlareEntity flare : level.getEntitiesOfClass(FlareEntity.class, box)) {
            double distance = flare.position().distanceToSqr(pos);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = flare;
            }
        }
        return best;
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.life--;
        if (this.life <= 0) {
            discard();
            return;
        }
        if (tickCount % 2 == 0 && world() instanceof ServerLevel server) {
            for (int i = 0; i < 30; i += 2) {
                server.sendParticles(ParticleTypes.END_ROD, getX(), getY() + i, getZ(), 1, 0.02, 0.15, 0.02, 0.0);
            }
            server.sendParticles(ParticleTypes.FLAME, getX(), getY() + 0.4, getZ(), 2, 0.08, 0.04, 0.08, 0.01);
        }
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.life = Nbt.getInt(tag, "Life", 600);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Life", this.life);
    }
}
