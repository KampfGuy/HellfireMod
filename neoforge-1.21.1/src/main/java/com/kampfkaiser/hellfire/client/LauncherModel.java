package com.kampfkaiser.hellfire.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
public class LauncherModel extends EntityModel<Entity> {
    private final ModelPart root;
    public LauncherModel(ModelPart root) { this.root = root; }
    public static LayerDefinition createBodyLayer() { return Meshes.launcher(); }
    @Override public void setupAnim(Entity entity, float a, float b, float c, float d, float e) {}
    @Override public void renderToBuffer(PoseStack pose, VertexConsumer buffer, int light, int overlay, int color) {
        this.root.render(pose, buffer, light, overlay, color);
    }
}
