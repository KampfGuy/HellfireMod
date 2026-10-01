
package com.kampfkaiser.hellfire.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class OrdnanceRenderer<T extends Entity> extends EntityRenderer<T> {
    private final EntityModel<Entity> model;
    private final ResourceLocation texture;
    private final float scale;

    public OrdnanceRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, Function<ModelPart, EntityModel<Entity>> factory, ResourceLocation texture, float scale) {
        super(context);
        this.model = factory.apply(context.bakeLayer(layer));
        this.texture = texture;
        this.scale = scale;
        this.shadowRadius = 0.35F;
    }

    @Override
    public void render(T entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffers, int light) {
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
        pose.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        pose.scale(-this.scale, -this.scale, this.scale);
        this.model.renderToBuffer(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(this.texture)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pose.popPose();
        super.render(entity, yaw, partialTick, pose, buffers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.texture;
    }
}
