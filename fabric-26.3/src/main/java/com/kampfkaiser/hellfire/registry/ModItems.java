package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.entity.Strikes;
import com.kampfkaiser.hellfire.item.AaPlatformItem;
import net.minecraft.world.item.ItemStack;
import com.kampfkaiser.hellfire.item.ThrowFlareItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
    public static Item MISSILE_STRIKE;
    public static Item NUCLEAR_STRIKE;
    public static Item NAPALM_STRIKE;
    public static Item BOMBING_RUN;
    public static Item AA_PLATFORM;

    private ModItems() {}

    public static void init() {
        MISSILE_STRIKE = register("missile_strike", props -> new ThrowFlareItem(props, Strikes.MISSILE, "item.hellfire.missile_strike.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
        NUCLEAR_STRIKE = register("nuclear_strike", props -> new ThrowFlareItem(props, Strikes.NUCLEAR, "item.hellfire.nuclear_strike.tooltip", 80), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
        NAPALM_STRIKE = register("napalm_strike", props -> new ThrowFlareItem(props, Strikes.NAPALM, "item.hellfire.napalm_strike.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
        BOMBING_RUN = register("bombing_run", props -> new ThrowFlareItem(props, Strikes.BOMBING, "item.hellfire.bombing_run.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
        AA_PLATFORM = register("aa_platform", props -> new AaPlatformItem(props), new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    }

    private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, HellfireMod.id(name));
        Item item = factory.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }


    public static ItemStack flareStack(int kind) {
        Item item = switch (kind) {
            case Strikes.NUCLEAR -> NUCLEAR_STRIKE;
            case Strikes.NAPALM -> NAPALM_STRIKE;
            case Strikes.BOMBING -> BOMBING_RUN;
            default -> MISSILE_STRIKE;
        };
        return new ItemStack(item);
    }
}
