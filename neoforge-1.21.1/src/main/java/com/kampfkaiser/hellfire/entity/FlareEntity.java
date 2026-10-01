package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class FlareEntity extends DurableEntity {
    public static final int RED = 0;
    public static final int YELLOW = 1;
    public static final int ORANGE = 2;
    public static final int BLUE = 3;

    private int life = 200;
    private int color = RED;

    public FlareEntity(EntityType<? extends FlareEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, int life, int color) {
        if (Env.client(level)) return;
        FlareEntity flare = new FlareEntity(ModEntities.flareType(), level);
        flare.setPos(pos.x, pos.y, pos.z);
        flare.markGhost();
        flare.life = life;
        flare.color = color;
        level.addFreshEntity(flare);
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
            Vector3f rgb = switch (this.color) {
                case YELLOW -> new Vector3f(1.0F, 0.86F, 0.05F);
                case ORANGE -> new Vector3f(1.0F, 0.42F, 0.05F);
                case BLUE -> new Vector3f(0.15F, 0.55F, 1.0F);
                default -> new Vector3f(1.0F, 0.08F, 0.05F);
            };
            DustParticleOptions dust = new DustParticleOptions(rgb, 1.35F);
            for (int i = 0; i < 28; i += 2) {
                server.sendParticles(dust, getX(), getY() + i, getZ(), 2, 0.04, 0.05, 0.04, 0.0);
            }
            server.sendParticles(this.color == YELLOW ? ParticleTypes.FLAME : ParticleTypes.LAVA,
                    getX(), getY() + 0.3, getZ(), 1, 0.05, 0.02, 0.05, 0.0);
        }
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.life = Nbt.getInt(tag, "Life", 200);
        this.color = Nbt.getInt(tag, "Color", RED);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Life", this.life);
        Nbt.putInt(tag, "Color", this.color);
    }
}
