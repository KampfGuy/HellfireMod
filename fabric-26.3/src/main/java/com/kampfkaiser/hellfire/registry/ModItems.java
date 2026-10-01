
package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.item.FlareItem;
import com.kampfkaiser.hellfire.item.NuclearChargeItem;
import com.kampfkaiser.hellfire.item.StrikeRadioItem;
import com.kampfkaiser.hellfire.item.TacticalMissileItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
    public static Item STRIKE_RADIO;
    public static Item TACTICAL_MISSILE;
    public static Item NUCLEAR_CHARGE;
    public static Item FLARE;

    private ModItems() {}

    public static void init() {
        STRIKE_RADIO = register("strike_radio", StrikeRadioItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
        TACTICAL_MISSILE = register("tactical_missile", TacticalMissileItem::new, new Item.Properties().stacksTo(16).rarity(Rarity.RARE));
        NUCLEAR_CHARGE = register("nuclear_charge", NuclearChargeItem::new, new Item.Properties().stacksTo(16).rarity(Rarity.EPIC));
        FLARE = register("flare", FlareItem::new, new Item.Properties().stacksTo(64));
    }

    private static Item register(String name, java.util.function.Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, HellfireMod.id(name));
        Item item = factory.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}
