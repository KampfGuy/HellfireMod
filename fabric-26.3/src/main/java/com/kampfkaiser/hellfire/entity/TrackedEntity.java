
package com.kampfkaiser.hellfire.entity;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class TrackedEntity extends Entity {
    public TrackedEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(ValueInput tag) {
        readExtra(tag);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput tag) {
        writeExtra(tag);
    }

    protected abstract void readExtra(ValueInput tag);

    protected abstract void writeExtra(ValueOutput tag);

    protected Level world() { return this.level(); }

    protected boolean client() { return this.level().isClientSide(); }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return false;
    }

    protected boolean grounded() {
        return this.onGround() || this.horizontalCollision || this.verticalCollision;
    }

    protected void markGhost() {
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    protected void markFloater() {
        this.setNoGravity(true);
    }

    protected void faceMotion() {
        Vec3 velocity = this.getDeltaMovement();
        if (velocity.lengthSqr() < 1.0E-6) return;
        float yaw = (float) (Math.atan2(velocity.z, velocity.x) * (180.0 / Math.PI)) - 90.0F;
        float pitch = (float) (-(Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)) * (180.0 / Math.PI)));
        snapYaw(yaw);
        this.setXRot(pitch);
        this.xRotO = pitch;
    }

    protected void snapYaw(float yaw) {
        this.setYRot(yaw);
        this.yRotO = yaw;
    }
}
