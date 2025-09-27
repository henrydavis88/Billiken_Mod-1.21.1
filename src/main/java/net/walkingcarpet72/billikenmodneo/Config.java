package net.walkingcarpet72.billikenmodneo;

import java.util.ArrayList;
import java.util.List;


import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenCrafting;
import org.slf4j.Logger;


// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@Mod(BillikenMod.MOD_ID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    public static final ModConfigSpec.BooleanValue BILLIKEN_BLOCK_BREAK_KILLS = BUILDER
            .comment("Set true to punish those that dare break the Billiken Block")
            .define("punish", true);


    public static final ModConfigSpec.IntValue BLOCK_TUITION_RESET_AMOUNT = BUILDER
            .comment("How long should Tuition Block reset the trades by?")
            .defineInRange("item length in seconds: ", 10000, 0, Integer.MAX_VALUE);


    public static final ModConfigSpec.IntValue TUITION_RESET_AMOUNT = BUILDER
            .comment("How long should the Tuition Item reset the trades by by?")
            .defineInRange("block length in seconds: ", 1000, 0, Integer.MAX_VALUE);



    public static final ModConfigSpec.IntValue LEVELS_FOR_FIRST = BUILDER
            .comment("How many levels should the first round of enchantment cost: ")
            .defineInRange("Levels for First: ", 15, 0, Integer.MAX_VALUE);




    public static final ModConfigSpec.IntValue LEVELS_FOR_SECOND = BUILDER
            .comment("How many levels should the third round of enchantment cost: ")
            .defineInRange("Levels for Second: ", 20, 0, Integer.MAX_VALUE);


    public static final ModConfigSpec.IntValue LEVELS_FOR_THIRD = BUILDER
            .comment("How many levels should the third round of enchantment cost: ")
            .defineInRange("Levels for Third: ", 25, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue BILLIKEN_TRADE_RESET = BUILDER
            .comment("How long should the cooldown of the Billiken Trading be?")
            .defineInRange("length in seconds: ", 10000, 0, Integer.MAX_VALUE);


    static final ModConfigSpec SPEC = BUILDER.build();




    public static List<BillikenCrafting> recipes = new ArrayList<>() {
    };


    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }


    /*
    public static Boolean billikenKills;
    public static Integer billikenTradeReset;
    public static Integer tuitionTradeReset;
    public static Integer tuitionBlockReset;
    public static Integer firstLevel;
    public static Integer secondLevel;
    public static Integer thirdLevel;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        Logger LOGGER = LogUtils.getLogger();

        billikenKills = BILLIKEN_BLOCK_BREAK_KILLS.get();
        billikenTradeReset = BILLIKEN_TRADE_RESET.get();
        tuitionBlockReset = BLOCK_TUITION_RESET_AMOUNT.get();
        tuitionTradeReset = TUITION_RESET_AMOUNT.get();
        firstLevel = LEVELS_FOR_FIRST.get();
        secondLevel = LEVELS_FOR_SECOND.get();
        thirdLevel = LEVELS_FOR_THIRD.get();

        LOGGER.atInfo().log(billikenTradeReset.toString());

    }

     */
}
