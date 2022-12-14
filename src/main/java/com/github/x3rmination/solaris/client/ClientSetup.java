package com.github.x3rmination.solaris.client;

import com.github.x3rmination.solaris.Solaris;
import com.github.x3rmination.solaris.client.layer.BodyPartLayer;
import com.github.x3rmination.solaris.client.layer.FrostBiteLayer;
import com.github.x3rmination.solaris.client.particle.AnimatedSparksParticle;
import com.github.x3rmination.solaris.client.particle.CherryBlossomParticle;
import com.github.x3rmination.solaris.common.block.SolarisSunBlockRenderer;
import com.github.x3rmination.solaris.common.item.AbyssalEdge.AbyssalEdgeAttackRenderer;
import com.github.x3rmination.solaris.common.item.FireKatana.FireKatanaAttackRenderer;
import com.github.x3rmination.solaris.common.item.Frostbite.FrostbiteAttackRenderer;
import com.github.x3rmination.solaris.common.item.IceShoulderPad.IceShoulderPadRenderer;
import com.github.x3rmination.solaris.client.model.armor.SolarArmorModel;
import com.github.x3rmination.solaris.common.item.SpringWind.CherryBlossomSeeker.CherryBlossomSeekerRenderer;
import com.github.x3rmination.solaris.common.registry.BlockEntityRegistry;
import com.github.x3rmination.solaris.common.registry.EntityRegistry;
import com.github.x3rmination.solaris.common.registry.ItemRegistry;
import com.github.x3rmination.solaris.common.registry.ParticleRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.List;

public class ClientSetup {

    public static void setup(final FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ItemRegistry.ICE_SHOULDER_PAD.get(), IceShoulderPadRenderer::new);
        EntityRenderers.register(EntityRegistry.FIRE_KATANA_ATTACK.get(), FireKatanaAttackRenderer::new);
        EntityRenderers.register(EntityRegistry.FROSTBITE_ATTACK.get(), FrostbiteAttackRenderer::new);
        EntityRenderers.register(EntityRegistry.ABYSSAL_EDGE_ATTACK.get(), AbyssalEdgeAttackRenderer::new);
        EntityRenderers.register(EntityRegistry.CHERRY_BLOSSOM_SEEKER.get(), CherryBlossomSeekerRenderer::new);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                ForgeRegistries.ENTITIES
                        .getValues().stream()
                        .filter(DefaultAttributes::hasSupplier)
                        .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                        .toList());
        entityTypes.forEach((entityType -> {
            try {
                if (entityType != EntityType.ENDER_DRAGON) {
                    LivingEntityRenderer<LivingEntity, ? extends EntityModel<LivingEntity>> renderer = event.getRenderer(entityType);
                    if (renderer != null) {
                        renderer.addLayer(new FrostBiteLayer(renderer));
                    }
                }
            } catch (Exception e) {
                Solaris.LOGGER.warn(entityType.getRegistryName() + " has custom renderer, FrostBiteLayer could not be added");
            }
        }));
        for (String skinType : event.getSkins()){
            EntityRenderer<? extends Player> renderer = event.getSkin(skinType);
            if(renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new FrostBiteLayer(livingRenderer));
                livingRenderer.addLayer(new BodyPartLayer(livingRenderer));
            }
        }
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //Vanilla is 0.5F inner, 1.0F outer, some models may vary
        event.registerLayerDefinition(SolarArmorModel.INNER_LAYER_LOCATION, () -> SolarArmorModel.createBodyLayer(new CubeDeformation(0.1F), 0.0F));
        event.registerLayerDefinition(SolarArmorModel.OUTER_LAYER_LOCATION, () -> SolarArmorModel.createBodyLayer(new CubeDeformation(0.6F), 0.0F));
    }

    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.SOLARIS_SUN_BLOCK_ENTITY.get(), SolarisSunBlockRenderer::new);
    }

    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.ANIMATED_SPARKS.get(), AnimatedSparksParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.CHERRY_BLOSSOM.get(), CherryBlossomParticle.Provider::new);
    }

}
