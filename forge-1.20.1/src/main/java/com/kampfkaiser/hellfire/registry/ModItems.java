package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.item.MissileStrikeItem;
import com.kampfkaiser.hellfire.item.NuclearStrikeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HellfireMod.MODID);

    public static final RegistryObject<Item> MISSILE_STRIKE = ITEMS.register("missile_strike",
            () -> new MissileStrikeItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NUCLEAR_STRIKE = ITEMS.register("nuclear_strike",
            () -> new NuclearStrikeItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    private ModItems() {}
}
