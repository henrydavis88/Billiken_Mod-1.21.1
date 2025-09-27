package net.walkingcarpet72.billikenmodneo.entity.custom;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.walkingcarpet72.billikenmodneo.recipe.AllBillikenRecipes;
import net.walkingcarpet72.billikenmodneo.recipe.BillikenRecipe;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;


public class BillikenEntity extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    private static Logger LOGGER = LogUtils.getLogger();

    protected static final EntityDataAccessor<Integer> BILLIKEN_INTERACTION_COOLDOWN = SynchedEntityData.defineId(BillikenEntity.class, EntityDataSerializers.INT);




    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BILLIKEN_INTERACTION_COOLDOWN, 0);
    }

    public BillikenEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        //billikenInteractionCooldown = 0;
        Set<String> curTags = this.getTags();
        LOGGER.atInfo().log("Hello new billiken");
        for(String str : curTags) {
            LOGGER.atInfo().log(str);
        }

        //this.setData(ModUtils.BILLIKEN_INTERACTION_COOLDOWN, 600);

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


    public void aiStep() {
        super.aiStep();
        int billikenInteractionCooldown = this.getBillikenInteractionCooldown();
        if(this.level().isClientSide()) {
            this.setupAnimationStates();
            if (billikenInteractionCooldown > 0) {
                this.setBillikenInteractionCooldown(billikenInteractionCooldown - 1);
            } else {
                this.level().addParticle(ParticleTypes.ENCHANT, this.getRandomX((double)0.5F), this.getRandomY(), this.getRandomZ((double)0.5F), (double)0.0F, (double)0.0F, (double)0.0F);
                this.setBillikenInteractionCooldown(0);
            }
            //LOGGER.atInfo().log(String.valueOf(billikenInteractionCooldown));



        }
        //this.setData(ModUtils.BILLIKEN_INTERACTION_COOLDOWN, this.getData(ModUtils.BILLIKEN_INTERACTION_COOLDOWN) + 1);
        //LOGGER.atInfo().log(String.valueOf(this.getData(ModUtils.BILLIKEN_INTERACTION_COOLDOWN)));
    }


    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        int billikenInteractionCooldown = this.getBillikenInteractionCooldown();

        ItemStack pStack = player.getItemInHand(hand);

        Item startingItem;
        Item endingItem;
        Integer amount;
        Integer levels;

        List<BillikenCrafting> recipes = new ArrayList<>();

        Registry<AllBillikenRecipes> allBillikenRecipesRegistry = player.level().registryAccess().registryOrThrow(BillikenMod.ClientModEvents.ALL_BILLIKEN_RECIPE_REGISTRY);

        for(AllBillikenRecipes allBillikenRecipes : allBillikenRecipesRegistry) {
            List<BillikenRecipe> billikenRecipes = allBillikenRecipes.recipes();
            for(BillikenRecipe curRecipe : billikenRecipes) {
                startingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(curRecipe.startingItem()));
                endingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(curRecipe.endingItem()));
                amount = curRecipe.amount();
                levels = curRecipe.levels();
                recipes.add(new BillikenCrafting(startingItem, endingItem, amount, levels));
            }
        }



        if (pStack.is(ModBlocks.TUITION_BLOCK.get().asItem())) {
            pStack.consume(1, player);
            this.setBillikenInteractionCooldown(billikenInteractionCooldown - Config.BLOCK_TUITION_RESET_AMOUNT.getAsInt() * 20);
            if (billikenInteractionCooldown <= 0) {
                this.setBillikenInteractionCooldown(0);
                pStack.consume(1, player);
            }
            return InteractionResult.SUCCESS;
        } else if (pStack.is(ModItems.TUITION.get())) {
            this.setBillikenInteractionCooldown(billikenInteractionCooldown - Config.TUITION_RESET_AMOUNT.getAsInt() * 20);
            if (billikenInteractionCooldown <= 0) {
                this.setBillikenInteractionCooldown(0);
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
                this.setBillikenInteractionCooldown(Config.BILLIKEN_TRADE_RESET.getAsInt() * 20);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        if (pStack.is(ItemTags.MINING_ENCHANTABLE) || pStack.is(ItemTags.WEAPON_ENCHANTABLE)) {
                LOGGER.atInfo().log("hello");
                int currentItemLevel = EnchantmentHelper.getItemEnchantmentLevel(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), pStack);

                LOGGER.atInfo().log(String.valueOf(currentItemLevel));
                LOGGER.atInfo().log(String.valueOf(Config.LEVELS_FOR_FIRST.getAsInt()));
                if (currentItemLevel == 0 && player.experienceLevel >= Config.LEVELS_FOR_FIRST.getAsInt()) {
                    LOGGER.atInfo().log(String.valueOf(Config.LEVELS_FOR_FIRST.getAsInt()));
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 1);
                    player.experienceLevel -= Config.LEVELS_FOR_FIRST.getAsInt();
                    this.setBillikenInteractionCooldown(Config.BILLIKEN_TRADE_RESET.getAsInt() * 20);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else if (currentItemLevel == 1 && player.experienceLevel >= Config.LEVELS_FOR_SECOND.getAsInt()) {
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 2);
                    player.experienceLevel -= Config.LEVELS_FOR_SECOND.getAsInt();
                    this.setBillikenInteractionCooldown(Config.BILLIKEN_TRADE_RESET.getAsInt() * 20);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else if (currentItemLevel == 2 && player.experienceLevel >= Config.LEVELS_FOR_THIRD.getAsInt()) {
                    pStack.enchant(player.level().registryAccess().holderOrThrow(ModEnchantments.BILLIKEN_BOUNTY), 3);
                    player.experienceLevel -= Config.LEVELS_FOR_THIRD.getAsInt();
                    this.setBillikenInteractionCooldown(Config.BILLIKEN_TRADE_RESET.getAsInt() * 20);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }


        }
        return super.mobInteract(player, hand);


    }

    /*

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        LOGGER.atInfo().log("Hello3");
        //this.billikenInteractionCooldown = compound.getInt("BillikenInteractionCooldown");
        //Set<String> curTags = this.getTags();
        //for(String str : curTags) {
        //    LOGGER.atInfo().log(str);
        //}
        String bic = String.valueOf(this.getEntityData().get(BILLIKEN_INTERACTION_COOLDOWN));
        LOGGER.atInfo().log("Billiken Interaction Cooldown Post-Load: " + bic);
        this.billikenInteractionCooldown = compound.getInt("BillikenInteractionCooldown");
    }

     */

    /*

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.billikenInteractionCooldown = compound.getInt("BillikenInteractionCooldown");
    }



    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BillikenInteractionCooldown", this.billikenInteractionCooldown);
    }

     */
    /*
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.getEntityData().set(BILLIKEN_INTERACTION_COOLDOWN, 0);
        compound.putInt("BillikenInteractionCooldown", this.billikenInteractionCooldown);
        String bic = String.valueOf(this.getEntityData().get(BILLIKEN_INTERACTION_COOLDOWN));
        LOGGER.atInfo().log("Billiken Interaction Cooldown Pre-Load: " + bic);
        LOGGER.atInfo().log("hello2");
        Set<String> curTags = this.getTags();
        for(String str : curTags) {
            LOGGER.atInfo().log(str);
        }

        //LOGGER.atInfo().log(compound.getAsString());


    }

     */

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBillikenInteractionCooldown(compound.getInt("BillikenInteractionCooldown"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("BillikenInteractionCooldown", this.getBillikenInteractionCooldown());
    }


    private void setBillikenInteractionCooldown(int coolDown) {
        this.entityData.set(BILLIKEN_INTERACTION_COOLDOWN, coolDown);
    }

    public int getBillikenInteractionCooldown() {
        return this.entityData.get(BILLIKEN_INTERACTION_COOLDOWN);
    }









}
