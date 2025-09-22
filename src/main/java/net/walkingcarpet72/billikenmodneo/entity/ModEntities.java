package net.walkingcarpet72.billikenmodneo.entity;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenEntity;

import java.util.function.Supplier;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BillikenMod.MOD_ID);

    public static final Supplier<EntityType<BillikenEntity>> BILLIKEN =
            ENTITY_TYPES.register("billiken", () -> EntityType.Builder.of(BillikenEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("billiken"));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
