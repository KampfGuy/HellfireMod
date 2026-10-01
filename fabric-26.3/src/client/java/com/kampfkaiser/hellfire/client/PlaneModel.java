package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class PlaneModel extends EntityModel<OrdnanceRenderState> {
    public PlaneModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return Meshes.plane();
    }

    @Override
    public void setupAnim(OrdnanceRenderState state) {
    }
}
