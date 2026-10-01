package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StrikePlaneEntity extends DurableEntity {
    private double ax;
    private double ay;
    private double az;
    private double dx;
    private double dz;
    private double nextDrop = -16.0;
    private int dropsLeft = 5;
    private boolean ready;

    public StrikePlaneEntity(EntityType<? extends StrikePlaneEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 target, Vec3 look) {
        if (Env.client(level)) return;
        Vec3 dir = new Vec3(look.x, 0.0, look.z);
        if (dir.lengthSqr() < 1.0E-4) dir = new Vec3(0.0, 0.0, 1.0);
        dir = dir.normalize();
        Vec3 start = target.subtract(dir.scale(72.0)).add(0.0, 26.0, 0.0);
        StrikePlaneEntity plane = new StrikePlaneEntity(ModEntities.planeType(), level);
        plane.setPos(start.x, start.y, start.z);
        plane.markGhost();
        plane.ax = target.x;
        plane.ay = target.y;
        plane.az = target.z;
        plane.dx = dir.x;
        plane.dz = dir.z;
        plane.nextDrop = -16.0;
        plane.dropsLeft = 5;
        plane.ready = true;
        plane.snapYaw((float) (Math.atan2(dir.z, dir.x) * (180.0 / Math.PI)) - 90.0F);
        level.addFreshEntity(plane);
    }

    @Override
    public void tick() {
        super.tick();
        if (client()) return;
        if (!this.ready) {
            discard();
            return;
        }
        double step = 1.55;
        setPos(getX() + this.dx * step, getY(), getZ() + this.dz * step);
        Vec3 dir = new Vec3(this.dx, 0.0, this.dz);
        double along = position().subtract(new Vec3(this.ax, this.ay, this.az)).dot(dir);
        snapYaw((float) (Math.atan2(dir.z, dir.x) * (180.0 / Math.PI)) - 90.0F);
        while (this.dropsLeft > 0 && along >= this.nextDrop) {
            BombEntity.spawn(world(), position().add(0.0, -1.4, 0.0), dir.scale(0.25).add(0.0, -0.12, 0.0));
            this.dropsLeft--;
            this.nextDrop += 8.0;
        }
        if (along > 64.0 || tickCount > 400) discard();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    protected void readExtra(ValueInput tag) {
        this.ready = Nbt.getBool(tag, "Ready", false);
        this.ax = Nbt.getDouble(tag, "AX", 0);
        this.ay = Nbt.getDouble(tag, "AY", 0);
        this.az = Nbt.getDouble(tag, "AZ", 0);
        this.dx = Nbt.getDouble(tag, "DX", 0);
        this.dz = Nbt.getDouble(tag, "DZ", 1);
        this.nextDrop = Nbt.getDouble(tag, "NextDrop", -16);
        this.dropsLeft = Nbt.getInt(tag, "Drops", 5);
    }

    @Override
    protected void writeExtra(ValueOutput tag) {
        Nbt.putBool(tag, "Ready", this.ready);
        Nbt.putDouble(tag, "AX", this.ax);
        Nbt.putDouble(tag, "AY", this.ay);
        Nbt.putDouble(tag, "AZ", this.az);
        Nbt.putDouble(tag, "DX", this.dx);
        Nbt.putDouble(tag, "DZ", this.dz);
        Nbt.putDouble(tag, "NextDrop", this.nextDrop);
        Nbt.putInt(tag, "Drops", this.dropsLeft);
    }
}
