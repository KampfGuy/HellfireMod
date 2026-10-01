package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class FlareModel extends EntityModel<OrdnanceRenderState> {
    public FlareModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return Meshes.flare();
    }

    @Override
    public void setupAnim(OrdnanceRenderState state) {
    }
}
