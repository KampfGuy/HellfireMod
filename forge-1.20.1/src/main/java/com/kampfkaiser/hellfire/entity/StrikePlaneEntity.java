package com.kampfkaiser.hellfire.entity;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class StrikePlaneEntity extends DurableEntity {
    public static final int BOMBS = 0;
    public static final int NAPALM = 1;
    public static final int NUKE = 2;

    private double ax;
    private double ay;
    private double az;
    private double dx;
    private double dz;
    private double nextDrop = -14.0;
    private double spacing = 7.0;
    private double speed = 1.35;
    private int dropsLeft = 5;
    private int mode = BOMBS;
    private boolean ready;

    public StrikePlaneEntity(EntityType<? extends StrikePlaneEntity> type, Level level) {
        super(type, level);
    }

    public static void spawn(Level level, Vec3 target, Vec3 look) {
        spawn(level, target, look, BOMBS);
    }

    public static void spawn(Level level, Vec3 target, Vec3 look, int mode) {
        if (Env.client(level)) return;
        Vec3 dir = new Vec3(look.x, 0.0, look.z);
        if (dir.lengthSqr() < 1.0E-4) dir = new Vec3(0.0, 0.0, 1.0);
        dir = dir.normalize();
        double distance = mode == NUKE ? 96.0 : 70.0;
        double altitude = mode == NUKE ? 34.0 : 24.0;
        Vec3 start = target.subtract(dir.scale(distance)).add(0.0, altitude, 0.0);
        StrikePlaneEntity plane = new StrikePlaneEntity(ModEntities.planeType(), level);
        plane.setPos(start.x, start.y, start.z);
        plane.markGhost();
        plane.ax = target.x;
        plane.ay = target.y + altitude;
        plane.az = target.z;
        plane.dx = dir.x;
        plane.dz = dir.z;
        plane.mode = mode;
        plane.speed = mode == NUKE ? 0.58 : 1.35;
        plane.dropsLeft = mode == NUKE ? 1 : 5;
        plane.spacing = mode == NUKE ? 1.0 : 7.0;
        plane.nextDrop = mode == NUKE ? 0.0 : -plane.spacing * (plane.dropsLeft / 2.0);
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
        setPos(getX() + this.dx * this.speed, this.ay, getZ() + this.dz * this.speed);
        Vec3 dir = new Vec3(this.dx, 0.0, this.dz);
        double along = new Vec3(getX() - this.ax, 0.0, getZ() - this.az).dot(dir);
        snapYaw((float) (Math.atan2(dir.z, dir.x) * (180.0 / Math.PI)) - 90.0F);
        while (this.dropsLeft > 0 && along >= this.nextDrop) {
            Vec3 drop = position().add(0.0, -1.4, 0.0);
            Vec3 velocity = dir.scale(0.22).add(0.0, -0.12, 0.0);
            if (this.mode == NUKE) BombEntity.spawnNuclear(world(), drop, velocity);
            else {
                BombEntity.spawn(world(), drop, velocity);
                if (this.mode == NAPALM) NapalmFieldEntity.spawn(world(), drop, 600);
            }
            this.dropsLeft--;
            this.nextDrop += this.spacing;
        }
        if (along > 72.0 || tickCount > 500) discard();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    protected void readExtra(CompoundTag tag) {
        this.ready = Nbt.getBool(tag, "Ready", false);
        this.ax = Nbt.getDouble(tag, "AX", 0);
        this.ay = Nbt.getDouble(tag, "AY", 0);
        this.az = Nbt.getDouble(tag, "AZ", 0);
        this.dx = Nbt.getDouble(tag, "DX", 0);
        this.dz = Nbt.getDouble(tag, "DZ", 1);
        this.nextDrop = Nbt.getDouble(tag, "NextDrop", -14);
        this.spacing = Nbt.getDouble(tag, "Spacing", 7);
        this.speed = Nbt.getDouble(tag, "Speed", 1.35);
        this.dropsLeft = Nbt.getInt(tag, "Drops", 5);
        this.mode = Nbt.getInt(tag, "Mode", BOMBS);
    }

    @Override
    protected void writeExtra(CompoundTag tag) {
        Nbt.putBool(tag, "Ready", this.ready);
        Nbt.putDouble(tag, "AX", this.ax);
        Nbt.putDouble(tag, "AY", this.ay);
        Nbt.putDouble(tag, "AZ", this.az);
        Nbt.putDouble(tag, "DX", this.dx);
        Nbt.putDouble(tag, "DZ", this.dz);
        Nbt.putDouble(tag, "NextDrop", this.nextDrop);
        Nbt.putDouble(tag, "Spacing", this.spacing);
        Nbt.putDouble(tag, "Speed", this.speed);
        Nbt.putInt(tag, "Drops", this.dropsLeft);
        Nbt.putInt(tag, "Mode", this.mode);
    }
}
