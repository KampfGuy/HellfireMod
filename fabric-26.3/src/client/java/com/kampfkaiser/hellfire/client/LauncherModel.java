package com.kampfkaiser.hellfire.client;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
public class LauncherModel extends EntityModel<OrdnanceRenderState> {
    public LauncherModel(ModelPart root) { super(root); }
    public static LayerDefinition createBodyLayer() { return Meshes.launcher(); }
    @Override public void setupAnim(OrdnanceRenderState state) {}
}
