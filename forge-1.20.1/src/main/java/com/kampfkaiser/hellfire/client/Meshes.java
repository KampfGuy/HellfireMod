package com.kampfkaiser.hellfire.client;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/** Original box models. Not copied from Minecraft or downloaded. */
public final class Meshes {
    private Meshes() {}

    public static LayerDefinition plane() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("fuselage", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -12.0F, 4.0F, 4.0F, 24.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 29).addBox(-16.0F, -0.5F, -3.0F, 14.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(0, 37).addBox(2.0F, -0.5F, -3.0F, 14.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("tailplane", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0F, -0.5F, 8.0F, 10.0F, 1.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 51).addBox(-0.5F, -6.0F, 8.0F, 1.0F, 6.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("cockpit", CubeListBuilder.create().texOffs(42, 29).addBox(-1.5F, -4.0F, -6.0F, 3.0F, 2.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(42, 38).addBox(-1.0F, -1.0F, -16.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("intake_left", CubeListBuilder.create().texOffs(42, 46).addBox(-3.5F, -1.0F, 6.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("intake_right", CubeListBuilder.create().texOffs(42, 54).addBox(1.5F, -1.0F, 6.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition bomb() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 6.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_l", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, 1.0F, -0.5F, 3.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_r", CubeListBuilder.create().texOffs(12, 22).addBox(2.0F, 1.0F, -0.5F, 3.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_f", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, 1.0F, -5.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_b", CubeListBuilder.create().texOffs(24, 10).addBox(-0.5F, 1.0F, 2.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition missile() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -8.0F, 3.0F, 3.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -1.0F, -12.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_l", CubeListBuilder.create().texOffs(16, 22).addBox(-4.5F, -0.5F, 4.0F, 3.0F, 1.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_r", CubeListBuilder.create().texOffs(16, 32).addBox(1.5F, -0.5F, 4.0F, 3.0F, 1.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_t", CubeListBuilder.create().texOffs(30, 22).addBox(-0.5F, -4.5F, 4.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("fin_d", CubeListBuilder.create().texOffs(30, 32).addBox(-0.5F, 1.5F, 4.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition charge() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("crate", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("band", CubeListBuilder.create().texOffs(0, 18).addBox(-5.2F, -6.0F, -5.2F, 10.4F, 2.0F, 10.4F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("lamp", CubeListBuilder.create().texOffs(18, 18).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition flare() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("shaft", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 12.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -16.0F, -1.5F, 3.0F, 4.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition sprite() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("card", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -0.5F, 8.0F, 14.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }


    public static LayerDefinition launcher() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 4.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, 4.0F, -5.0F, 10.0F, 8.0F, 10.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(0, 42).addBox(-1.5F, 8.0F, -12.0F, 3.0F, 3.0F, 10.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("dish", CubeListBuilder.create().texOffs(32, 22).addBox(-4.0F, 12.0F, -3.0F, 8.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

}
