package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.item.MissileStrikeItem;
import com.kampfkaiser.hellfire.item.NuclearStrikeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
    public static Item MISSILE_STRIKE;
    public static Item NUCLEAR_STRIKE;

    private ModItems() {}

    public static void init() {
        MISSILE_STRIKE = register("missile_strike", MissileStrikeItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
        NUCLEAR_STRIKE = register("nuclear_strike", NuclearStrikeItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, HellfireMod.id(name));
        Item item = factory.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}
