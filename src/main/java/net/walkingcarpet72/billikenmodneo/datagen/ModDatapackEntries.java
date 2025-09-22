package net.walkingcarpet72.billikenmodneo.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.walkingcarpet72.billikenmodneo.BillikenMod;
import net.walkingcarpet72.billikenmodneo.enchantment.ModEnchantments;


import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BillikenMod.MOD_ID));
    }
}
