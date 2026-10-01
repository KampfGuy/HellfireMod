package com.kampfkaiser.hellfire.client;

import com.kampfkaiser.hellfire.entity.Strikes;
import com.kampfkaiser.hellfire.entity.ThrownFlareEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class ThrownFlareRenderer extends EntityRenderer<ThrownFlareEntity, OrdnanceRenderState> {
    private final SpriteModel model;

    public ThrownFlareRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SpriteModel(context.bakeLayer(ModModelLayers.SPRITE));
    }

    @Override
    public OrdnanceRenderState createRenderState() { return new OrdnanceRenderState(); }

    @Override
    public void extractRenderState(ThrownFlareEntity entity, OrdnanceRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = entity.getYRot();
        state.xRot = entity.getXRot();
        state.kind = entity.kind();
        state.shadowRadius = 0.15F;
    }

    @Override
    public void submit(OrdnanceRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        pose.pushPose();
        pose.rotateDegrees(Axis.YP, 180.0F - state.yRot);
        pose.rotateDegrees(Axis.XP, state.xRot);
        pose.scale(-1.15F, -1.15F, 1.15F);
        Identifier texture = ModModelLayers.tex(texture(state.kind));
        collector.submitModel(this.model, state, pose, texture, state.lightCoords, OverlayTexture.NO_OVERLAY, EntityRenderState.NO_OUTLINE);
        pose.popPose();
        super.submit(state, pose, collector, camera);
    }

    static String texture(int kind) {
        return switch (kind) {
            case Strikes.NUCLEAR -> "textures/item/nuclear_strike.png";
            case Strikes.NAPALM -> "textures/item/napalm_strike.png";
            case Strikes.BOMBING -> "textures/item/bombing_run.png";
            default -> "textures/item/missile_strike.png";
        };
    }
}
