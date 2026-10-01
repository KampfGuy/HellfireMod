
package com.kampfkaiser.hellfire;

import com.kampfkaiser.hellfire.registry.ModEntities;
import com.kampfkaiser.hellfire.registry.ModItems;
import com.kampfkaiser.hellfire.registry.ModTabs;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(HellfireMod.MODID)
public class HellfireMod {
    public static final String MODID = "hellfire";
    public static final Logger LOGGER = LogUtils.getLogger();

    public HellfireMod(IEventBus bus, ModContainer container) {
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        container.registerConfig(ModConfig.Type.COMMON, HellfireConfig.SPEC);
        LOGGER.info("Hellfire (NeoForge) ready");
    }
}
