package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HellfireMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HELLFIRE = TABS.register("hellfire", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.hellfire"))
            .icon(() -> new ItemStack(ModItems.MISSILE_STRIKE.get()))
            .displayItems((params, output) -> {
                                output.accept(ModItems.MISSILE_STRIKE.get());
                output.accept(ModItems.NUCLEAR_STRIKE.get());
                output.accept(ModItems.NAPALM_STRIKE.get());
                output.accept(ModItems.BOMBING_RUN.get());
                output.accept(ModItems.AA_PLATFORM.get());
            })
            .build());

    private ModTabs() {}
}
