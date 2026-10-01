
package com.kampfkaiser.hellfire.client;

import com.kampfkaiser.hellfire.HellfireMod;
import com.kampfkaiser.hellfire.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HellfireMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HellfireClient {
    private HellfireClient() {}

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.PLANE, PlaneModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BOMB, BombModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MISSILE, MissileModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CHARGE, ChargeModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FLARE, FlareModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.PLANE.get(), ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.PLANE, PlaneModel::new, ModModelLayers.tex("textures/entity/plane.png"), 1.65F));
        event.registerEntityRenderer(ModEntities.BOMB.get(), ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.BOMB, BombModel::new, ModModelLayers.tex("textures/entity/bomb.png"), 1.15F));
        event.registerEntityRenderer(ModEntities.MISSILE.get(), ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.MISSILE, MissileModel::new, ModModelLayers.tex("textures/entity/missile.png"), 1.25F));
        event.registerEntityRenderer(ModEntities.CHARGE.get(), ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.CHARGE, ChargeModel::new, ModModelLayers.tex("textures/entity/charge.png"), 1.2F));
        event.registerEntityRenderer(ModEntities.FLARE.get(), ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.FLARE, FlareModel::new, ModModelLayers.tex("textures/entity/flare.png"), 1.35F));
        event.registerEntityRenderer(ModEntities.CLOUD.get(), ctx -> new CloudRenderer(ctx));
    }
}
