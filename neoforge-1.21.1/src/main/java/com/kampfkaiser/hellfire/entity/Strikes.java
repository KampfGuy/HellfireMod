package com.kampfkaiser.hellfire.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Calls the fictional strike that belongs to a thrown flare. */
public final class Strikes {
    public static final int MISSILE = 0;
    public static final int NUCLEAR = 1;
    public static final int NAPALM = 2;
    public static final int BOMBING = 3;

    private Strikes() {}

    public static void at(Level level, int kind, Vec3 pos, Vec3 aim) {
        if (Env.client(level)) return;
        Vec3 flat = new Vec3(aim.x, 0.0, aim.z);
        if (flat.lengthSqr() < 1.0E-4) flat = new Vec3(0.0, 0.0, 1.0);
        switch (kind) {
            case NUCLEAR -> {
                FlareEntity.spawn(level, pos, 160, FlareEntity.YELLOW);
                StrikePlaneEntity.spawn(level, pos, flat, StrikePlaneEntity.NUKE);
                Env.sound(level, pos.x, pos.y, pos.z, "launch", 2.2F, 0.7F);
            }
            case NAPALM -> {
                FlareEntity.spawn(level, pos, 80, FlareEntity.ORANGE);
                StrikePlaneEntity.spawn(level, pos, flat, StrikePlaneEntity.NAPALM);
                Env.sound(level, pos.x, pos.y, pos.z, "launch", 1.6F, 0.9F);
            }
            case BOMBING -> {
                FlareEntity.spawn(level, pos, 80, FlareEntity.BLUE);
                StrikePlaneEntity.spawn(level, pos, flat, StrikePlaneEntity.BOMBS);
                Env.sound(level, pos.x, pos.y, pos.z, "launch", 1.8F, 1.0F);
            }
            default -> missile(level, pos);
        }
    }

    public static void missile(Level level, Vec3 pos) {
        if (Env.client(level)) return;
        double ox = (level.getRandom().nextDouble() - 0.5) * 18.0;
        double oz = (level.getRandom().nextDouble() - 0.5) * 18.0;
        if (ox * ox + oz * oz < 36.0) ox = 14.0;
        Vec3 from = pos.add(ox, 36.0, oz);
        Vec3 velocity = pos.subtract(from).normalize().scale(1.5);
        MissileEntity.spawn(level, from, velocity, pos, false);
        FlareEntity.spawn(level, pos.add(0.0, 0.2, 0.0), 90, FlareEntity.RED);
        Env.sound(level, pos.x, pos.y, pos.z, "launch", 2.0F, 0.85F);
    }
}
