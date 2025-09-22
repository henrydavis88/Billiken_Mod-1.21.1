package net.walkingcarpet72.billikenmodneo.events;


import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.entity.ModEntities;
import net.walkingcarpet72.billikenmodneo.entity.client.BillikenModel;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenEntity;

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


