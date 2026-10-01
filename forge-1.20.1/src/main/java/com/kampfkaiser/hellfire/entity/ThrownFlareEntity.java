package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** A thrown flare. It uses the item sprite and calls a strike when it lands. */
public class ThrownFlareEntity extends TrackedEntity {
    private static final EntityDataAccessor<Integer> KIND = SynchedEntityData.defineId(ThrownFlareEntity.class, EntityDataSerializers.INT);

    private int age;
    private double aimX = 1;
    private double aimZ;

    public ThrownFlareEntity(EntityType<? extends ThrownFlareEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Player player, int kind, float power) {
        if (Env.client(level)) return;
        Vec3 look = player.getLookAngle();
        ThrownFlareEntity flare = new ThrownFlareEntity(ModEntities.thrownType(), level);
        Vec3 pos = player.getEyePosition().add(look.scale(0.8));
        flare.setPos(pos.x, pos.y, pos.z);
        flare.setDeltaMovement(look.scale(0.45 + power * 1.45).add(0.0, 0.12, 0.0));
        flare.aimX = look.x;
        flare.aimZ = look.z;
        flare.getEntityData().set(KIND, kind);
        flare.faceMotion();
        level.addFreshEntity(flare);
    }

    public int kind() { return this.entityData.get(KIND); }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(KIND, Strikes.MISSILE);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.age++;
        if (getY() < world().getMinBuildHeight() - 8 || this.age > 200) {
            land();
            return;
        }
        setDeltaMovement(getDeltaMovement().add(0.0, -0.05, 0.0));
        Vec3 next = getDeltaMovement();
        move(MoverType.SELF, next);
        faceMotion();
        if (this.age > 2 && grounded()) land();
    }

    private void land() {
        Strikes.at(world(), kind(), position(), new Vec3(this.aimX, 0.0, this.aimZ));
        discard();
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.age = Nbt.getInt(tag, "Age", 0);
        this.aimX = Nbt.getDouble(tag, "AimX", 1);
        this.aimZ = Nbt.getDouble(tag, "AimZ", 0);
        this.entityData.set(KIND, Nbt.getInt(tag, "Kind", Strikes.MISSILE));
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putInt(tag, "Age", this.age);
        Nbt.putDouble(tag, "AimX", this.aimX);
        Nbt.putDouble(tag, "AimZ", this.aimZ);
        Nbt.putInt(tag, "Kind", kind());
    }
}
