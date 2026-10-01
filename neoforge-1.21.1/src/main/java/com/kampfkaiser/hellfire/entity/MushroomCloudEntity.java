package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Stylized particle cloud. Not a physical model of a real detonation. */
public class MushroomCloudEntity extends DurableEntity {
    private int age;
    private int radius = 18;

    public MushroomCloudEntity(EntityType<? extends MushroomCloudEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, int radius) {
        if (Env.client(level)) return;
        MushroomCloudEntity cloud = new MushroomCloudEntity(ModEntities.cloudType(), level);
        cloud.setPos(pos.x, pos.y, pos.z);
        cloud.markGhost();
        cloud.radius = radius;
        level.addFreshEntity(cloud);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.age++;
        if (world() instanceof ServerLevel server) {
            for (int i = 0; i < 18; i++) {
                server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() + i * 1.15, getZ(), 1, 0.35, 0.05, 0.35, 0.01);
                if (i < 7) {
                    server.sendParticles(ParticleTypes.FLAME, getX(), getY() + i * 0.55, getZ(), 1, 0.3, 0.08, 0.3, 0.01);
                }
            }
            double cap = Math.min(9.0, 2.0 + this.age / 5.0);
            double capY = 18.0 + Math.min(8.0, this.age / 12.0);
            for (int i = 0; i < 18; i++) {
                double angle = i * Math.PI * 2.0 / 18.0;
                double x = getX() + Math.cos(angle) * cap;
                double z = getZ() + Math.sin(angle) * cap;
                server.sendParticles(ParticleTypes.CLOUD, x, getY() + capY, z, 2, 0.45, 0.15, 0.45, 0.005);
                server.sendParticles(ParticleTypes.LARGE_SMOKE, x * 0.15 + getX() * 0.85, getY() + capY - 1.5, z * 0.15 + getZ() * 0.85, 1, 0.3, 0.1, 0.3, 0.01);
            }
            if (this.age < 30 && this.age % 4 == 0) {
                server.sendParticles(ParticleTypes.EXPLOSION, getX(), getY() + 2.0, getZ(), 1, 0.2, 0.2, 0.2, 0.0);
            }
        }
        if (this.age > 170) discard();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.age = Nbt.getInt(tag, "Age", 0);
        this.radius = Nbt.getInt(tag, "Radius", 18);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Age", this.age);
        Nbt.putInt(tag, "Radius", this.radius);
    }
}
