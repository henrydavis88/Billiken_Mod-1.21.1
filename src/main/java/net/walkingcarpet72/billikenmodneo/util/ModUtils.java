package net.walkingcarpet72.billikenmodneo.util;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.walkingcarpet72.billikenmodneo.BillikenMod;

import java.util.function.Supplier;

public class ModUtils {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BillikenMod.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> BILLIKEN_INTERACTION_COOLDOWN =
            ATTACHMENT_TYPES.register("billikeninteractioncooldown", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
