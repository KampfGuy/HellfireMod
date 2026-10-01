package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MissileEntity extends TrackedEntity {
    private boolean hasTarget;
    private double tx;
    private double ty;
    private double tz;
    private boolean incendiary;
    private int age;

    public MissileEntity(EntityType<? extends MissileEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 pos, Vec3 velocity, Vec3 target, boolean incendiary) {
        if (Env.client(level)) return;
        MissileEntity missile = new MissileEntity(ModEntities.missileType(), level);
        missile.setPos(pos.x, pos.y, pos.z);
        missile.setDeltaMovement(velocity);
        missile.hasTarget = true;
        missile.tx = target.x;
        missile.ty = target.y;
        missile.tz = target.z;
        missile.incendiary = incendiary;
        missile.markFloater();
        level.addFreshEntity(missile);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        this.age++;
        if (!this.hasTarget || this.age > 180) {
            blow();
            return;
        }
        Vec3 target = new Vec3(this.tx, this.ty, this.tz);
        Vec3 to = target.subtract(position());
        if (to.length() < 1.35) {
            blow();
            return;
        }
        Vec3 desired = to.normalize().scale(1.3);
        Vec3 velocity = getDeltaMovement().scale(0.45).add(desired.scale(0.55));
        if (velocity.length() > 1.75) velocity = velocity.normalize().scale(1.75);
        setDeltaMovement(velocity);
        Vec3 next = position().add(velocity);
        net.minecraft.core.BlockPos ahead = net.minecraft.core.BlockPos.containing(next);
        if (this.age > 6 && !world().getBlockState(ahead).isAir() && !world().getBlockState(ahead).canBeReplaced()) {
            blow();
            return;
        }
        setPos(next.x, next.y, next.z);
        faceMotion();
        if (this.age > 8 && position().distanceToSqr(target) < 2.0) blow();
    }

    private void blow() {
        Blasts.missile(world(), position(), this.incendiary);
        discard();
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.hasTarget = Nbt.getBool(tag, "HasTarget", false);
        this.tx = Nbt.getDouble(tag, "TX", 0.0);
        this.ty = Nbt.getDouble(tag, "TY", 0.0);
        this.tz = Nbt.getDouble(tag, "TZ", 0.0);
        this.incendiary = Nbt.getBool(tag, "Incendiary", false);
        this.age = Nbt.getInt(tag, "Age", 0);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putBool(tag, "HasTarget", this.hasTarget);
        Nbt.putDouble(tag, "TX", this.tx);
        Nbt.putDouble(tag, "TY", this.ty);
        Nbt.putDouble(tag, "TZ", this.tz);
        Nbt.putBool(tag, "Incendiary", this.incendiary);
        Nbt.putInt(tag, "Age", this.age);
    }
}
