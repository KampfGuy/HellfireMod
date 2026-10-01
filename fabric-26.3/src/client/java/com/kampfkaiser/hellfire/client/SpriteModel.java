package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class SpriteModel extends EntityModel<OrdnanceRenderState> {
    public SpriteModel(ModelPart root) { super(root); }
    public static LayerDefinition createBodyLayer() { return Meshes.sprite(); }
    @Override public void setupAnim(OrdnanceRenderState state) {}
}
