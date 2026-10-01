
package com.kampfkaiser.hellfire.entity;

import net.minecraft.nbt.CompoundTag;

public final class Nbt {
    private Nbt() {}
    public static void putInt(CompoundTag tag, String key, int value) { tag.putInt(key, value); }
    public static void putDouble(CompoundTag tag, String key, double value) { tag.putDouble(key, value); }
    public static void putBool(CompoundTag tag, String key, boolean value) { tag.putBoolean(key, value); }
    public static int getInt(CompoundTag tag, String key, int def) { return tag.contains(key) ? tag.getInt(key) : def; }
    public static double getDouble(CompoundTag tag, String key, double def) { return tag.contains(key) ? tag.getDouble(key) : def; }
    public static boolean getBool(CompoundTag tag, String key, boolean def) { return tag.contains(key) ? tag.getBoolean(key) : def; }
}
