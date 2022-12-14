package com.github.x3rmination.solaris.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;

public class BodyPartLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public BodyPartLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> pRenderer) {
        super((RenderLayerParent<T, M>) pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
//        ModelPart part = TestModel.createBodyLayer().bakeRoot();
//        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entitySolid(new ResourceLocation(Solaris.MOD_ID, "textures/body_part/texture.png")));
//        HumanoidModel<AbstractClientPlayer> parentModel = (HumanoidModel<AbstractClientPlayer>) this.getParentModel();
//        part.copyFrom(parentModel.getHead());
//        part.render(pPoseStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F));
    }
}
