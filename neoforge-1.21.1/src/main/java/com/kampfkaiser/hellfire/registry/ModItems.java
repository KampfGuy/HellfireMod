package com.kampfkaiser.hellfire.registry;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.entity.Strikes;
import com.kampfkaiser.hellfire.item.RadioItem;
import com.kampfkaiser.hellfire.item.ThrowFlareItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HellfireMod.MODID);

    public static final DeferredItem<Item> RADIO = ITEMS.register("radio",
            () -> new RadioItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> MISSILE_STRIKE = ITEMS.register("missile_strike",
            () -> new ThrowFlareItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), Strikes.MISSILE, "item.hellfire.missile_strike.tooltip", 30));
    public static final DeferredItem<Item> NUCLEAR_STRIKE = ITEMS.register("nuclear_strike",
            () -> new ThrowFlareItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), Strikes.NUCLEAR, "item.hellfire.nuclear_strike.tooltip", 80));
    public static final DeferredItem<Item> NAPALM_STRIKE = ITEMS.register("napalm_strike",
            () -> new ThrowFlareItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), Strikes.NAPALM, "item.hellfire.napalm_strike.tooltip", 30));
    public static final DeferredItem<Item> BOMBING_RUN = ITEMS.register("bombing_run",
            () -> new ThrowFlareItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), Strikes.BOMBING, "item.hellfire.bombing_run.tooltip", 30));

    private ModItems() {}

    public static Item radioItem() { return RADIO.get(); }
}
