package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.entity.Strikes;
import com.kampfkaiser.hellfire.item.RadioItem;
import com.kampfkaiser.hellfire.item.ThrowFlareItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
    public static Item RADIO;
    public static Item MISSILE_STRIKE;
    public static Item NUCLEAR_STRIKE;
    public static Item NAPALM_STRIKE;
    public static Item BOMBING_RUN;

    private ModItems() {}

    public static void init() {
        RADIO = register("radio", props -> new RadioItem(props), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
        MISSILE_STRIKE = register("missile_strike", props -> new ThrowFlareItem(props, Strikes.MISSILE, "item.hellfire.missile_strike.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
        NUCLEAR_STRIKE = register("nuclear_strike", props -> new ThrowFlareItem(props, Strikes.NUCLEAR, "item.hellfire.nuclear_strike.tooltip", 80), new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
        NAPALM_STRIKE = register("napalm_strike", props -> new ThrowFlareItem(props, Strikes.NAPALM, "item.hellfire.napalm_strike.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
        BOMBING_RUN = register("bombing_run", props -> new ThrowFlareItem(props, Strikes.BOMBING, "item.hellfire.bombing_run.tooltip", 30), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, HellfireMod.id(name));
        Item item = factory.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static Item radioItem() { return RADIO; }
}
