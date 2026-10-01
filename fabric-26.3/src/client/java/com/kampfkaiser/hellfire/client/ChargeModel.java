package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ChargeModel extends EntityModel<OrdnanceRenderState> {
    public ChargeModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return Meshes.charge();
    }

    @Override
    public void setupAnim(OrdnanceRenderState state) {
    }
}
