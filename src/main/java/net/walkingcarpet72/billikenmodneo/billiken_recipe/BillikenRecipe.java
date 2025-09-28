package net.walkingcarpet72.billikenmodneo.billiken_recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BillikenRecipe (
        String startingItem,
        String endingItem,
        Integer amount,
        Integer levels
)
{
    public static final Codec<BillikenRecipe> BILLIKEN_RECIPE_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("starting_item").forGetter(BillikenRecipe::startingItem),
                Codec.STRING.fieldOf("ending_item").forGetter(BillikenRecipe::endingItem),
                Codec.INT.fieldOf("amount").forGetter(BillikenRecipe::amount),
                Codec.INT.fieldOf("levels").forGetter(BillikenRecipe::levels))
        .apply(instance, BillikenRecipe::new)
    );

}
