package net.walkingcarpet72.billikenmod.events;


import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.walkingcarpet72.billikenmod.BillikenMod;
import net.walkingcarpet72.billikenmod.entity.ModEntities;
import net.walkingcarpet72.billikenmod.entity.client.BillikenAnimations;
import net.walkingcarpet72.billikenmod.entity.client.BillikenModel;
import net.walkingcarpet72.billikenmod.entity.custom.BillikenEntity;

@EventBusSubscriber(modid = BillikenMod.MOD_ID)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BillikenModel.LAYER_LOCATION, BillikenModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BILLIKEN.get(), BillikenEntity.createAttributes().build());
    }
}


