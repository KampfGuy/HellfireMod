
package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.item.FlareItem;
import com.kampfkaiser.hellfire.item.NuclearChargeItem;
import com.kampfkaiser.hellfire.item.StrikeRadioItem;
import com.kampfkaiser.hellfire.item.TacticalMissileItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HellfireMod.MODID);

    public static final RegistryObject<Item> STRIKE_RADIO = ITEMS.register("strike_radio",
            () -> new StrikeRadioItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TACTICAL_MISSILE = ITEMS.register("tactical_missile",
            () -> new TacticalMissileItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NUCLEAR_CHARGE = ITEMS.register("nuclear_charge",
            () -> new NuclearChargeItem(new Item.Properties().stacksTo(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FLARE = ITEMS.register("flare",
            () -> new FlareItem(new Item.Properties().stacksTo(64)));

    private ModItems() {}
}
