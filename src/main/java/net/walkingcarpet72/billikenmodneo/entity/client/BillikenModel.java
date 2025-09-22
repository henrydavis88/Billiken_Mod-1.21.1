package net.walkingcarpet72.billikenmodneo.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenEntity;


@OnlyIn(Dist.CLIENT)
public class BillikenModel<T extends BillikenEntity> extends HierarchicalModel<T> {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BillikenMod.MOD_ID, "billiken"), "main");
    private final ModelPart base;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_ear;
    private final ModelPart left_ear;

    public BillikenModel(ModelPart root) {
        this.base = root.getChild("base");
        this.left_arm = this.base.getChild("left_arm");
        this.right_arm = this.base.getChild("right_arm");
        this.right_leg = this.base.getChild("right_leg");
        this.left_leg = this.base.getChild("left_leg");
        this.body = this.base.getChild("body");
        this.head = this.base.getChild("head");
        this.right_ear = this.head.getChild("right_ear");
        this.left_ear = this.head.getChild("left_ear");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition left_arm = base.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, -13.0F, 0.0F));

        PartDefinition larm_r1 = left_arm.addOrReplaceChild("larm_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-7.0F, -1.5F, -5.0005F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.5F, 2.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = base.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(5.0F, -13.0F, -1.0F));

        PartDefinition rarm_r1 = right_arm.addOrReplaceChild("rarm_r1", CubeListBuilder.create().texOffs(24, 30).addBox(5.0F, -1.5F, -5.0005F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.5F, 3.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = base.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(2.0F, -6.0F, 0.0F));

        PartDefinition rleg_r1 = right_leg.addOrReplaceChild("rleg_r1", CubeListBuilder.create().texOffs(12, 30).addBox(1.0F, 5.5F, -4.0005F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.5F, 2.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = base.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, -6.0F, 0.0F));

        PartDefinition lleg_r1 = left_leg.addOrReplaceChild("lleg_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-3.0F, 5.5F, -4.0005F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.5F, 2.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition body = base.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck_r1 = body.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(36, 34).addBox(-1.0F, -2.5F, -3.0005F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-5.0F, -1.5F, -5.0005F, 10.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.5F, 2.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0757F, -13.9586F, -0.6F));

        PartDefinition lhair_r1 = head.addOrReplaceChild("lhair_r1", CubeListBuilder.create().texOffs(33, 12).addBox(-2.0F, -10.5F, -2.0005F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(33, 19).addBox(1.0F, -8.5F, -2.0005F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0757F, 1.4586F, 2.6005F, 0.0F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(1, 1).addBox(-1.4038F, -6.7678F, -4.0005F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0757F, 1.4586F, 2.6005F, 0.0F, 0.0F, -0.7854F));


        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(3.9243F, -7.0414F, 1.6F));

        PartDefinition rear_r1 = right_ear.addOrReplaceChild("rear_r1", CubeListBuilder.create().texOffs(33, 25).addBox(3.0F, -7.5F, -2.0005F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(37, 39).addBox(4.0F, -9.5F, -2.0005F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.5F, 1.0005F, 0.0F, 0.0F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0757F, -7.0414F, 1.6F, 0.0F, 3.1416F, 0.0F));

        PartDefinition lear_r1 = left_ear.addOrReplaceChild("lear_r1", CubeListBuilder.create().texOffs(37, 30).addBox(3.0F, -7.5F, 0.0005F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 25).addBox(4.0F, -9.5F, 0.0005F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 8.5F, -1.0005F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return base;
    }

    @Override
    public void setupAnim(BillikenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(BillikenAnimations.ANIM_BILLIKEN_WALK, limbSwing, limbSwingAmount, 9.0F, 100f);
        this.animate(entity.idleAnimationState, BillikenAnimations.ANIM_BILLIKEN_IDLE, ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head.yRot = headYaw * ((float)Math.PI / 100f);
        this.head.xRot = headPitch * ((float)Math.PI / 100f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        base.render(poseStack, vertexConsumer, packedLight, color);

    }
}
