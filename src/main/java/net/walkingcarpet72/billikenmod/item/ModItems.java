package net.walkingcarpet72.billikenmod.item;




import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.walkingcarpet72.billikenmod.BillikenMod;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BillikenMod.MOD_ID);


    public static final DeferredItem<Item> TUITION = ITEMS.register("tuition",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
