package com.kampfkaiser.hellfire.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;

public class MissileModel extends EntityModel<Entity> {
    private final ModelPart root;

    public MissileModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        return Meshes.missile();
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {
    }

    @Override
    public void renderToBuffer(PoseStack pose, VertexConsumer buffer, int light, int overlay, int color) {
        this.root.render(pose, buffer, light, overlay, color);
    }
}
