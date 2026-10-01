
package com.kampfkaiser.hellfire.client;

import com.kampfkaiser.hellfire.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HellfireClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLayerRegistry.registerModelLayer(ModModelLayers.PLANE, PlaneModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayers.BOMB, BombModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayers.MISSILE, MissileModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayers.CHARGE, ChargeModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayers.FLARE, FlareModel::createBodyLayer);

        EntityRendererRegistry.register(ModEntities.PLANE, ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.PLANE, PlaneModel::new, ModModelLayers.tex("textures/entity/plane.png"), 1.65F));
        EntityRendererRegistry.register(ModEntities.BOMB, ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.BOMB, BombModel::new, ModModelLayers.tex("textures/entity/bomb.png"), 1.15F));
        EntityRendererRegistry.register(ModEntities.MISSILE, ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.MISSILE, MissileModel::new, ModModelLayers.tex("textures/entity/missile.png"), 1.25F));
        EntityRendererRegistry.register(ModEntities.CHARGE, ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.CHARGE, ChargeModel::new, ModModelLayers.tex("textures/entity/charge.png"), 1.2F));
        EntityRendererRegistry.register(ModEntities.FLARE, ctx -> new OrdnanceRenderer<>(ctx, ModModelLayers.FLARE, FlareModel::new, ModModelLayers.tex("textures/entity/flare.png"), 1.35F));
        EntityRendererRegistry.register(ModEntities.CLOUD, CloudRenderer::new);
    }
}
