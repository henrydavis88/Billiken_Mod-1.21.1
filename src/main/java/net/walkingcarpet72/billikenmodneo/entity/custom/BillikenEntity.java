package net.walkingcarpet72.billikenmodneo.entity.custom;

import com.mojang.logging.LogUtils;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import net.walkingcarpet72.billikenmodneo.Config;
import net.walkingcarpet72.billikenmodneo.blocks.ModBlocks;
import net.walkingcarpet72.billikenmodneo.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

import static net.walkingcarpet72.billikenmodneo.enchantment.ModEnchantments.BILLIKEN_BOUNTY;


public class BillikenEntity extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    private static Logger LOGGER = LogUtils.getLogger();


    private int billikenInteractionCooldown = 0;

    public BillikenEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);


    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1,new PanicGoal(this,2.0));
        this.goalSelector.addGoal(2,new TemptGoal(this,1.25,stack -> stack.is(ModBlocks.TUITION_BLOCK.get().asItem()), false));
        this.goalSelector.addGoal(3,new TemptGoal(this,1.25,stack -> stack.is(ModItems.TUITION.get()), false));
        this.goalSelector.addGoal(4,new LookAtPlayerGoal(this, Player.class, 40.0f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH,30).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 25);
    }


    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <=0) {
            this.idleAnimationTimeout = 35;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
            if (billikenInteractionCooldown > 0) {
                billikenInteractionCooldown -= 1;
            } else {
                this.level().addParticle(ParticleTypes.ENCHANT, this.getRandomX((double)0.5F), this.getRandomY(), this.getRandomZ((double)0.5F), (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }

    }



    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        List<BillikenCrafting> recipesFinal = BillikenCraftsAssembler.jsonReaderTwo();
        ItemStack pStack = player.getItemInHand(hand);


        if (pStack.is(ModBlocks.TUITION_BLOCK.get().asItem())) {
            pStack.consume(1, player);
            billikenInteractionCooldown -= Config.tuitionBlockReset * 20;
            return InteractionResult.SUCCESS;
        } else if (pStack.is(ModItems.TUITION.get())) {
            billikenInteractionCooldown -= Config.tuitionTradeReset * 20;
            if (billikenInteractionCooldown <= 0) {
                billikenInteractionCooldown = 0;
                pStack.consume(1, player);
            }
            return InteractionResult.SUCCESS;
        }

        if (billikenInteractionCooldown > 0) {
            return super.mobInteract(player, hand);
        }

        for (int i = 0; i < recipesFinal.size(); i++) {

            if (pStack.is(recipesFinal.get(i).startingItem) && player.experienceLevel >= recipesFinal.get(i).levelsRequired) {
                pStack.consume(1, player);
                for (int j = 1; j <= recipesFinal.get(i).itemCount; j++) {
                    player.addItem(recipesFinal.get(i).endResult.getDefaultInstance());
                }
                player.experienceLevel -= recipesFinal.get(i).levelsRequired;
                billikenInteractionCooldown = Config.billikenTradeReset * 20;
                return InteractionResult.SUCCESS;
            }
        }
        if (pStack.is(ItemTags.MINING_ENCHANTABLE) || pStack.is(ItemTags.WEAPON_ENCHANTABLE)) {
                /*int currentItemLevel = EnchantmentHelper.getItemEnchantmentLevel(RegistryAccess.fromRegistryOfRegistries(BILLIKEN_BOUNTY), pStack);

                BuiltInRegistries.ITEM.getHolder(ResourceLocation.parse("billikenmodneo:billiken_block"));

                if (currentItemLevel == 0 && player.experienceLevel >= Config.firstLevel) {
                    pStack.enchant(BILLIKEN_BOUNTY.getOrThrow(player), 1);
                    player.experienceLevel -= Config.firstLevel;
                    billikenInteractionCooldown =  Config.billikenTradeReset * 20;
                    return InteractionResult.SUCCESS;
                } else if (currentItemLevel == 1 && player.experienceLevel >= Config.secondLevel) {
                    pStack.enchant(BILLIKEN_BOUNTY.getOrThrow(player), 2);
                    player.experienceLevel -= Config.secondLevel;
                    billikenInteractionCooldown =  Config.billikenTradeReset * 20;
                    return InteractionResult.SUCCESS;
                } else if (currentItemLevel == 2 && player.experienceLevel >= Config.thirdLevel) {
                    pStack.enchant(BILLIKEN_BOUNTY.getOrThrow(player), 3);
                    player.experienceLevel -= Config.thirdLevel;
                    billikenInteractionCooldown =  Config.billikenTradeReset * 20;
                    return InteractionResult.SUCCESS;
                }

*/
        }
        return super.mobInteract(player, hand);
    }


}
