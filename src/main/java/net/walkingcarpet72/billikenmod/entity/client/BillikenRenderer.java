package net.walkingcarpet72.billikenmod.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.walkingcarpet72.billikenmod.BillikenMod;
import net.walkingcarpet72.billikenmod.entity.custom.BillikenEntity;

@OnlyIn(Dist.CLIENT)
public class BillikenRenderer extends MobRenderer<BillikenEntity, BillikenModel<BillikenEntity>> {

    public BillikenRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BillikenModel(pContext.bakeLayer(BillikenModel.LAYER_LOCATION)),0.85f);
    }

    @Override
    public ResourceLocation getTextureLocation(BillikenEntity billikenEntity) {
        return ResourceLocation.fromNamespaceAndPath(BillikenMod.MOD_ID, "textures/entity/billiken/billiken.png");
    }

    @Override
    public void render(BillikenEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }




}
