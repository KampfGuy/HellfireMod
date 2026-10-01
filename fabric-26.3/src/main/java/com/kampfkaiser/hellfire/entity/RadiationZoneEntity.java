package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** A lingering green zone. Fiction only: it hurts like wither and is labeled radiation. */
public class RadiationZoneEntity extends DurableEntity {
    private int life = 1200;
    private int radius = 16;

    public RadiationZoneEntity(EntityType<? extends RadiationZoneEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, int radius, int life) {
        if (Env.client(level)) return;
        RadiationZoneEntity zone = new RadiationZoneEntity(ModEntities.radiationType(), level);
        zone.setPos(pos.x, pos.y, pos.z);
        zone.markGhost();
        zone.radius = Math.max(8, radius);
        zone.life = life;
        level.addFreshEntity(zone);
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
        if (tickCount % 5 == 0) {
            DustParticleOptions dust = new DustParticleOptions(0x3CF233, 1.4F);
            for (int i = 0; i < 18; i++) {
                double ang = this.random.nextDouble() * Math.PI * 2.0;
                double dist = this.random.nextDouble() * this.radius;
                double px = getX() + Math.cos(ang) * dist;
                double pz = getZ() + Math.sin(ang) * dist;
                double py = getY() + this.random.nextDouble() * 3.0;
                server.sendParticles(dust, px, py, pz, 1, 0.2, 0.4, 0.2, 0.0);
            }
            server.sendParticles(ParticleTypes.COMPOSTER, getX(), getY() + 1.0, getZ(), 6, this.radius * 0.35, 1.0, this.radius * 0.35, 0.01);
        }
        if (tickCount % 20 == 0) {
            AABB box = new AABB(position(), position()).inflate(this.radius, 6.0, this.radius);
            for (Entity entity : nearby(server, box)) {
                if (!(entity instanceof LivingEntity living) || !living.isAlive()) continue;
                if (living instanceof Player player && (player.isSpectator() || player.getAbilities().instabuild)) continue;
                if (entity.position().distanceTo(new Vec3(getX(), entity.getY(), getZ())) > this.radius) continue;
                Env.harm(server, living, 2.0F);
            }
        }
    }

    protected java.util.List<Entity> nearby(ServerLevel level, AABB box) {
        return level.getEntities((Entity) null, box, entity -> true);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) { return distance < 192.0 * 192.0; }

    @Override
    protected void readExtra(ValueInput tag) {
        this.life = Nbt.getInt(tag, "Life", 1200);
        this.radius = Nbt.getInt(tag, "Radius", 16);
    }

    @Override
    protected void writeExtra(ValueOutput tag) {
        Nbt.putInt(tag, "Life", this.life);
        Nbt.putInt(tag, "Radius", this.radius);
    }
}
