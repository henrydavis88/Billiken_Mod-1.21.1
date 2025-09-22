package net.walkingcarpet72.billikenmod.enchantment;


import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.walkingcarpet72.billikenmod.BillikenMod;
import net.walkingcarpet72.billikenmod.enchantment.custom.BillikenBountyEnchantmentEffect;

public class ModEnchantments {

    public static final ResourceKey<Enchantment> BILLIKEN_BOUNTY = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(BillikenMod.MOD_ID, "billiken_bounty"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);




        register(context, BILLIKEN_BOUNTY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                2,
                3,
                Enchantment.dynamicCost(15, 9),
                Enchantment.dynamicCost(65, 9),
                4,
                EquipmentSlotGroup.MAINHAND)).exclusiveWith(enchantments.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)).
                withEffect(EnchantmentEffectComponents.LOCATION_CHANGED, new BillikenBountyEnchantmentEffect()));

    }


    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
