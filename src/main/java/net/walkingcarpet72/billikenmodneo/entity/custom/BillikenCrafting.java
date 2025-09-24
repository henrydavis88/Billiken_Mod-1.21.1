package net.walkingcarpet72.billikenmodneo.entity.custom;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.Item;
import net.walkingcarpet72.billikenmodneo.recipe.BillikenRecipe;

public class BillikenCrafting {
    public Item startingItem;
    public Item endResult;
    public Integer levelsRequired;
    public Integer itemCount;


    public BillikenCrafting(Item startingItem, Item endResult, Integer itemCount, Integer levelsRequired) {
        this.startingItem = startingItem;
        this.endResult = endResult;
        this.itemCount = itemCount;
        this.levelsRequired = levelsRequired;

    }


}
