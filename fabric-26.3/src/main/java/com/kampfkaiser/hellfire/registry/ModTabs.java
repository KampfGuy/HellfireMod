package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModTabs {
    private ModTabs() {}

    public static void init() {
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, HellfireMod.id("hellfire"));
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, FabricCreativeModeTab.builder()
                .title(Component.translatable("itemGroup.hellfire"))
                .icon(() -> new ItemStack(ModItems.MISSILE_STRIKE))
                .displayItems((params, output) -> {
                    output.accept(ModItems.MISSILE_STRIKE);
                    output.accept(ModItems.NUCLEAR_STRIKE);
                })
                .build());
    }
}
