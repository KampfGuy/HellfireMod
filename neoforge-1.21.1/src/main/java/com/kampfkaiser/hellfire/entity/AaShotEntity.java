package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AaShotEntity extends DurableEntity {
    private int targetId = -1;
    private int age;

    public AaShotEntity(EntityType<? extends AaShotEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, Entity target) {
        if (Env.client(level)) return;
        AaShotEntity shot = new AaShotEntity(ModEntities.shotType(), level);
        shot.setPos(pos.x, pos.y, pos.z);
        shot.markFloater();
        shot.targetId = target.getId();
        level.addFreshEntity(shot);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.age++;
        Entity target = world().getEntity(this.targetId);
        if (target == null || !target.isAlive() || this.age > 60) {
            discard();
            return;
        }
        Vec3 aim = target.getBoundingBox().getCenter().subtract(position());
        if (aim.length() < 1.25) {
            hit(target);
            return;
        }
        Vec3 step = aim.normalize().scale(2.1);
        setDeltaMovement(step);
        setPos(getX() + step.x, getY() + step.y, getZ() + step.z);
        faceMotion();
        if (world() instanceof ServerLevel server && tickCount % 2 == 0) {
            server.sendParticles(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER, getX(), getY(), getZ(), 2, 0.05, 0.05, 0.05, 0.0);
        }
    }

    private void hit(Entity target) {
        if (target instanceof Player player) {
            if (world() instanceof ServerLevel server) Env.harm(server, player, 6.0F);
        } else if (target instanceof LivingEntity living && world() instanceof ServerLevel server) {
            Env.harm(server, living, 40.0F);
            if (living.isAlive()) living.discard();
        } else {
            target.discard();
        }
        Env.sound(world(), getX(), getY(), getZ(), "explode", 0.6F, 1.4F);
        discard();
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.targetId = Nbt.getInt(tag, "Target", -1);
        this.age = Nbt.getInt(tag, "Age", 0);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Target", this.targetId);
        Nbt.putInt(tag, "Age", this.age);
    }
}
