package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class BombModel extends EntityModel<OrdnanceRenderState> {
    public BombModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return Meshes.bomb();
    }

    @Override
    public void setupAnim(OrdnanceRenderState state) {
    }
}
