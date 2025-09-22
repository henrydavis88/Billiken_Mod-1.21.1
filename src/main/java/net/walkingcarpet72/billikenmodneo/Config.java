package net.walkingcarpet72.billikenmodneo;

import java.util.ArrayList;
import java.util.List;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenCrafting;


// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@Mod(BillikenMod.MOD_ID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Boolean> BILLIKEN_BLOCK_BREAK_KILLS = BUILDER
            .comment("Set true to punish those that dare break the Billiken Block")
            .define("punish",true);

    public static final ModConfigSpec.ConfigValue<Integer> BILLIKEN_TRADE_RESET = BUILDER
            .comment("How long should the cooldown of the Billiken Trading be?")
            .define("length in seconds: ", 300);

    public static final ModConfigSpec.ConfigValue<Integer> BLOCK_TUITION_RESET_AMOUNT = BUILDER
            .comment("How much should the Tuition Block reset the trading cooldown")
            .define("length in seconds: ", 300);

    public static final ModConfigSpec.ConfigValue<Integer> TUITION_RESET_AMOUNT = BUILDER
            .comment("How much should the Tuition Item reset the trading cooldown")
            .define("length in seconds: ", 30);
    public static final ModConfigSpec.ConfigValue<Integer> LEVELS_FOR_FIRST = BUILDER
            .comment("How many levels should the first round of enchantment cost: ")
            .define("Levels: ", 15);
    public static final ModConfigSpec.ConfigValue<Integer> LEVELS_FOR_SECOND = BUILDER
            .comment("How many levels should the second round of enchantment cost: ")
            .define("Levels: ", 20);
    public static final ModConfigSpec.ConfigValue<Integer> LEVELS_FOR_THIRD = BUILDER
            .comment("How many levels should the third round of enchantment cost: ")
            .define("Levels: ", 30);


    static final ModConfigSpec SPEC = BUILDER.build();


    public static Boolean billikenKills;
    public static Integer billikenTradeReset;
    public static Integer tuitionTradeReset;
    public static Integer tuitionBlockReset;
    public static Integer firstLevel;
    public static Integer secondLevel;
    public static Integer thirdLevel;


    public static List<BillikenCrafting> recipes = new ArrayList<>() {
    };


    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        billikenKills = BILLIKEN_BLOCK_BREAK_KILLS.get();
        billikenTradeReset = BILLIKEN_TRADE_RESET.get();
        tuitionBlockReset = BLOCK_TUITION_RESET_AMOUNT.get();
        tuitionTradeReset = TUITION_RESET_AMOUNT.get();
        firstLevel = LEVELS_FOR_FIRST.get();
        secondLevel = LEVELS_FOR_SECOND.get();
        thirdLevel = LEVELS_FOR_THIRD.get();

    }
}
