package net.walkingcarpet72.billikenmodneo.datagen;

import com.mojang.logging.LogUtils;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BillikenMod.MOD_ID)
public class DataGenerators {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

      Logger LOGGER = LogUtils.getLogger();

      LOGGER.atInfo().log("Hello");
      generator.addProvider(event.includeServer(), new ModDataPackProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
              List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

      BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
      generator.addProvider(event.includeServer(), blockTagsProvider);
      generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

      generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));


      generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

  }

}
