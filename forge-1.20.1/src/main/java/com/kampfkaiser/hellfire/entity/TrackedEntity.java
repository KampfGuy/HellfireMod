
package com.kampfkaiser.hellfire.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class TrackedEntity extends Entity {
    public TrackedEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        readExtra(tag);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        writeExtra(tag);
    }

    protected abstract void readExtra(CompoundTag tag);

    protected abstract void writeExtra(CompoundTag tag);

    protected Level world() { return this.level(); }

    protected boolean client() { return this.level().isClientSide; }

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
