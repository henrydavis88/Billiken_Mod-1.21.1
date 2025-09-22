package net.walkingcarpet72.billikenmod.entity.custom;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.walkingcarpet72.billikenmod.BillikenMod;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;


@Mod(BillikenMod.MOD_ID)
public class BillikenCraftsAssembler {



    public BillikenCraftsAssembler() {
        LOGGER.atInfo().log("starting assembly");
        final IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener(this::jsonReader);

    }

    private static Logger LOGGER = LogUtils.getLogger();

    public static List<BillikenCrafting> recipes = new ArrayList<>();



    private void jsonReader (AddReloadListenerEvent event) {
        LOGGER.atInfo().log("starting to read");


        Gson gson = new Gson();
        JsonObject craftingRecipesFile = null;

        try {

            craftingRecipesFile = gson.fromJson(new FileReader("billikenmodneo:billiken_crafting/billiken_crafts.json"), JsonObject.class);
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

    public static List<BillikenCrafting> jsonReaderTwo() {
        LOGGER.atInfo().log("starting to read");

        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("billikenmodneo","billiken_crafting/billiken_crafts.json");

        String path = BillikenCraftsAssembler.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        path = path + "resources\\data\\billikenmod\\billiken_crafting\\billiken_crafts.json";
        String sep = "\\";
        path = path.replace("build/","");
        path = path.replaceAll("/", Matcher.quoteReplacement(sep));
        path = path.replaceAll("sourcesSets", "src");
        LOGGER.atInfo().log(path);




        Gson gson = new Gson();
        JsonObject craftingRecipesFile = null;
        recipes.clear();

        try {
            craftingRecipesFile = gson.fromJson(new FileReader(path), JsonObject.class);
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


    }


}
