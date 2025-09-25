package net.walkingcarpet72.billikenmodneo.entity.custom;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.recipe.BillikenRecipe;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static net.walkingcarpet72.billikenmodneo.recipe.BillikenRecipe.BILLIKEN_RECIPE_CODEC;

/*
@Mod(BillikenMod.MOD_ID)
public class BillikenCraftsAssembler {



    public BillikenCraftsAssembler() {
        LOGGER.atInfo().log("starting assembly");
        final IEventBus forgeBus = NeoForge.EVENT_BUS;
        //forgeBus.addListener(this::jsonReader);

    }

    private static Logger LOGGER = LogUtils.getLogger();

    public static List<BillikenCrafting> recipes = new ArrayList<>();


    /*
    private void jsonReader (AddReloadListenerEvent event) {
        LOGGER.atInfo().log("starting to read");

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("billikenmodneo","billiken_crafting/billiken_crafts.json");

        LOGGER.atInfo().log("Resource Location: ");
        LOGGER.atInfo().log(String.valueOf(location));


        Gson gson = new Gson();
        JsonObject craftingRecipesFile = null;



        try {

            String path = BillikenCraftsAssembler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path + "resources\\data\\billikenmodneo\\billiken_crafting\\billiken_crafts.json";
            String sep = "\\";
            path = path.replace("build/","");
            path = path.replaceAll("/", Matcher.quoteReplacement(sep));
            path = path.replaceAll("sourcesSets", "src");

            LOGGER.atInfo().log("Path");
            LOGGER.atInfo().log(path);

            craftingRecipesFile = gson.fromJson(new FileReader(location.getPath()), JsonObject.class);

            JsonArray recipeList = craftingRecipesFile.get("recipes").getAsJsonArray();
            for (int i = 0; i < recipeList.size(); i++) {
                JsonObject currentRecipe = recipeList.get(i).getAsJsonObject();
                Item startingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(currentRecipe.get("starting_item").getAsString()));
                Item endingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(currentRecipe.get("ending_item").getAsString()));
                Integer amount = currentRecipe.get("amount").getAsInt();
                Integer levels = currentRecipe.get("levels").getAsInt();
                recipes.add(new BillikenCrafting(startingItem, endingItem, amount, levels));
                LOGGER.atInfo().log("hello");
            }



        } catch (FileNotFoundException e) {
            LOGGER.atInfo().log("no files");
            throw new RuntimeException(e);
        }
    }
    */
    /*
    public static List<BillikenCrafting> jsonReaderTwo() {
        LOGGER.atInfo().log("starting to read");

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("billikenmodneo","billiken_crafting/billiken_crafts.json");

        LOGGER.atInfo().log(String.valueOf(location));

        String path = BillikenCraftsAssembler.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        LOGGER.atInfo().log(path);

        path = path + "resources\\data\\billikenmodneo\\billiken_crafting\\billiken_crafts.json";
        String sep = "\\";
        path = path.replace("build/","");
        path = path.replaceAll("/", Matcher.quoteReplacement(sep));
        path = path.replaceAll("sourcesSets", "src");
        LOGGER.atInfo().log(path);




        Gson gson = new Gson();
        JsonObject craftingRecipesFile = null;
        recipes.clear();

        try {
            craftingRecipesFile = gson.fromJson(new FileReader(location.getPath()), JsonObject.class);
            JsonArray recipeList = craftingRecipesFile.get("recipes").getAsJsonArray();
            for (int i = 0; i < recipeList.size(); i++) {
                JsonObject currentRecipe = recipeList.get(i).getAsJsonObject();
                Item startingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(currentRecipe.get("starting_item").getAsString()));
                Item endingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(currentRecipe.get("ending_item").getAsString()));
                Integer amount = currentRecipe.get("amount").getAsInt();
                Integer levels = currentRecipe.get("levels").getAsInt();
                recipes.add(new BillikenCrafting(startingItem, endingItem, amount, levels));
            }
            LOGGER.atInfo().log(String.valueOf(recipes.size()));
            return (recipes);

        } catch (FileNotFoundException e) {
            LOGGER.atInfo().log("no files");
            throw new RuntimeException(e);
        }




    }*/

 /*   public static List<BillikenCrafting> jsonReaderThree() {
        //get json file location
        //get Codec
        Item startingItem;
        Item endingItem;
        Integer amount;
        Integer levels;




        for(BillikenRecipe recipe : billikenRegistry) {
            startingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(recipe.startingItem()));
            endingItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(recipe.endingItem()));
            amount = recipe.amount();
            levels = recipe.levels();
            recipes.add(new BillikenCrafting(startingItem, endingItem, amount, levels));
        }

        return recipes;
    }


/*

}

  */
