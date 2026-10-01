package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import java.util.UUID;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** A boxy launcher. It locks onto airborne targets and shoots them down. */
public class AaPlatformEntity extends DurableEntity {
    private static final double RANGE = 32.0;
    private UUID owner;
    private int lockId = -1;
    private int lockTicks;
    private int cooldown;

    public AaPlatformEntity(EntityType<? extends AaPlatformEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, UUID owner) {
        if (Env.client(level)) return;
        AaPlatformEntity platform = new AaPlatformEntity(ModEntities.platformType(), level);
        platform.setPos(pos.x, pos.y, pos.z);
        platform.owner = owner;
        level.addFreshEntity(platform);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        if (this.cooldown > 0) this.cooldown--;
        if (!(world() instanceof ServerLevel server)) return;
        Entity target = pick(server);
        if (target == null) {
            this.lockId = -1;
            this.lockTicks = 0;
            return;
        }
        if (target.getId() != this.lockId) {
            this.lockId = target.getId();
            this.lockTicks = 0;
        }
        this.lockTicks++;
        outline(server, target);
        if (this.lockTicks >= 8 && this.cooldown <= 0) {
            AaShotEntity.spawn(server, position().add(0.0, 1.3, 0.0), target);
            Env.sound(server, getX(), getY(), getZ(), "launch", 0.7F, 1.6F);
            this.cooldown = 24;
            this.lockTicks = 0;
        }
    }

    private Entity pick(ServerLevel level) {
        AABB box = new AABB(position(), position()).inflate(RANGE, 18.0, RANGE);
        Entity best = null;
        double bestDistance = RANGE * RANGE;
        for (Entity entity : nearby(level, box)) {
            if (!targetable(entity)) continue;
            double distance = entity.distanceToSqr(this);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = entity;
            }
        }
        return best;
    }

    private boolean targetable(Entity entity) {
        if (entity == this || !entity.isAlive()) return false;
        if (entity instanceof AaPlatformEntity || entity instanceof AaShotEntity) return false;
        if (entity instanceof FlareEntity || entity instanceof ThrownFlareEntity || entity instanceof NapalmFieldEntity
                || entity instanceof RadiationZoneEntity || entity instanceof MushroomCloudEntity) return false;
        if (entity instanceof Player player) {
            if (this.owner != null && this.owner.equals(player.getUUID())) return false;
            return player.isFallFlying();
        }
        if (entity instanceof StrikePlaneEntity || entity instanceof MissileEntity || entity instanceof BombEntity) return true;
        if (!(entity instanceof Mob mob) || mob.onGround()) return false;
        String path = EntityType.getKey(mob.getType()).getPath();
        return mob.isNoGravity()
                || path.equals("phantom")
                || path.equals("ghast")
                || path.equals("happy_ghast")
                || path.equals("blaze")
                || path.equals("bee")
                || path.equals("allay")
                || path.equals("vex")
                || path.equals("bat")
                || path.equals("parrot")
                || path.equals("wither")
                || path.equals("ender_dragon");
    }

    private void outline(ServerLevel level, Entity target) {
        AABB box = target.getBoundingBox().inflate(0.15);
        DustParticleOptions dust = new DustParticleOptions(0x44FF55, 1.1F);
        double[][] corners = {
                {box.minX, box.minY, box.minZ}, {box.maxX, box.minY, box.minZ},
                {box.minX, box.maxY, box.minZ}, {box.maxX, box.maxY, box.minZ},
                {box.minX, box.minY, box.maxZ}, {box.maxX, box.minY, box.maxZ},
                {box.minX, box.maxY, box.maxZ}, {box.maxX, box.maxY, box.maxZ}
        };
        for (double[] corner : corners) {
            level.sendParticles(dust, corner[0], corner[1], corner[2], 1, 0.02, 0.02, 0.02, 0.0);
        }
    }

    protected java.util.List<Entity> nearby(ServerLevel level, AABB box) {
        return level.getEntities((Entity) null, box, entity -> true);
    }

    @Override
    protected void readExtra(ValueInput tag) {
        this.cooldown = Nbt.getInt(tag, "Cooldown", 0);
        if (Nbt.getBool(tag, "HasOwner", false)) {
            this.owner = new UUID(Nbt.getLong(tag, "OwnerM", 0), Nbt.getLong(tag, "OwnerL", 0));
        }
    }

    @Override
    protected void writeExtra(ValueOutput tag) {
        Nbt.putInt(tag, "Cooldown", this.cooldown);
        Nbt.putBool(tag, "HasOwner", this.owner != null);
        if (this.owner != null) {
            Nbt.putLong(tag, "OwnerM", this.owner.getMostSignificantBits());
            Nbt.putLong(tag, "OwnerL", this.owner.getLeastSignificantBits());
        }
    }
}
