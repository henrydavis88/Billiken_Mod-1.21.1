package net.walkingcarpet72.billikenmodneo.entity.custom;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Registry;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.Config;
import net.walkingcarpet72.billikenmodneo.blocks.ModBlocks;
import net.walkingcarpet72.billikenmodneo.enchantment.ModEnchantments;
import net.walkingcarpet72.billikenmodneo.item.ModItems;
import net.walkingcarpet72.billikenmodneo.recipe.BillikenRecipe;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;



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

        ItemStack pStack = player.getItemInHand(hand);

        Item startingItem;
        Item endingItem;
        Integer amount;
        Integer levels;

        List<BillikenCrafting> recipes = new ArrayList<>();

        Registry<BillikenRecipe> billikenRegistry = player.level().registryAccess().registryOrThrow(BillikenMod.ClientModEvents.BILLIKEN_RECIPE_REGISTRY);

        for(BillikenRecipe recipe : billikenRegistry) {
            startingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(recipe.startingItem()));
            endingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(recipe.endingItem()));
            amount = recipe.amount();
            levels = recipe.levels();
            recipes.add(new BillikenCrafting(startingItem, endingItem, amount, levels));
        }


        if (pStack.is(ModBlocks.TUITION_BLOCK.get().asItem())) {
            pStack.consume(1, player);
            billikenInteractionCooldown -= Config.BLOCK_TUITION_RESET_AMOUNT.getAsInt() * 20;
            return InteractionResult.SUCCESS;
        } else if (pStack.is(ModItems.TUITION.get())) {
            billikenInteractionCooldown -= Config.TUITION_RESET_AMOUNT.getAsInt() * 20;
            if (billikenInteractionCooldown <= 0) {
                billikenInteractionCooldown = 0;
                pStack.consume(1, player);
            }
            return InteractionResult.SUCCESS;
        }

        if (billikenInteractionCooldown > 0) {
            return super.mobInteract(player, hand);
        }

        for (int i = 0; i < recipes.size(); i++) {

            if (pStack.is(recipes.get(i).startingItem) && player.experienceLevel >= recipes.get(i).levelsRequired) {
                pStack.consume(1, player);
                for (int j = 1; j <= recipes.get(i).itemCount; j++) {
                    player.addItem(recipes.get(i).endResult.getDefaultInstance());
                }
                player.experienceLevel -= recipes.get(i).levelsRequired;
                billikenInteractionCooldown = Config.BILLIKEN_TRADE_RESET.getAsInt() * 20;
                return InteractionResult.SUCCESS;
            }
        }

        if (pStack.is(ItemTags.MINING_ENCHANTABLE) || pStack.is(ItemTags.WEAPON_ENCHANTABLE)) {

                int currentItemLevel = EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), pStack);

                BuiltInRegistries.ITEM.getHolder(ResourceLocation.parse("billikenmodneo:billiken_block"));

                if (currentItemLevel == 0 && player.experienceLevel >= Config.LEVELS_FOR_FIRST.getAsInt()) {
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 1);
                    player.experienceLevel -= Config.LEVELS_FOR_FIRST.getAsInt();
                    billikenInteractionCooldown =  Config.BILLIKEN_TRADE_RESET.getAsInt() * 20;
                    return InteractionResult.SUCCESS;
                } else if (currentItemLevel == 1 && player.experienceLevel >= Config.LEVELS_FOR_SECOND.getAsInt()) {
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 2);
                    player.experienceLevel -= Config.LEVELS_FOR_SECOND.getAsInt();
                    billikenInteractionCooldown =  Config.BILLIKEN_TRADE_RESET.getAsInt() * 20;
                    return InteractionResult.SUCCESS;
                } else if (currentItemLevel == 2 && player.experienceLevel >= Config.LEVELS_FOR_THIRD.getAsInt()) {
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 3);
                    player.experienceLevel -= Config.LEVELS_FOR_THIRD.getAsInt();
                    billikenInteractionCooldown =  Config.BILLIKEN_TRADE_RESET.getAsInt() * 20;
                    return InteractionResult.SUCCESS;
                }


        }
        return super.mobInteract(player, hand);
    }




}
