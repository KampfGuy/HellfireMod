package com.kampfkaiser.hellfire.client;

import com.kampfkaiser.hellfire.entity.Strikes;
import com.kampfkaiser.hellfire.entity.ThrownFlareEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ThrownFlareRenderer extends EntityRenderer<ThrownFlareEntity> {
    private final SpriteModel model;

    public ThrownFlareRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SpriteModel(context.bakeLayer(ModModelLayers.SPRITE));
        this.shadowRadius = 0.15F;
    }

    @Override
    public void render(ThrownFlareEntity entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffers, int light) {
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
        pose.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        pose.scale(-1.15F, -1.15F, 1.15F);
        ResourceLocation texture = ModModelLayers.tex(texture(entity.kind()));
        this.model.renderToBuffer(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pose.popPose();
        super.render(entity, yaw, partialTick, pose, buffers, light);
    }

    static String texture(int kind) {
        return switch (kind) {
            case Strikes.NUCLEAR -> "textures/item/nuclear_strike.png";
            case Strikes.NAPALM -> "textures/item/napalm_strike.png";
            case Strikes.BOMBING -> "textures/item/bombing_run.png";
            default -> "textures/item/missile_strike.png";
        };
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownFlareEntity entity) {
        return ModModelLayers.tex(texture(entity.kind()));
    }
}
