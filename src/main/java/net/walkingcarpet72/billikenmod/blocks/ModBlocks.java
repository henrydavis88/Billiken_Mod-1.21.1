package net.walkingcarpet72.billikenmod.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.walkingcarpet72.billikenmod.BillikenMod;
import net.walkingcarpet72.billikenmod.blocks.custom.BillikenBlock;
import net.walkingcarpet72.billikenmod.item.ModItems;

import java.util.function.Supplier;



public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BillikenMod.MOD_ID);

    public static final DeferredBlock<Block> TUITION_BLOCK = BLOCKS.register("tuition_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(0.5f).sound(SoundType.CROP)));

    public static final DeferredBlock<Block> BILLIKEN_BLOCK = registerBlock("billiken_block",
            () -> new BillikenBlock(BlockBehaviour.Properties.of()
                    .strength(4f).sound(SoundType.ANCIENT_DEBRIS)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}