
package com.kampfkaiser.hellfire.entity;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public final class Nbt {
    private Nbt() {}
    public static void putInt(ValueOutput tag, String key, int value) { tag.putInt(key, value); }
    public static void putDouble(ValueOutput tag, String key, double value) { tag.putDouble(key, value); }
    public static void putBool(ValueOutput tag, String key, boolean value) { tag.putBoolean(key, value); }
    public static int getInt(ValueInput tag, String key, int def) { return tag.getIntOr(key, def); }
    public static double getDouble(ValueInput tag, String key, double def) { return tag.getDoubleOr(key, def); }
    public static boolean getBool(ValueInput tag, String key, boolean def) { return tag.getBooleanOr(key, def); }
}
