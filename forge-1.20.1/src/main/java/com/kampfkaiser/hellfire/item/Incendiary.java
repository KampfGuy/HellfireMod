
package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.HellfireConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class Incendiary {
    private static final String KEY = "HellfireIgnite";

    private Incendiary() {}

    public static boolean flag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.getBoolean(KEY);
    }

    public static boolean enabled(ItemStack stack) {
        return flag(stack) || HellfireConfig.missileStartsFires();
    }

    public static void toggle(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(KEY, !tag.getBoolean(KEY));
    }
}
