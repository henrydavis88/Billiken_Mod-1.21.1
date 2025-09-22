package net.walkingcarpet72.billikenmod.entity.custom;

import net.minecraft.world.item.Item;

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
