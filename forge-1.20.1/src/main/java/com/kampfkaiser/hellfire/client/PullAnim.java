package com.kampfkaiser.hellfire.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class PullAnim {
    private PullAnim() {}

    public static void register(Item item) {
        ItemProperties.register(item, new ResourceLocation("minecraft", "pulling"), (stack, level, entity, seed) -> pulling(stack, entity) ? 1.0F : 0.0F);
        ItemProperties.register(item, new ResourceLocation("minecraft", "pull"), (stack, level, entity, seed) -> {
            if (!pulling(stack, entity)) return 0.0F;
            return Math.min(1.0F, (72000 - entity.getUseItemRemainingTicks()) / 20.0F);
        });
    }

    private static boolean pulling(ItemStack stack, LivingEntity entity) {
        return entity != null && entity.isUsingItem() && entity.getUseItem() == stack;
    }
}
