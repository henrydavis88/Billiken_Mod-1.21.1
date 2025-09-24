package net.walkingcarpet72.billikenmodneo.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BillikenRecipe {

    public static final Codec<BillikenRecipe> BILLIKEN_RECIPE_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("starting_item").forGetter(BillikenRecipe::getStartingItem),
                Codec.STRING.fieldOf("ending_item").forGetter(BillikenRecipe::getEndingItem),
                Codec.INT.fieldOf("amount").forGetter(BillikenRecipe::getAmount),
                Codec.INT.fieldOf("levels").forGetter(BillikenRecipe::getLevels))
        .apply(instance, BillikenRecipe::new)
    );


    public String startingItem;
    public String endingItem;
    public Integer amount;
    public Integer levels;

    public BillikenRecipe(String startingItem, String endingItem, Integer amount, Integer levels) {
        this.startingItem = startingItem;
        this.endingItem = endingItem;
        this.amount = amount;
        this.levels = levels;
    }

    public String getStartingItem() {
        return startingItem;
    }

    public String getEndingItem() {
        return endingItem;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getLevels() {
        return levels;
    }


}
