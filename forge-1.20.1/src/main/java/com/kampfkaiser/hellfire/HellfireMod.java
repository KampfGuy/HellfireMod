
package com.kampfkaiser.hellfire;

import com.kampfkaiser.hellfire.registry.ModEntities;
import com.kampfkaiser.hellfire.registry.ModItems;
import com.kampfkaiser.hellfire.registry.ModTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(HellfireMod.MODID)
public class HellfireMod {
    public static final String MODID = "hellfire";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public HellfireMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HellfireConfig.SPEC);
        LOGGER.info("Hellfire (Forge) ready");
    }
}
