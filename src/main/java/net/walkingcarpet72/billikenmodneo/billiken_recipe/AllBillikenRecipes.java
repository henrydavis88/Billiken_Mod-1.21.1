package net.walkingcarpet72.billikenmodneo.billiken_recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record AllBillikenRecipes(
        List<BillikenRecipe> recipes
) {
    public static final Codec<AllBillikenRecipes> ALL_BILLIKEN_RECIPES_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(BillikenRecipe.BILLIKEN_RECIPE_CODEC).fieldOf("recipes").forGetter(AllBillikenRecipes::recipes)
            ).apply(instance, AllBillikenRecipes::new)
    );

}
