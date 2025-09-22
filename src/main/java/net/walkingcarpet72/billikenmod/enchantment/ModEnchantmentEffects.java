package net.walkingcarpet72.billikenmod.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.walkingcarpet72.billikenmod.BillikenMod;
import net.walkingcarpet72.billikenmod.enchantment.custom.BillikenBountyEnchantmentEffect;

import java.util.function.Supplier;


public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LOCATION_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, BillikenMod.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentLocationBasedEffect>> BILLIKEN_BOUNTY =
            LOCATION_ENCHANTMENT_EFFECTS.register("billiken_bounty", () -> BillikenBountyEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        LOCATION_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
