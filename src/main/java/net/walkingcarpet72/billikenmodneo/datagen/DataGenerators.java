package net.walkingcarpet72.billikenmodneo.datagen;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;


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
  }

}
