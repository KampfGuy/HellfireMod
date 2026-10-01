package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.HellfireConfig;
import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NuclearChargeEntity extends DurableEntity {
    private int fuse = 200;

    public NuclearChargeEntity(EntityType<? extends NuclearChargeEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos) {
        if (Env.client(level)) return;
        NuclearChargeEntity charge = new NuclearChargeEntity(ModEntities.chargeType(), level);
        charge.setPos(pos.x, pos.y, pos.z);
        charge.markGhost();
        charge.setPermanentlyInvulnerable(true);
        charge.fuse = HellfireConfig.fuseTicks();
        level.addFreshEntity(charge);
        Env.sound(level, pos.x, pos.y, pos.z, "fuse", 1.0F, 0.7F);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.fuse--;
        if (world() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY() + 0.6, getZ(), 3, 0.15, 0.25, 0.15, 0.01);
            server.sendParticles(ParticleTypes.FLAME, getX(), getY() + 0.3, getZ(), 2, 0.12, 0.05, 0.12, 0.01);
            if (this.fuse % 20 == 0) {
                Env.sound(server, getX(), getY(), getZ(), "fuse", 1.2F, 0.8F + (1.0F - this.fuse / 200.0F) * 0.6F);
            }
        }
        if (this.fuse <= 0) {
            Level level = world();
            Vec3 pos = position();
            discard();
            Blasts.nuclear(level, pos);
        }
    }

    @Override
    protected void readExtra(ValueInput tag) {
        this.fuse = Nbt.getInt(tag, "Fuse", 200);
    }

    @Override
    protected void writeExtra(ValueOutput tag) {
        Nbt.putInt(tag, "Fuse", this.fuse);
    }
}
