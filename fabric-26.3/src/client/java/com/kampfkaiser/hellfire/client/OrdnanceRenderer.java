
package com.kampfkaiser.hellfire.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class OrdnanceRenderer<T extends Entity> extends EntityRenderer<T, OrdnanceRenderState> {
    private final EntityModel<OrdnanceRenderState> model;
    private final Identifier texture;
    private final float scale;

    public OrdnanceRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, Function<ModelPart, EntityModel<OrdnanceRenderState>> factory, Identifier texture, float scale) {
        super(context);
        this.model = factory.apply(context.bakeLayer(layer));
        this.texture = texture;
        this.scale = scale;
    }

    @Override
    public OrdnanceRenderState createRenderState() {
        return new OrdnanceRenderState();
    }

    @Override
    public void extractRenderState(T entity, OrdnanceRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = entity.getYRot();
        state.xRot = entity.getXRot();
        state.shadowRadius = 0.35F;
    }

    @Override
    public void submit(OrdnanceRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        pose.pushPose();
        pose.rotateDegrees(Axis.YP, 180.0F - state.yRot);
        pose.rotateDegrees(Axis.XP, state.xRot);
        pose.scale(-this.scale, -this.scale, this.scale);
        collector.submitModel(this.model, state, pose, this.texture, state.lightCoords, OverlayTexture.NO_OVERLAY, EntityRenderState.NO_OUTLINE);
        pose.popPose();
        super.submit(state, pose, collector, camera);
    }
}
