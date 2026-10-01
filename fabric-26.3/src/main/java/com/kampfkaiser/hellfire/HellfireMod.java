
package com.kampfkaiser.hellfire;

import com.kampfkaiser.hellfire.registry.ModEntities;
import com.kampfkaiser.hellfire.registry.ModItems;
import com.kampfkaiser.hellfire.registry.ModTabs;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HellfireMod implements ModInitializer {
    public static final String MODID = "hellfire";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModEntities.init();
        ModItems.init();
        ModTabs.init();
        HellfireConfig.load();
        LOGGER.info("Hellfire (Fabric) ready");
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
