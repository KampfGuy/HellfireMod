package com.kampfkaiser.hellfire.item;

import com.kampfkaiser.hellfire.HellfireConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class Incendiary {
    private static final String KEY = "HellfireIgnite";

    private Incendiary() {}

    public static boolean flag(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.copyTag().getBoolean(KEY);
    }

    public static boolean enabled(ItemStack stack) {
        return flag(stack) || HellfireConfig.missileStartsFires();
    }

    public static void toggle(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putBoolean(KEY, !tag.getBoolean(KEY));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
}
