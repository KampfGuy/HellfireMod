
package com.kampfkaiser.hellfire.client;

import com.kampfkaiser.hellfire.HellfireMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public final class ModModelLayers {
    public static final ModelLayerLocation PLANE = layer("strike_plane");
    public static final ModelLayerLocation BOMB = layer("bomb");
    public static final ModelLayerLocation MISSILE = layer("missile");
    public static final ModelLayerLocation CHARGE = layer("nuclear_charge");
    public static final ModelLayerLocation FLARE = layer("flare");
    public static final ModelLayerLocation LAUNCHER = layer("aa_platform");

    private ModModelLayers() {}

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(new ResourceLocation(HellfireMod.MODID, name), "main");
    }

    public static ResourceLocation tex(String path) {
        return new ResourceLocation(HellfireMod.MODID, path);
    }
}
