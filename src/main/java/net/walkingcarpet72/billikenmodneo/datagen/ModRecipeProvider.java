package net.walkingcarpet72.billikenmodneo.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.walkingcarpet72.billikenmodneo.blocks.ModBlocks;
import net.walkingcarpet72.billikenmodneo.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TUITION_BLOCK.get())
                .pattern("TTT")
                .pattern("TDT")
                .pattern("TTT")
                .define('T', ModItems.TUITION.get())
                .define('D', Items.DIAMOND_BLOCK.asItem())
                .unlockedBy("has_tuition", has(ModItems.TUITION)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TUITION.get())
                .pattern("PPP")
                .pattern("PEP")
                .pattern("PPP")
                .define('P', Items.PAPER.asItem())
                .define('E', Items.EMERALD.asItem())
                .unlockedBy("has_emerald", has(Items.EMERALD)).save(recipeOutput);
    }
}
