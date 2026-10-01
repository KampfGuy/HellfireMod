
package com.kampfkaiser.hellfire;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class HellfireConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue NUCLEAR_RADIUS = BUILDER
            .comment("In-game crater radius in blocks (about twice this across). Capped so a survival world stays playable. Not a real-world measurement.")
            .defineInRange("nuclearRadius", 18, 8, 22);
    public static final ModConfigSpec.DoubleValue EXPLOSION_POWER = BUILDER
            .comment("Minecraft explosion power of each staged blast inside the crater. Capped.")
            .defineInRange("explosionPower", 3.2, 1.0, 6.0);
    public static final ModConfigSpec.DoubleValue MISSILE_POWER = BUILDER
            .defineInRange("missilePower", 5.5, 1.0, 6.0);
    public static final ModConfigSpec.DoubleValue BOMB_POWER = BUILDER
            .defineInRange("bombPower", 3.0, 1.0, 5.0);
    public static final ModConfigSpec.BooleanValue MISSILE_STARTS_FIRES = BUILDER
            .comment("If true, every tactical missile starts fires. Otherwise only stacks toggled with sneak-use do.")
            .define("missileStartsFires", false);
    public static final ModConfigSpec.IntValue FUSE_SECONDS = BUILDER
            .defineInRange("fuseSeconds", 10, 3, 30);

    static final ModConfigSpec SPEC = BUILDER.build();

    private HellfireConfig() {}

    public static int nuclearRadius() { return clamp(read(NUCLEAR_RADIUS, 18), 8, 22); }
    public static float explosionPower() { return (float) clampD(read(EXPLOSION_POWER, 3.2), 1.0, 6.0); }
    public static float missilePower() { return (float) clampD(read(MISSILE_POWER, 5.5), 1.0, 6.0); }
    public static float bombPower() { return (float) clampD(read(BOMB_POWER, 3.0), 1.0, 5.0); }
    public static boolean missileStartsFires() { return read(MISSILE_STARTS_FIRES, false); }
    public static int fuseTicks() { return clamp(read(FUSE_SECONDS, 10), 3, 30) * 20; }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value, T fallback) {
        try { return value.get(); } catch (Exception ignored) { return fallback; }
    }

    private static int clamp(int v, int min, int max) { return Math.max(min, Math.min(max, v)); }
    private static double clampD(double v, double min, double max) { return Math.max(min, Math.min(max, v)); }
}
