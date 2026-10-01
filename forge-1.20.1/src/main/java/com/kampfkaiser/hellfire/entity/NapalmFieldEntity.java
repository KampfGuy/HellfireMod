package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** A patch of game fire that keeps lighting the surface for a while. */
public class NapalmFieldEntity extends DurableEntity {
    private int life = 600;

    public NapalmFieldEntity(EntityType<? extends NapalmFieldEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, int life) {
        if (Env.client(level)) return;
        NapalmFieldEntity field = new NapalmFieldEntity(ModEntities.napalmType(), level);
        field.setPos(pos.x, pos.y, pos.z);
        field.markGhost();
        field.life = life;
        level.addFreshEntity(field);
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
        if (!(world() instanceof ServerLevel server)) return;
        if (tickCount % 8 == 0) {
            int cx = net.minecraft.util.Mth.floor(getX());
            int cz = net.minecraft.util.Mth.floor(getZ());
            int cy = net.minecraft.util.Mth.floor(getY());
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (dx * dx + dz * dz > 6) continue;
                    light(server, cx + dx, cy, cz + dz);
                }
            }
            server.sendParticles(ParticleTypes.FLAME, getX(), getY() + 0.4, getZ(), 8, 1.2, 0.3, 1.2, 0.01);
            server.sendParticles(ParticleTypes.LAVA, getX(), getY() + 0.2, getZ(), 2, 1.0, 0.2, 1.0, 0.0);
            AABB box = new AABB(position(), position()).inflate(3.0, 2.0, 3.0);
            for (Entity entity : nearby(server, box)) {
                if (entity instanceof LivingEntity living && living.isAlive()) {
                    living.setRemainingFireTicks(160);
                }
            }
        }
    }

    private void light(ServerLevel level, int x, int y, int z) {
        for (int dy = 3; dy >= -6; dy--) {
            BlockPos ground = new BlockPos(x, y + dy, z);
            BlockPos above = ground.above();
            if (!level.getBlockState(ground).isAir() && level.getBlockState(above).isAir()) {
                if (Blocks.FIRE.defaultBlockState().canSurvive(level, above)) {
                    level.setBlock(above, Blocks.FIRE.defaultBlockState(), 2);
                }
                return;
            }
        }
    }

    protected java.util.List<Entity> nearby(ServerLevel level, AABB box) {
        return level.getEntities((Entity) null, box);
    }

    @Override
    protected void readExtra(CompoundTag tag) { this.life = Nbt.getInt(tag, "Life", 600); }

    @Override
    protected void writeExtra(CompoundTag tag) { Nbt.putInt(tag, "Life", this.life); }
}
