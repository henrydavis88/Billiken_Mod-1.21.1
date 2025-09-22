package net.walkingcarpet72.billikenmodneo.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;

public class BillikenBountyEnchantmentEffect implements EnchantmentLocationBasedEffect {

    public static final MapCodec<BillikenBountyEnchantmentEffect> CODEC = MapCodec.unit(BillikenBountyEnchantmentEffect::new);


    @Override
    public void onChangedBlock(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3, boolean b) {
        if (i >= 1) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                Double activateEffect = Math.random();
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 240, 0, true, true));
                if (activateEffect <= 0.02 * i) {
                    Double chooseEffect = Math.random();
                    if (chooseEffect <= 0.2) {
                        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 240, 0, true, true));
                    } else if (chooseEffect <= 0.4) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0, true, true));
                    } else if (chooseEffect <= 0.6) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0, true, true));
                    } else if (chooseEffect <= 0.8) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 240, 0, true, true));
                    } else {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 0, true, true));
                    }

                }

            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentLocationBasedEffect> codec() {
        return CODEC;
    }
}
